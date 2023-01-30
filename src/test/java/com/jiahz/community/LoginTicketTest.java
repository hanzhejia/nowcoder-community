package com.jiahz.community;

import com.jiahz.community.entity.LoginTicket;
import com.jiahz.community.mapper.LoginTicketMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author: jiahz
 * @Date: 2023/1/27 10:21
 */
@SpringBootTest
public class LoginTicketTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsert() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(123);
        loginTicket.setTicket("abcdefg");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testupdate() {
        loginTicketMapper.selectByTicket("abcdefg");
        loginTicketMapper.updateStatus("abcdefg", 1);
        loginTicketMapper.selectByTicket("abcdefg");

    }

}
