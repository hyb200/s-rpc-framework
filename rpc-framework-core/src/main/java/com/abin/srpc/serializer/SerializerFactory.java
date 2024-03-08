package com.abin.srpc.serializer;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {

    /**
     * 序列化映射
     */
    private static final Map<String, Serializer> SERIALIZER_MAP =
            new HashMap<String, Serializer>() {{
                put(SerializerKeys.JDK, new JdkSerializer());
                put(SerializerKeys.JSON, new JsonSerializer());
                put(SerializerKeys.KRYO, new KryoSerializer());
                put(SerializerKeys.HESSIAN, new HessianSerializer());
            }};

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULE_SERIALIZER = SERIALIZER_MAP.get(SerializerKeys.JDK);

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SERIALIZER_MAP.getOrDefault(key, DEFAULE_SERIALIZER);
    }
}
