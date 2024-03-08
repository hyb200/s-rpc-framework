package com.abin.srpc.proxy;

import com.abin.srpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（创建代理对象）
 */
public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取 Mock 代理对象
     * @param serviceClass
     * @return
     * @param <T>
     */
    private static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
