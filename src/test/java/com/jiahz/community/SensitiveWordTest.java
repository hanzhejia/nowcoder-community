package com.jiahz.community;

import com.jiahz.community.util.SensitiveWordsFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SensitiveWordTest
 *
 * @Author: jiahz
 * @Date: 2023/2/14 19:27
 * @Description:
 */
@SpringBootTest
public class SensitiveWordTest {

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    @Test
    public void testSensitive() {
        String text = "这里可以🌟赌🌟博🌟，这里可以🌟嫖🌟娼，这里可以吸毒";
        text = sensitiveWordsFilter.filter(text);
        System.out.println(text);
    }
}
