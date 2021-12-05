package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.client.dto.GroupStatDTO;
import com.githab.tbot.telegrambot.client.dto.StatisticDTO;
import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for StatisticService")
class StatisticsServiceTest {

    private GroupSubService groupSubService;
    private TelegramUserService telegramUserService;

    private StatisticsService statisticsService;

    @BeforeEach
    public void init(){
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        statisticsService = new StatisticsServiceImpl(groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlySendStatDTO(){
        //given
        Mockito
                .when(telegramUserService.retrieveAllInActiveUsers())
                .thenReturn(singletonList(new TelegramUser()));

        TelegramUser activeUser = new TelegramUser();
        activeUser.setGroupSubs(singletonList(new GroupSub()));

        Mockito
                .when(telegramUserService.retrieveAllActiveUsers())
                .thenReturn(singletonList(activeUser));

        GroupSub groupSub = new GroupSub();
        groupSub.setTitle("group");
        groupSub.setId(1);
        groupSub.setUsers(singletonList(new TelegramUser()));

        Mockito.when(groupSubService.findAll()).thenReturn(singletonList(groupSub));

        //when
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        //then
        assertNotNull(statisticDTO);
        assertEquals(1, statisticDTO.getActiveUserCount());
        assertEquals(1, statisticDTO.getInactiveUserCount());
        assertEquals(1.0, statisticDTO.getAverageGroupCountByUser());
        assertEquals(singletonList(
                new GroupStatDTO(groupSub.getId(), groupSub.getTitle(),groupSub.getUsers().size())),
                statisticDTO.getGroupStatDTOs());
    }
}