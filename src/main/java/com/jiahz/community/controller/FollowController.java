package com.jiahz.community.controller;

import com.jiahz.community.entity.Page;
import com.jiahz.community.entity.User;
import com.jiahz.community.service.FollowService;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.CommunityUtil;
import com.jiahz.community.util.EntityTypeEnum;
import com.jiahz.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName: FollowController
 *
 * @Author: jiahz
 * @Date: 2023/3/1 15:30
 * @Description:
 */
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/follow")
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已关注");
    }

    @PostMapping("/unfollow")
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已取消关注");
    }

    @GetMapping("/followees/{userId}")
    public String followees(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user", user);

        page.setLimit(5);
        page.setPath("/followees/" + userId);
        page.setRows((int) followService.getFolloweeCount(userId, EntityTypeEnum.ENTITY_TYPE_USER.getEntityType()));

        List<Map<String, Object>> userList = followService.getFollowees(userId, page.getOffset(), page.getLimit());
        if (userList != null) {
            for (Map<String, Object> map : userList) {
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }
        model.addAttribute("followees", userList);

        return "/site/followee";
    }

    @GetMapping("/followers/{userId}")
    public String followers(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user", user);

        page.setLimit(5);
        page.setPath("/followers/" + userId);
        page.setRows((int) followService.getFollowerCount(EntityTypeEnum.ENTITY_TYPE_USER.getEntityType(), userId));

        List<Map<String, Object>> userList = followService.getFollowers(userId, page.getOffset(), page.getLimit());
        if (userList != null) {
            for (Map<String, Object> map : userList) {
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }
        model.addAttribute("followers", userList);

        return "/site/follower";
    }

    private boolean hasFollowed(int userId) {
        if (hostHolder.getUser() == null) {
            return false;
        }
        return followService.hasFollowed(hostHolder.getUser().getId(), EntityTypeEnum.ENTITY_TYPE_USER.getEntityType(), userId);
    }
}
