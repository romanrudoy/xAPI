package ru.devoir.commons.command.impl;

import org.bukkit.entity.Player;
import ru.devoir.commons.command.AbstractCommand;
import ru.devoir.commons.command.Command;
import ru.devoir.commons.gui.impl.TestMenu;
import ru.devoir.commons.gui.impl.TestPagedMenu;

public class TestCommand extends AbstractCommand {

    public TestCommand() {
        this.command = new Command().withLabel("test").withAliases("testcommand", "testcmd")
                .withNoArgumentsMethod(i -> new TestMenu().setPlayer((Player) i.getSender().getBase()).open())
                .withUnknownArgumentMethod(i -> new TestMenu().setPlayer((Player) i.getSender().getBase()).open())
                .withSubCommand("paged", i -> new TestPagedMenu().setPlayer((Player) i.getSender().getBase()).open());
    }

}
