package com.githab.tbot.telegrambot.client;

import com.githab.tbot.telegrambot.client.dto.GroupCountRequestArgs;
import com.githab.tbot.telegrambot.client.dto.GroupDiscussionInfo;
import com.githab.tbot.telegrambot.client.dto.GroupInfo;
import com.githab.tbot.telegrambot.client.dto.GroupRequestArgs;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link JRGroupClient} interface.
 */

@Component
public class JRCGroupClientImpl implements JRGroupClient {

    private String apiGroupPath;

    public JRCGroupClientImpl(@Value("${api.path}") String apiGroupPath) {
        this.apiGroupPath = apiGroupPath + "/groups";
    }

    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {
        return Unirest.get(apiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {
        return Unirest.get(apiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>() {
                })
                .getBody();
    }

    @Override
    public Integer getGroupCount(GroupCountRequestArgs countRequestArgs) {
        return Integer.valueOf(
                Unirest.get(String.format("%s/count", apiGroupPath))
                        .queryString(countRequestArgs.populateQueries())
                        .asString()
                        .getBody()
        );
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        String query = String.format("%s/group%s", apiGroupPath, id.toString());
        return Unirest.get(query)
                .asObject(GroupDiscussionInfo.class)
                .getBody();
    }
}
