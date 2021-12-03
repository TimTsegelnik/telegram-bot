package com.githab.tbot.telegrambot.client.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupDiscussionInfo extends GroupInfo{

    private Integer lastTime;
    private Integer newCommentCount;
}
