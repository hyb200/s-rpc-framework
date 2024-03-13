package com.abin.srpc.protocol.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 协议消息序列化器枚举
 */
@Getter
public enum MessageSerializer {
    JDK(0, "jdk"),
    JSON(1, "json"),
    KRYO(2, "kryo"),
    HESSIAN(3, "hessian");

    private final int key;
    private final String val;

    MessageSerializer(int key, String val) {
        this.key = key;
        this.val = val;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MessageSerializer::getVal).collect(Collectors.toList());
    }

    /**
     * 根据 key 获取枚举
     *
     * @param key
     * @return
     */
    public static MessageSerializer getEnumByKey(int key) {
        for (MessageSerializer anEnum : MessageSerializer.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        throw new IllegalArgumentException("unknown protocol message serializer key: " + key);
    }

    /**
     * 根据 val 获取枚举
     *
     * @param val
     * @return
     */
    public static MessageSerializer getEnumByValue(String val) {
        if (ObjectUtil.isEmpty(val)) {
            throw new NullPointerException("protocol message serializer value can not be empty");
        }
        for (MessageSerializer anEnum : MessageSerializer.values()) {
            if (anEnum.val.equals(val)) {
                return anEnum;
            }
        }
        throw new IllegalArgumentException("unknown protocol message serializer value: " + val);
    }
}
