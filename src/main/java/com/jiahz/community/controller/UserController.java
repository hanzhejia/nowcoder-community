package com.jiahz.community.controller;

import com.jiahz.community.annotation.LoginRequired;
import com.jiahz.community.entity.User;
import com.jiahz.community.enums.EntityTypeEnum;
import com.jiahz.community.service.FollowService;
import com.jiahz.community.service.LikeService;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.CommunityUtil;
import com.jiahz.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * UserController
 *
 * @Author: jiahz
 * @Date: 2023/1/29 22:43
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domainPath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @LoginRequired
    @GetMapping("/setting")
    public String getSettingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @GetMapping("/profile")
    public String getProfilePage() {
        return "/site/profile";
    }

    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "????????????????????????!");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (StringUtils.isBlank(suffix) || !(suffix.equals("png") || suffix.equals("jpg") || suffix.equals("jpeg"))) {
            model.addAttribute("error", "?????????????????????!");
            return "/site/setting";
        }

        // ?????????????????????
        fileName = CommunityUtil.generateUUID() + suffix;
        File target = new File(uploadPath + "/" + fileName);
        try {
            // ????????????
            headerImage.transferTo(target);
        } catch (IOException e) {
            log.error("??????????????????: " + e.getMessage());
            throw new RuntimeException("????????????????????????????????????!", e);
        }

        // ?????????????????????????????????
        User user = hostHolder.getUser();
        String headerUrl = domainPath + contextPath + "/user/header/" + fileName;
        userService.updateHeaderUrl(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        response.setContentType("/image/" + suffix);
        try (
                // ??????try????????????????????????????????????finally?????????
                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("?????????????????????" + e.getMessage());
        }
    }

    @PostMapping("/updatePassword")
    public String updatePassword(String oldPassword, String newPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        if (map == null || map.isEmpty()) {
            return "redirect:/logout";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }
    }

    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("??????????????????");
        }
        // ??????
        model.addAttribute("user", user);
        // ????????????
        int likeCount = likeService.getUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);
        // ????????????
        long followeeCount = followService.getFolloweeCount(userId, EntityTypeEnum.ENTITY_TYPE_USER.getEntityType());
        model.addAttribute("followeeCount", followeeCount);
        // ????????????
        long followerCount = followService.getFollowerCount(EntityTypeEnum.ENTITY_TYPE_USER.getEntityType(), userId);
        model.addAttribute("followerCount", followerCount);
        // ????????????????????????????????????
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed
                    (hostHolder.getUser().getId(), EntityTypeEnum.ENTITY_TYPE_USER.getEntityType(), userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);
        return "site/profile";

    }

}
