package com.abin.provider;

import com.abin.common.model.User;
import com.abin.common.service.UserService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println(user.getName());
        return user;
    }
}
