package com.jiahz.community.util;

/**
 * @Author: jiahz
 * @Date: 2023/1/27 10:49
 */
public enum ExpiredEnum {

    DEFAULT_EXPIRED_SECONDS(3600 * 12),
    REMEMBER_EXPIRED_SECONDS(3600 * 24 * 100);

    private int seconds;

    ExpiredEnum(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

}
