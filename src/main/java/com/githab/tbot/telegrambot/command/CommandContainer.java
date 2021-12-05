package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.client.JRGroupClient;
import com.githab.tbot.telegrambot.command.annotation.AdminCommand;
import com.githab.tbot.telegrambot.service.GroupSubService;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.githab.tbot.telegrambot.command.CommandName.*;
import static java.util.Objects.nonNull;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;


    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JRGroupClient jrGroupClient, GroupSubService groupSubService,
                            List<String> admins) {
        this.admins = admins;

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(telegramUserService, sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(telegramUserService, sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_GROUP_SUB.getCommandName(),
                        new AddGroupSubCommand(sendBotMessageService, jrGroupClient, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .put(DELETE_GROUP_SUB.getCommandName(),
                        new DeleteGroupSubCommand(sendBotMessageService, telegramUserService, groupSubService))
                .put(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault)){
            if (admins.contains(username)){
                return orDefault;
            }else {
                return unknownCommand;
            }
        }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }
}
