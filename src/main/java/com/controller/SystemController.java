package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pangbohuan
 * @description 系统基本功能控制层:登录、退出登录等
 * @date 2019-06-06 14:06
 **/
@RestController
@RequestMapping("/")
public class SystemController {


    /**
     * 测试
     */
    @GetMapping("test")
    public String test() {
        return "正常运行";
    }


    @PostMapping("login")
    public String login() {
        return "登录成功";
    }
}
