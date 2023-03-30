package com.babijon.commons.command.impl;

import com.babijon.commons.command.CommandInfo;
import com.babijon.commons.command.ICommand;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "test", aliases = {"testcmd", "tcmd"})
public class TestCommand extends ICommand {

    public TestCommand() {
        super("test");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§fSuccess!");
    }

}
