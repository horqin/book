package com.example.book.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.book.domain.User;
import com.example.book.mapper.UserMapper;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${properties.salt}")
    private String salt;

    @Override
    public boolean login(User user) {
        user.setPassword(SaSecureUtil.sha256(salt + user.getPassword()));
        User match = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword()));
        if (match != null) {
            Long id = match.getId();
            StpUtil.login(id);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public boolean register(User user) {
        user.setPassword(SaSecureUtil.sha256(salt + user.getPassword()));
        try {
            this.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User currentUser() {
        Long id = StpUtil.getLoginIdAsLong();
        return this.getById(id);
    }
}
