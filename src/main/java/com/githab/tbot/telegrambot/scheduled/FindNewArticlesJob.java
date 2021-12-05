package com.githab.tbot.telegrambot.scheduled;

import com.githab.tbot.telegrambot.service.FindNewArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class FindNewArticlesJob {

    private final FindNewArticlesService findNewArticleService;

    @Autowired
    public FindNewArticlesJob(FindNewArticlesService findNewArticleService) {
        this.findNewArticleService = findNewArticleService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewArticleFixedRate}")
    public void findNewArticles() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Find new article job started.");

        findNewArticleService.findNewArticles();

        LocalDateTime end = LocalDateTime.now();

        log.info("Find new articles job finished. Took seconds: {}",
                getTimeOfWork(start, end));
    }

    private long getTimeOfWork(LocalDateTime start, LocalDateTime end) {
        return end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC);
    }
}
