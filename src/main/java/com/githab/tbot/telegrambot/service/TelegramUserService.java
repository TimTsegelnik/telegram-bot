package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {

    void save(TelegramUser telegramUser);

    List<TelegramUser> retrieveAllActiveUsers();

    List<TelegramUser> retrieveAllInActiveUsers();

    Optional<TelegramUser>findByChatId(String chatId);


}
