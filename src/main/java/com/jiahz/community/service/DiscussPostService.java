package com.jiahz.community.service;

import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.mapper.DiscussPostMapper;
import com.jiahz.community.util.SensitiveWordsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    public List<DiscussPost> getDiscussPosts(int userId, int current, int size) {
        return discussPostMapper.selectDiscussPosts(userId, current, size);
    }

    public int getDiscussPostCount(int userId) {
        return discussPostMapper.selectDiscussPostCount(userId);
    }

    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        // 过滤敏感词
        post.setTitle(sensitiveWordsFilter.filter(post.getTitle()));
        post.setContent(sensitiveWordsFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost getDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }
}
