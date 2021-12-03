package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command{
    public static final String STAT_MESSAGE = "There are %s people are using telegram bot right now!";

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;;

    public StatCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        String chartId = update.getMessage().getChatId().toString();
        String message = String.format(STAT_MESSAGE, activeUserCount);
        sendBotMessageService.sendMessage(chartId, message);
    }
}
