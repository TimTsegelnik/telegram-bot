package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.githab.tbot.telegrambot.command.CommandUtils.getChatId;

public class ListGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public void execute(Update update) {
        //todo add exception handling
        TelegramUser telegramUser = telegramUserService
                .findByChatId(getChatId(update))
                .orElseThrow(NotFoundException::new);
        String message = "I found all subscribes for groups: \n\n %s";
        String collect = telegramUser.getGroupSubs().stream()
                .map(it -> "Group: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        String formatMessage = String.format(message, collect);

        sendBotMessageService.sendMessage(telegramUser.getChatId(), formatMessage);
    }
}
