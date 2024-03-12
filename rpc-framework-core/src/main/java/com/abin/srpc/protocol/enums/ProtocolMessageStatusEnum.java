package com.abin.srpc.protocol.enums;

import lombok.Getter;

@Getter
public enum ProtocolMessageStatusEnum {
    OK("ok", 20),
    BAD_REQUEST("badRequest", 40),
    BAD_RESPONSE("badResponse", 50);

    private final String text;

    private final int val;

    ProtocolMessageStatusEnum(String text, int val) {
        this.text = text;
        this.val = val;
    }

    /**
     * 根据 val 获取枚举
     * @param val
     * @return
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int val) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.val == val) {
                return anEnum;
            }
        }
        return null;
    }
}
