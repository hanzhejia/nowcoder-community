package com.jiahz.community.controller;

import com.jiahz.community.entity.User;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.ActivationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * ClassName: LoginController
 *
 * @Author: jiahz
 * @Date: 2023/1/23 22:57
 * @Description:
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功，请尽快前往邮箱激活！");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }

    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model, @PathVariable int userId, @PathVariable String code) {
        int activationResult = userService.activation(userId, code);
        if (activationResult == ActivationEnum.ACTIVATION_SUCCESS.getActivationStatus()) {
            model.addAttribute("msg", "激活成功，您的账号可以正常使用了！");
            model.addAttribute("target", "/login");
        } else if (activationResult == ActivationEnum.ACTIVATION_REPEAT.getActivationStatus()) {
            model.addAttribute("msg", "无效操作，您的账号已经成功激活，请勿重复操作！");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，您提供的激活码错误！");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }
}
