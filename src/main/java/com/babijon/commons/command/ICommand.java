package com.babijon.commons.command;

import com.babijon.commons.utils.CommandMapUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ICommand extends Command {

    private final CommandInfo commandInfo;

    public ICommand(@NotNull String name) {
        super(name);
        this.commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);

        this.registerCommand();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (!sender.hasPermission(commandInfo.permission())) {
            sender.sendMessage("§fUnknown command. Type \"help\" for help.");
            return true;
        }

        if (commandInfo.onlyPlayers()) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cCommand is available only for players!");
                return true;
            }

            execute(((Player) sender).getPlayer(), args);

        } else {
            execute(sender, args);
        }

        return true;

    }

    public void execute(Player player, String[] args) {

    }

    public void execute(CommandSender sender, String[] args) {

    }

    private void registerCommand() {
        List<String> commands = Arrays.stream(commandInfo.aliases()).collect(Collectors.toList());
        commands.add(commandInfo.name());
        try {
            Method register = SimpleCommandMap.class.getDeclaredMethod("register",
                    String.class, Command.class, Boolean.TYPE, String.class);
            register.setAccessible(true);
            for (String cmd : commands) {
                register.invoke(CommandMapUtils.getMap(), cmd, this, !(commandInfo.name().equals(cmd)), "astoris");
            }

            register.setAccessible(false);

            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            Map<String, Command> map = (Map) knownCommands.get(CommandMapUtils.getMap());
            for (String cmd : commands) {
                map.put(cmd.toLowerCase(), this);
            }

            knownCommands.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
