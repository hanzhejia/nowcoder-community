package com.jiahz.community;

import com.jiahz.community.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MessageMapperTest
 *
 * @Author: jiahz
 * @Date: 2023/2/18 18:28
 * @Description:
 */
@SpringBootTest
public class MessageMapperTest {

    @Autowired
    private MessageMapper mapper;

    @Test
    public void testConversation() {
        mapper.selectConversationList(111, 0, 5);
    }

}
