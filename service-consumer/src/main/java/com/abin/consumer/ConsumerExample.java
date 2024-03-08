package com.abin.consumer;

import com.abin.common.model.User;
import com.abin.common.service.UserService;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.proxy.ServiceProxyFactory;
import com.abin.srpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("ikun");

        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.printf("newUser: %s\n", newUser.getName());
        } else {
            System.out.println("user is null");
        }
        int number = userService.getNumber();
        System.out.println(number);
    }
}
