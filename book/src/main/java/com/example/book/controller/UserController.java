package com.example.book.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.book.domain.User;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public SaResult login(@RequestBody User user) {
        if (userService.login(user)) {
            return SaResult.ok();
        }
        return SaResult.error("用户名或者密码错误");
    }

    @PostMapping("/logout")
    @ResponseBody
    public SaResult logout() {
        if (userService.logout()) {
            return SaResult.ok();
        }
        return SaResult.error("用户登出失败");
    }

    @PostMapping("/register")
    @ResponseBody
    public SaResult register(@RequestBody User user) {
        if (userService.register(user)) {
            return SaResult.ok();
        }
        return SaResult.error("用户已经注册");
    }
}
