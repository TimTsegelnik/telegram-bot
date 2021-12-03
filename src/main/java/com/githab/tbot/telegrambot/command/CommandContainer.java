package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.client.JRGroupClient;
import com.githab.tbot.telegrambot.service.GroupSubService;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.githab.tbot.telegrambot.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;


    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JRGroupClient jrGroupClient, GroupSubService groupSubService) {

        AddGroupSubCommand addGroupSubCommand = new AddGroupSubCommand(sendBotMessageService, jrGroupClient, groupSubService);

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(telegramUserService, sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(telegramUserService, sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_GROUP_SUB.getCommandName(),
                        new AddGroupSubCommand(sendBotMessageService, jrGroupClient, groupSubService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
