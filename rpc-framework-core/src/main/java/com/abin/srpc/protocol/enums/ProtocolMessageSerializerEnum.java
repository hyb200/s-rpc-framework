package com.abin.srpc.protocol.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum ProtocolMessageSerializerEnum {
    JDK(0, "jdk"),
    JSON(1, "json"),
    KRYO(2, "kryo"),
    HESSIAN(3, "hessian");

    private final int key;
    private final String val;

    ProtocolMessageSerializerEnum(int key, String val) {
        this.key = key;
        this.val = val;
    }

    /**
     * 获取值列表
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.val).collect(Collectors.toList());
    }

    /**
     * 根据 key 获取枚举
     * @param key
     * @return
     */
    public static ProtocolMessageSerializerEnum getEnumByKey(int key) {
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据 val 获取枚举
     * @param val
     * @return
     */
    public static ProtocolMessageSerializerEnum getEnumByValue(String val) {
        if (ObjectUtil.isEmpty(val)) {
            return null;
        }
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if (anEnum.val.equals(val)) {
                return anEnum;
            }
        }
        return null;
    }
}
