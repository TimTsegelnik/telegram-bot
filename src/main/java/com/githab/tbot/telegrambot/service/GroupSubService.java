package com.githab.tbot.telegrambot.service;

import com.githab.tbot.telegrambot.client.dto.GroupDiscussionInfo;
import com.githab.tbot.telegrambot.repository.entity.GroupSub;

import java.util.Optional;

public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer id);
}
