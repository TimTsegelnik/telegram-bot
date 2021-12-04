package com.githab.tbot.telegrambot.client;

import com.githab.tbot.telegrambot.client.dto.PostInfo;

import java.util.List;

public interface JRPostClient {
    /**
     * Find new posts since lastPostId in provided group.
     *
     * @param groupId provided group ID.
     * @param lastPostId provided last post ID.
     * @return the collection of the new {@link PostInfo}.
     */
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
