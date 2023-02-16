package com.jiahz.community.controller;

import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.entity.User;
import com.jiahz.community.service.DiscussPostService;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.CommunityUtil;
import com.jiahz.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * DiscussPostController
 *
 * @Author: jiahz
 * @Date: 2023/2/14 21:11
 * @Description:
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "未登录");
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setType(0);
        post.setStatus(0);
        post.setCreateTime(new Date());
        post.setCommentCount(0);
        post.setScore(0.0);
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJSONString(0, "发布成功");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable("discussPostId") int id, Model model) {
        DiscussPost post = discussPostService.getDiscussPostById(id);
        model.addAttribute("post", post);
        User user = userService.getUserById(post.getUserId());
        model.addAttribute("user", user);

        return "/site/discuss-detail";
    }

}
