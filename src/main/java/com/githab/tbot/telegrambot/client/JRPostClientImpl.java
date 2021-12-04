package com.githab.tbot.telegrambot.client;

import com.githab.tbot.telegrambot.client.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class JRPostClientImpl implements JRPostClient {

    private final String apiPostPath;

    public JRPostClientImpl(@Value("${api.path}") String apiPostPath) {
        this.apiPostPath = apiPostPath + "/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        List<PostInfo> lastPostsByGroup = Unirest.get(apiPostPath)
                .queryString("order", "NEW")
                .queryString("groupFid", groupId)
                .queryString("limit", 15)
                .asObject(new GenericType<List<PostInfo>>() {
                }).getBody();
        List<PostInfo> newPosts = new ArrayList<>();
        for (PostInfo post : lastPostsByGroup) {
            if (lastPostId.equals(post.getId())){
                return newPosts;
            }
            newPosts.add(post);
        }
        return newPosts;
    }
}
