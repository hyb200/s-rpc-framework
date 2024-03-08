package com.abin.srpc.server;

/**
 * Http 服务器接口
 */
public interface HttpServer {

    /**
     * 初始化服务器
     * @param port
     */
    void init(int port);
}
