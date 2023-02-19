package com.jiahz.community.mapper;

import com.jiahz.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MessageMapper
 *
 * @Author: jiahz
 * @Date: 2023/2/18 18:08
 * @Description:
 */
@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表, 针对每条会话返回最新的一条消息
    List<Message> selectConversationList(int userId, int offset, int limit);


    // 查询当前用户的会话数量
    int selectConversationCount(int userId);

    // 查询某个会话所包含的私信列表
    List<Message> selectMessageList(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量
    int selectMessageCount(String conversationId);

    // 查询未读私信的数量
    int selectMessageUnreadCount(int userId, String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 修改消息状态
    int updateStatus(List<Integer> ids, int status);
}
