package com.jiahz.community.service;

import com.jiahz.community.entity.User;
import com.jiahz.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 *
 * @Author: jiahz
 * @Date: 2023/1/19 15:57
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }
}
