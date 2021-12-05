package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.client.dto.StatisticDTO;
import com.githab.tbot.telegrambot.command.annotation.AdminCommand;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.StatisticsService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static java.lang.String.format;

@AdminCommand
public class StatCommand implements Command {
    public static final String STAT_MESSAGE =
            "✨ <b> Prepared statistics </b> ✨ \n" +
                    "- Number of active users: %s \n" +
                    "- Number of inactive users: %s \n" +
                    "- Average number of groups per user: %s \n" +
                    "<b> Information on active groups </b>: \n" +
                    "%s";

    private final SendBotMessageService sendBotMessageService;
    private final StatisticsService statisticsService;
    ;

    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroup = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> format("%s (id = %s) - %s subscribers",
                        it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(
                update.getMessage().getChatId().toString(),
                format(STAT_MESSAGE,
                        statisticDTO.getActiveUserCount(),
                        statisticDTO.getInactiveUserCount(),
                        statisticDTO.getAverageGroupCountByUser(),
                        collectedGroup));
    }
}
