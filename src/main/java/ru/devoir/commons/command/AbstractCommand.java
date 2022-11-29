package ru.devoir.commons.command;

import org.bukkit.command.CommandSender;
import ru.devoir.commons.utils.CommandMapUtils;

import java.util.List;

public class AbstractCommand {

    protected Command command;

    public void register() {

        if (command == null) return;

        CommandMapUtils.getMap().register(command.getLabel(), new org.bukkit.command.Command(command.getLabel(), "Command created with DevoirAPI",  "/" + command.getLabel(), command.getAliases()) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                command.execute(commandSender, strings);
                return true;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                return command.getTabComplete(args);
            }
        });

    }

}
