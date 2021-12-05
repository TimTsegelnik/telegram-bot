package com.githab.tbot.telegrambot.bot;

import com.githab.tbot.telegrambot.client.JRGroupClient;
import com.githab.tbot.telegrambot.command.CommandContainer;
import com.githab.tbot.telegrambot.service.GroupSubService;
import com.githab.tbot.telegrambot.service.SendBotMessageServiceImpl;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.githab.tbot.telegrambot.command.CommandName.NO;


@Component
public class TBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public TBot(TelegramUserService telegramUserService, JRGroupClient groupClient,
                GroupSubService groupSubService,
                @Value("#{'${bot.admins}'.split(',')}")List<String> admins) {
        this.commandContainer = new CommandContainer(
                new SendBotMessageServiceImpl(this),
                telegramUserService, groupClient, groupSubService,
                admins);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String name = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, name).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), name).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
