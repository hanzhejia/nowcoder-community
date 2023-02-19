package com.jiahz.community.service;

import com.jiahz.community.entity.Message;
import com.jiahz.community.mapper.MessageMapper;
import com.jiahz.community.util.SensitiveWordsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * MessageService
 *
 * @Author: jiahz
 * @Date: 2023/2/18 18:46
 * @Description:
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageMapper.selectConversationList(userId, offset, limit);
    }

    public int getConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> getMessageList(String conversationId, int offset, int limit) {
        return messageMapper.selectMessageList(conversationId, offset, limit);
    }

    public int getMessageCount(String conversationId) {
        return messageMapper.selectMessageCount(conversationId);
    }

    public int getMessageUnreadCount(int userId, String conversationId) {
        return messageMapper.selectMessageUnreadCount(userId, conversationId);
    }

    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveWordsFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    public int readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, 1);
    }


}
