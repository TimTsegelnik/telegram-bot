package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.client.JRGroupClient;
import com.githab.tbot.telegrambot.client.dto.GroupDiscussionInfo;
import com.githab.tbot.telegrambot.client.dto.GroupRequestArgs;
import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.service.GroupSubService;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.githab.tbot.telegrambot.command.CommandName.ADD_GROUP_SUB;
import static com.githab.tbot.telegrambot.command.CommandUtils.getChatId;
import static com.githab.tbot.telegrambot.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AddGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final JRGroupClient jrGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JRGroupClient jrGroupClient, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.jrGroupClient = jrGroupClient;
        this.groupSubService = groupSubService;
    }


    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(groupId)){
            GroupDiscussionInfo groupById = jrGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())){
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub saveGroupSub = groupSubService.save(chatId, groupById);
            String message = "Subscribed on group " + saveGroupSub.getTitle();
            sendBotMessageService.sendMessage(chatId, message);
        }else {
            sendGroupNotFound(chatId, groupId);
        }

    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFound = "Undefined group by ID = \"%s\"";
        String message = String.format(groupNotFound, groupId);
        sendBotMessageService.sendMessage(chatId, message);
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = jrGroupClient.getGroupList(GroupRequestArgs.builder().build())
                .stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "If you are going to subscribe on some group. \n"
                +   "enter group's name - ID of group";
    }
}
