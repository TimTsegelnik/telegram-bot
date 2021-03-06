package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.client.JRPostClient;
import com.githab.tbot.telegrambot.client.dto.PostInfo;
import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewArticlesServiceImpl implements FindNewArticlesService {
    public static final String WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JRPostClient jrPostClient;
    private final SendBotMessageService sendBotMessageService;


    public FindNewArticlesServiceImpl(GroupSubService groupSubService,
                                      JRPostClient jrPostClient,
                                      SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.jrPostClient = jrPostClient;
        this.sendBotMessageService = sendMessageService;
    }


    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = jrPostClient.findNewPosts(gSub.getId(), gSub.getLastArticleId());

            setNewLastArticleId(gSub, newPosts);

            notifySubscribersAboutNewArticles(gSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewArticles(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨There is a new article<b>%s</b> in the group <b>%s</b>.✨\n\n" +
                                "<b>Description:</b> %s\n\n" +
                                "<b>Link:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendBotMessageService.sendMessage(it.getChatId(), messagesWithNewArticles));
    }

    private void setNewLastArticleId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastArticleId(id);
                    groupSubService.save(gSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(WEB_POST_FORMAT, key);
    }
}
