package com.githab.tbot.telegrambot.client.dto;

import lombok.Data;

@Data
public class MeGroupInfo {
    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
