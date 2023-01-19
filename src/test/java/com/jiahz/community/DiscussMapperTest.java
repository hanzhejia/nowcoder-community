package com.jiahz.community;

import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.mapper.DiscussPostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ClassName: DiscussMapperTest
 *
 * @Author: jiahz
 * @Date: 2023/1/19 15:43
 * @Description:
 */
@SpringBootTest
public class DiscussMapperTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testGetDiscussPost () {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost);
        }

        int discussPostCount = discussPostMapper.selectDiscussPostCount(0);
        System.out.println(discussPostCount);
    }
}


