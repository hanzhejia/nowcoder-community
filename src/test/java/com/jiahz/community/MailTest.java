package com.jiahz.community;

import com.jiahz.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * ClassName: MailTest
 *
 * @Author: jiahz
 * @Date: 2023/1/20 21:12
 * @Description:
 */
@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail () {
        mailClient.sendMail("hanzhejia@outlook.com", "TEST", "send mail from idea");
    }

    @Test
    public void testHtmlMail () {
        Context context = new Context();
        context.setVariable("name", "hanzhejia");
        String content = templateEngine.process("/mail/forget", context);
        System.out.println(content);
        mailClient.sendMail("hanzhejia@outlook.com", "HTML", content);
    }
}
