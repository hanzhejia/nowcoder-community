package com.jiahz.community.util;

import com.jiahz.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * HostHolder
 *
 * @Author: jiahz
 * @Date: 2023/1/29 21:59
 * @Description: 持有用户信息，代替session对象
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
