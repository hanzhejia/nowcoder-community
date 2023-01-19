package com.jiahz.community.service;

import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.mapper.DiscussPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DiscussPostService
 *
 * @Author: jiahz
 * @Date: 2023/1/19 15:50
 * @Description:
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> getDiscussPosts(int userId, int current, int size) {
        return discussPostMapper.selectDiscussPosts(userId, current, size);
    }

    public int getDiscussPostCount(int userId) {
        return discussPostMapper.selectDiscussPostCount(userId);
    }

}
