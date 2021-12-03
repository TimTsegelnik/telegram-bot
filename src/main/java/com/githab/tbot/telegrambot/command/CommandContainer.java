package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.githab.tbot.telegrambot.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {

        commandMap = ImmutableMap.of(
                START.getCommandName(), new StartCommand(telegramUserService, sendBotMessageService),
                STOP.getCommandName(), new StopCommand(telegramUserService, sendBotMessageService),
                HELP.getCommandName(), new HelpCommand(sendBotMessageService),
                NO.getCommandName(), new NoCommand(sendBotMessageService),
                STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService)
        );

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
