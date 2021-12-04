package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.githab.tbot.telegrambot.command.CommandName.*;

public class HelpCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format(
            "<b>Available command</b>\n" +
                    "<b>Start\\finish bot working</b>\n" +
                    "%s - start working with me\n" +
                    "%s - stop working with me\n\n" +

                    "Working with group's subscriber\n" +
                    "%s - subscribe on group of articles\n" +
                    "%s - get list you group\\n" +

                    "%s - get help about my work\n" +
                    "%s - get my statistic\n",
    START.getCommandName(),STOP.getCommandName(), ADD_GROUP_SUB.getCommandName(),
    LIST_GROUP_SUB.getCommandName(), HELP.getCommandName(), STAT.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
