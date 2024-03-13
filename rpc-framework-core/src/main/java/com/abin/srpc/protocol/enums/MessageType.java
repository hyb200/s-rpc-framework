package com.abin.srpc.protocol.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    MessageType(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     * @param key
     * @return
     */
    public static MessageType getEnumByKey(int key) {
        for (MessageType anEnum : MessageType.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        throw new IllegalArgumentException("unknown protocol message type: " + key);
    }
}
