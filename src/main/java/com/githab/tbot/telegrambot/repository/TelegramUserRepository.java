package com.githab.tbot.telegrambot.repository;

import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {

    List<TelegramUser> findAllByActiveTrue();

    List<TelegramUser> findAllByActiveFalse();

}
