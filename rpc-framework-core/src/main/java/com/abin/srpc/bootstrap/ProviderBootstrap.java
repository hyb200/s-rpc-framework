package com.abin.srpc.bootstrap;

import com.abin.srpc.RpcApplication;
import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import com.abin.srpc.model.ServiceRegisterInfo;
import com.abin.srpc.registry.LocalRegistry;
import com.abin.srpc.registry.Registry;
import com.abin.srpc.registry.RegistryFactory;
import com.abin.srpc.server.tcp.VertxTcpServer;

import java.util.List;

public class ProviderBootstrap {

    public static void init(List<ServiceRegisterInfo> serviceRegisterInfos) {
        //  RPC 框架初始化
        RpcApplication.init();
        //  全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfos) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //  本地注册服务
            LocalRegistry.register(serviceName, serviceRegisterInfo.getServiceImplClass());

            //  注册到服务中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }

            //  启动 web 服务
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.init(rpcConfig.getServerPort());
        }
    }
}
