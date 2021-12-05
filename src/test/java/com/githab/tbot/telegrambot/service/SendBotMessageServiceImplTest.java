package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.bot.TBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageServiceImpl")
class SendBotMessageServiceImplTest {

    private SendBotMessageService sendBotMessageService;
    private TBot tBot;

    @BeforeEach
    public void init(){
        tBot = Mockito.mock(TBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(tBot);
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
        Mockito.verify(tBot).execute(sendMessage);
    }
}