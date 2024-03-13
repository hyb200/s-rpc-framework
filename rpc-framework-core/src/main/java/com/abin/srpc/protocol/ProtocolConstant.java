package com.abin.srpc.protocol;

public final class ProtocolConstant {
    private ProtocolConstant() {}

    /**
     * 消息头长度
     */
    public static final int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    public static final byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    public static final byte PROTOCOL_VERSION = 0x1;
}
