package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    private static final String NO_MESSAGE = "I'm executing commands which start with(/).\n" +
            "In case you want to see list of commands inter: /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        String chartId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chartId, NO_MESSAGE);
    }
}
