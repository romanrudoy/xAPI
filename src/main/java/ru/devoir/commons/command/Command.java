package ru.devoir.commons.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.devoir.commons.command.objects.CommandInfo;
import ru.devoir.commons.command.objects.CommandIssuer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Command {

    private final Map<String, Consumer<CommandInfo>> subCommands = new HashMap<>();
    private Consumer<CommandInfo> noArgs;
    private Consumer<CommandInfo> unknownArgument;
    private String label;
    private List<String> aliases;

    public Command withLabel(String label) {
        this.label = label;
        return this;
    }

    public Command withAliases(String... aliases) {
        this.aliases = List.of(aliases);
        return this;
    }

    public Command withSubCommand(String name, Consumer<CommandInfo> func) {
        this.subCommands.put(name, func);
        return this;
    }

    public Command withNoArgumentsMethod(Consumer<CommandInfo> noArgs) {
        this.noArgs = noArgs;
        return this;
    }

    public Command withUnknownArgumentMethod(Consumer<CommandInfo> unknownArgumentMethod) {
        this.unknownArgument = unknownArgumentMethod;
        return this;
    }

    public void execute(CommandSender sender, String[] args) {

        CommandInfo info = new CommandInfo().of(new CommandIssuer(sender), args);

        if (args.length == 0) {
            noArgs.accept(info);
            return;
        }

        String first = args[0].toLowerCase();
        if (!subCommands.containsKey(first)) {
            unknownArgument.accept(info);
            return;
        }

        subCommands.get(first).accept(info);

    }

    public List<String> getTabComplete(String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length < 2)
            result.addAll(subCommands.keySet());
        else
            result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));

        return result;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getAliases() {
        return aliases;
    }

}
