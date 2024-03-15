package com.abin.srpc.bootstrap;

import com.abin.srpc.RpcApplication;

public class ConsumerBootstrap {
    public static void init() {
        //  RPC 框架初始化（配置注册中心）
        RpcApplication.init();
    }
}
