package com.abin.srpc.protocol.enums;

import lombok.Getter;

/**
 * 协议消息状态枚举
 */
@Getter
public enum MessageStatus {
    OK("ok", 20),
    BAD_REQUEST("badRequest", 40),
    BAD_RESPONSE("badResponse", 50);

    private final String text;

    private final int val;

    MessageStatus(String text, int val) {
        this.text = text;
        this.val = val;
    }

    /**
     * 根据 val 获取枚举
     * @param val
     * @return
     */
    public static MessageStatus getEnumByValue(int val) {
        for (MessageStatus anEnum : MessageStatus.values()) {
            if (anEnum.val == val) {
                return anEnum;
            }
        }
        throw new IllegalArgumentException("unknown protocol message status: " + val);
    }
}
