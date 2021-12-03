package com.githab.tbot.telegrambot.client;

import com.githab.tbot.telegrambot.client.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.githab.tbot.telegrambot.client.dto.GroupInfoType.TECH;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for JRGroupClientImplTest")
class JRGroupClientTest {
    private final JRGroupClient groupClient
            = new JRCGroupClientImpl("https://javarush.ru/api/1.0/rest");

    @Test
    public void shouldProperlyGetGroupWithEmptyArgs() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);

        //then
        assertNotNull(groupList);
        assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        //when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        //then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        //given
        GroupCountRequestArgs args = GroupCountRequestArgs.builder()
                .type(TECH)
                .build();

        //when
        Integer groupCount = groupClient.getGroupCount(args);

        //then
        assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        //given
        Integer androidGroupId = 16;

        //when
        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        //then
        assertNotNull(groupById);
        assertEquals(16, groupById.getId());
        assertEquals(TECH, groupById.getType());
        assertEquals("android", groupById.getKey());
    }
}