package com.abin.rpcspringbootstarter.bootstrap;

import com.abin.rpcspringbootstarter.annotation.EnableRpc;
import com.abin.srpc.RpcApplication;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.server.tcp.VertxTcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 框架启动
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    /**
     * Spring 初始化时执行，初始化框架
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");
        RpcApplication.init();

        //  全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //  启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.init(rpcConfig.getServerPort());
        } else {
            log.info("Don't need server");
        }
    }
}
