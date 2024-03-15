package com.abin.provider;

import com.abin.common.service.UserService;
import com.abin.srpc.RpcApplication;
import com.abin.srpc.bootstrap.ProviderBootstrap;
import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import com.abin.srpc.model.ServiceRegisterInfo;
import com.abin.srpc.registry.LocalRegistry;
import com.abin.srpc.registry.Registry;
import com.abin.srpc.registry.RegistryFactory;
import com.abin.srpc.server.HttpServer;
import com.abin.srpc.server.VertxHttpServer;
import com.abin.srpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

public class ProviderExample {
    public static void main(String[] args) {
        //  要注册的服务
        List<ServiceRegisterInfo> registerInfos = new ArrayList<>();

        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        registerInfos.add(serviceRegisterInfo);

        ProviderBootstrap.init(registerInfos);
    }
}
