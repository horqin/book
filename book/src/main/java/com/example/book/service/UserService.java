package com.example.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book.domain.User;

public interface UserService extends IService<User> {

    /**
     * 注册
     * @param user 用户
     * @return 成功或失败
     */
    boolean register(User user);

    /**
     * 登录
     * @param user 用户
     * @return 成功或失败
     */
    boolean login(User user);

    /**
     * 是否登录
     * @return 成功或失败
     */
    boolean isLogin();

    /**
     * 获取当前用户
     * @return 当前用户
     */
    User currentUser();
}
