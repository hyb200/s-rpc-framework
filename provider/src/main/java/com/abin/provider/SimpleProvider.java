package com.abin.provider;

import com.abin.common.service.UserService;
import com.abin.registry.LocalRegistry;
import com.abin.server.HttpServer;
import com.abin.server.VertxHttpServer;

/**
 * 服务提供者示例
 */
public class SimpleProvider {
    public static void main(String[] args) {
        //  注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //  启动服务
        HttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.init(8080);
    }
}
