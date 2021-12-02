package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.bot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for SendBotMessageServiceImpl")
class SendBotMessageServiceImplTest {

    private SendBotMessageService sendBotMessageService;
    private TelegramBot telegramBot;

    @BeforeEach
    public void init(){
        telegramBot = Mockito.mock(TelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    }

    @Test
    void shouldProperlySendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}