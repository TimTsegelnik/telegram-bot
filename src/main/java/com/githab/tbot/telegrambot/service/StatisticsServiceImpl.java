package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.client.dto.GroupStatDTO;
import com.githab.tbot.telegrambot.client.dto.StatisticDTO;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final GroupSubService groupSubService;
    private final TelegramUserService telegramUserService;

    public StatisticsServiceImpl(GroupSubService groupSubService, TelegramUserService telegramUserService) {
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StatisticDTO countBotStatistic() {
        List<GroupStatDTO> groupStatDTOS = groupSubService.findAll().stream()
                .filter(it -> !isEmpty(it.getUsers()))
                .map(groupSub -> new GroupStatDTO(
                        groupSub.getId(),
                        groupSub.getTitle(),
                        groupSub.getUsers().size()
                ))
                .collect(Collectors.toList());
        List<TelegramUser> telegramAllActiveUsers = telegramUserService.retrieveAllActiveUsers();
        List<TelegramUser> telegramAllInActiveUsers = telegramUserService.retrieveAllInActiveUsers();

        double groupPerUser = getGroupPerUser(telegramAllActiveUsers);
        return new StatisticDTO(
                telegramAllActiveUsers.size(),
                telegramAllInActiveUsers.size(),
                groupStatDTOS,
                groupPerUser);
    }

    private double getGroupPerUser(List<TelegramUser> allActiveUsers) {
        return (double) allActiveUsers.stream().mapToInt(it -> it.getGroupSubs().size()).sum() / allActiveUsers.size();
    }
}
