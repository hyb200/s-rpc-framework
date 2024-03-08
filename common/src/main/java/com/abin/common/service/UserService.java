package com.abin.common.service;

import com.abin.common.model.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 根据 ID 获取用户信息
     * @param user
     * @return
     */
    User getUser(User user);

    default int getNumber() {
        return 1;
    }
}
