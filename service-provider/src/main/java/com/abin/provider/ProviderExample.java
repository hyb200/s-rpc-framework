package com.abin.provider;

import com.abin.common.service.UserService;
import com.abin.srpc.RpcApplication;
import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import com.abin.srpc.registry.LocalRegistry;
import com.abin.srpc.registry.Registry;
import com.abin.srpc.registry.RegistryFactory;
import com.abin.srpc.server.HttpServer;
import com.abin.srpc.server.VertxHttpServer;
import com.abin.srpc.server.tcp.VertxTcpServer;

public class ProviderExample {
    public static void main(String[] args) {
        //  RPC 框架初始化
        RpcApplication.init();

        //  注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        //  注册到服务中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //  启动 web 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.init(9999);
    }
}
