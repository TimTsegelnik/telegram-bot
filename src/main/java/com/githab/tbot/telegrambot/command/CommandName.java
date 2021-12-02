package com.githab.tbot.telegrambot.command;

public enum CommandName {
    NO(""),
    HELP("/help"),
    START("/start"),
    STOP("/stop");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
