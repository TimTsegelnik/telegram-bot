package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.client.dto.GroupDiscussionInfo;
import com.githab.tbot.telegrambot.repository.entity.GroupSub;

public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
