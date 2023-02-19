package com.jiahz.community.controller;

import com.jiahz.community.entity.Message;
import com.jiahz.community.entity.Page;
import com.jiahz.community.entity.User;
import com.jiahz.community.service.MessageService;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.CommunityUtil;
import com.jiahz.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * MessageController
 *
 * @Author: jiahz
 * @Date: 2023/2/18 18:48
 * @Description:
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @GetMapping("/conversation/list")
    public String getConversationList(Model model, Page page) {
        User user = hostHolder.getUser();
        page.setLimit(5);
        page.setPath("/conversation/list");
        page.setRows(messageService.getConversationCount(user.getId()));

        List<Message> conversationList = messageService.getConversationList(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("conversation", message);
                map.put("messageCount", messageService.getMessageCount(message.getConversationId()));
                map.put("unreadMessageCount", messageService.getMessageUnreadCount(user.getId(), message.getConversationId()));
                // 将与当前用户会话中的目标用户放入集合
                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                map.put("target", userService.getUserById(targetId));

                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);
        int unreadCount = messageService.getMessageUnreadCount(user.getId(), null);
        model.addAttribute("unreadCount", unreadCount);

        return "/site/letter";
    }

    @GetMapping("/conversation/detail/{conversationId}")
    public String getConversationDetail(@PathVariable("conversationId") String conversationId, Model model, Page page) {
        page.setLimit(5);
        page.setPath("/conversation/detail/" + conversationId);
        page.setRows(messageService.getMessageCount(conversationId));

        // 私信列表
        List<Message> messageList = messageService.getMessageList(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> messages = new ArrayList<>();
        if (messageList != null) {
            for (Message message : messageList) {
                Map<String, Object> map = new HashMap<>();
                map.put("message", message);
                map.put("fromUser", userService.getUserById(message.getFromId()));
                messages.add(map);
            }
        }
        model.addAttribute("messages", messages);

        // 获取与当前用户会话的目标用户信息
        String[] ids = conversationId.split("_");
        int id0 = Integer.parseInt(ids[0]);
        int id1 = Integer.parseInt(ids[1]);
        if (hostHolder.getUser().getId() == id0) {
            model.addAttribute("target", userService.getUserById(id1));
        } else {
            model.addAttribute("target", userService.getUserById(id0));
        }

        // 设置已读
        List<Integer> messageIds = getMessageIds(messageList);
        if (!messageIds.isEmpty()) {
            messageService.readMessage(messageIds);
        }

        return "/site/letter-detail";
    }

    private List<Integer> getMessageIds(List<Message> messageList) {
        List<Integer> ids = new ArrayList<>();
        int userId = hostHolder.getUser().getId();
        if (messageList != null) {
            for (Message message : messageList) {
                if (userId == message.getToId() && message.getStatus() == 0) {
                    ids.add(message.getId());
                }
            }
        }
        return ids;
    }

    @PostMapping("/message/send")
    @ResponseBody
    public String sendMessage(String toName, String content) {
        User targetUser = userService.getUserByUserName(toName);
        if (targetUser == null) {
            return CommunityUtil.getJSONString(1, "目标用户不存在");
        }

        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(targetUser.getId());
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        } else {
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }
        message.setContent(content);
        message.setStatus(0);
        message.setCreateTime(new Date());
        messageService.addMessage(message);

        return CommunityUtil.getJSONString(0);

    }
}
