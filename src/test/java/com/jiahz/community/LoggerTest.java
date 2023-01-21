package com.jiahz.community;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: LoggerTest
 *
 * @Author: jiahz
 * @Date: 2023/1/20 13:53
 * @Description:
 */
@SpringBootTest
public class LoggerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger () {
        System.out.println(LOGGER.getName());
        LOGGER.trace("trace logger");
        LOGGER.debug("debug logger");
        LOGGER.info("info logger");
        LOGGER.warn("warn logger");
        LOGGER.error("error logger");
    }
}
