package com.githab.tbot.telegrambot.client;

import com.githab.tbot.telegrambot.client.dto.PostInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.githab.tbot.telegrambot.client.JRGroupClientTest.API_PATH;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for JRPostClient")
class JRPostClientTest {

    private final JRPostClient postClient = new JRPostClientImpl(API_PATH);

    @Test
    public void shouldProperlyGetNew15Posts(){
        //when
        List<PostInfo> newPosts = postClient.findNewPosts(30, 2935);

        //then
        assertEquals(15, newPosts.size());
    }
}