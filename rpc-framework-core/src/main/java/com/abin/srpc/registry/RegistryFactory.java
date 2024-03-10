package com.abin.srpc.registry;

import com.abin.srpc.serializer.JdkSerializer;
import com.abin.srpc.serializer.Serializer;
import com.abin.srpc.spi.SpiLoader;

/**
 * 序列化器工厂
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULE_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
