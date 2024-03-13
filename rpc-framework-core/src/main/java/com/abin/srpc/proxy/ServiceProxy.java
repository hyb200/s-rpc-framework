package com.abin.srpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.abin.srpc.RpcApplication;
import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.constant.RpcConstant;
import com.abin.srpc.model.RpcRequest;
import com.abin.srpc.model.RpcResponse;
import com.abin.srpc.model.ServiceMetaInfo;
import com.abin.srpc.protocol.ProtocolConstant;
import com.abin.srpc.protocol.ProtocolMessage;
import com.abin.srpc.protocol.ProtocolMessageDecoder;
import com.abin.srpc.protocol.ProtocolMessageEncoder;
import com.abin.srpc.protocol.enums.ProtocolMessageSerializerEnum;
import com.abin.srpc.protocol.enums.ProtocolMessageTypeEnum;
import com.abin.srpc.registry.Registry;
import com.abin.srpc.registry.RegistryFactory;
import com.abin.srpc.serializer.JdkSerializer;
import com.abin.srpc.serializer.Serializer;
import com.abin.srpc.serializer.SerializerFactory;
import com.abin.srpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {

            //  从注册中心获取服务请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());

            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            ServiceMetaInfo info = serviceMetaInfoList.get(0);

            //  发送 TCP 请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, info);
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }
}
