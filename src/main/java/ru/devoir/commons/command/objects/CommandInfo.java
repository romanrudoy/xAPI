package ru.devoir.commons.command.objects;

public class CommandInfo {

    private CommandIssuer issuer;
    private String[] args;

    public CommandInfo of(CommandIssuer issuer, String[] args) {

        this.issuer = issuer;
        this.args = args;

        return this;

    }

    public CommandIssuer getSender() {
        return issuer;
    }

    public String[] getArgs() {
        return args;
    }

}
