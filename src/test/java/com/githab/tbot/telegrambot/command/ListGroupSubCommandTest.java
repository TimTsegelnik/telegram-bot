package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.githab.tbot.telegrambot.command.AbstractCommandTest.prepareUpdate;
import static com.githab.tbot.telegrambot.command.CommandName.LIST_GROUP_SUB;

@DisplayName("Unit-level testing for ListGroupSubCommand")
class ListGroupSubCommandTest {

    @Test
    public void shouldProperlyShowsListGroupSub(){
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId("1");

        List<GroupSub> groupSubs = new ArrayList<>();
        groupSubs.add(populateGroupSub(1, "gs1"));
        groupSubs.add(populateGroupSub(2, "gs2"));
        groupSubs.add(populateGroupSub(3, "gs3"));
        groupSubs.add(populateGroupSub(4, "gs4"));

        telegramUser.setGroupSubs(groupSubs);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = prepareUpdate(Long.valueOf(telegramUser.getChatId()), LIST_GROUP_SUB.getCommandName());

        String joinedGroup = telegramUser.getGroupSubs().stream()
                .map(it -> "Group: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        String collectedGroup = String.format("I found all subscribes for groups: \n\n %s", joinedGroup);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedGroup);

    }

    private GroupSub populateGroupSub(Integer id, String title){
        GroupSub groupSub = new GroupSub();
        groupSub.setId(id);
        groupSub.setTitle(title);
        return groupSub;
    }
}