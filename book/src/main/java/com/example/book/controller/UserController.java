package com.example.book.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.book.domain.User;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public SaResult login(@Valid @RequestBody User user) {
        if (userService.login(user)) {
            return SaResult.ok();
        }
        return SaResult.error("用户无法登录");
    }

    @PostMapping("/isLogin")
    @ResponseBody
    public SaResult isLogin() {
        boolean isLogin = userService.isLogin();
        return SaResult.ok().setData(isLogin);
    }

    @PostMapping("/register")
    @ResponseBody
    public SaResult register(@Valid @RequestBody User user) {
        if (userService.register(user)) {
            return SaResult.ok();
        }
        return SaResult.error("用户已经注册");
    }
}
