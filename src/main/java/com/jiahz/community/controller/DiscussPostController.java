package com.jiahz.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: DiscussPostController
 *
 * @Author: jiahz
 * @Date: 2023/1/19 15:05
 * @Description:
 */
@Controller
@RequestMapping("/discussPost")
public class DiscussPostController {

    public String hello() {
        return "index";
    }
}
