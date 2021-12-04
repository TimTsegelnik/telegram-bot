package com.githab.tbot.telegrambot.command;

import com.githab.tbot.telegrambot.repository.entity.GroupSub;
import com.githab.tbot.telegrambot.repository.entity.TelegramUser;
import com.githab.tbot.telegrambot.service.GroupSubService;
import com.githab.tbot.telegrambot.service.SendBotMessageService;
import com.githab.tbot.telegrambot.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.githab.tbot.telegrambot.command.CommandName.DELETE_GROUP_SUB;
import static com.githab.tbot.telegrambot.command.CommandUtils.getChatId;
import static com.githab.tbot.telegrambot.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService,
                                 TelegramUserService telegramUserService, GroupSubService
                                         groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);

        if (isNumeric(groupId)){
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if (optionalGroupSub.isPresent()){
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, format("I deleted subscribe on group: %s",groupSub.getTitle()));
            }else {
                sendBotMessageService.sendMessage(chatId, "I didn't find that group =/");
            }
        }else {
            sendBotMessageService.sendMessage(chatId, "It's wrong format for group ID. \n");
        }
    }
    private void sendGroupIdList(String chatId){
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)){
            message = "At this moment you don't have any subscribe";
        }else {
            String userGroupSubData = groupSubs.stream()
                    .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());

            message = String.format( "If you want to delete your subscribe - enter the command with group's ID. \n" +
                    "Example: /deleteGroupSub 16 \n\n" +
                    "There is the list with all your groups \n\n" +
                    " group name - group ID \n\n" +
                    "%s", userGroupSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
