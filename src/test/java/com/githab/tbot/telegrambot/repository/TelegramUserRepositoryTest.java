package com.githab.tbot.telegrambot.repository;

import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TelegramUserRepositoryTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    public void shouldProbablyFindAllActivesUsers() {
        //when
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        //then
        assertEquals(5, users.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1234567890");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> saveUser = telegramUserRepository.findById(telegramUser.getChatId());

        //then
        Assertions.assertTrue(saveUser.isPresent());
        assertEquals(telegramUser, saveUser.get());
    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveGroupSubsForUser.sql"})
    @Test
    public void shouldProperlyGetAllGroupSubsForUser() {
        //when
        Optional<TelegramUser> userFromDb = telegramUserRepository.findById("1");

        //then
        assertTrue(userFromDb.isPresent());
        List<GroupSub> groupSubs = userFromDb.get().getGroupSubs();
        for (int i = 0; i < groupSubs.size(); i++) {
            assertEquals(String.format("g%s", (i + 1)), groupSubs.get(i).getTitle());
            assertEquals(i + 1, groupSubs.get(i).getId());
            assertEquals(i + 1, groupSubs.get(i).getLastArticleId());
        }

    }
}