package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.githab.tbot.telegrambot.command.CommandName.STAT;
import static java.lang.String.format;

public class AdminHelpCommand implements Command{
    public static final String ADMIN_HELP_MESSAGE = format(
            "✨ <b> Available admin commands </b> ✨ \n\n"
            + "<b> Get statistics </b> \n"
            + "%s - bot statistics \n",
            STAT.getCommandName());

    private final SendBotMessageService sendBotMessageService;

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, ADMIN_HELP_MESSAGE);
    }
}
