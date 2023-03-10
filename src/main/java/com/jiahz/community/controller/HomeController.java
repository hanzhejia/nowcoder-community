package com.jiahz.community.controller;

import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.entity.Page;
import com.jiahz.community.entity.User;
import com.jiahz.community.enums.EntityTypeEnum;
import com.jiahz.community.service.DiscussPostService;
import com.jiahz.community.service.LikeService;
import com.jiahz.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: HomeController
 *
 * @Author: jiahz
 * @Date: 2023/1/19 16:09
 * @Description:
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/index")
    public String index(Model model, Page page) {
        page.setRows(discussPostService.getDiscussPostCount(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.getDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.getUserById(post.getUserId());
                map.put("user", user);
                long likeCount = likeService.getEntityLikeCount(EntityTypeEnum.ENTITY_TYPE_POST.getEntityType(), post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }
}
