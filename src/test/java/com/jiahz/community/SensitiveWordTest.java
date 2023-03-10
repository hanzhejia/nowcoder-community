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
        String text = "θΏιε―δ»₯πθ΅πεποΌθΏιε―δ»₯πε«πε¨ΌοΌθΏιε―δ»₯εΈζ―";
        text = sensitiveWordsFilter.filter(text);
        System.out.println(text);
    }
}
