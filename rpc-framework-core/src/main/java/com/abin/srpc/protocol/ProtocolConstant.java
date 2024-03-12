package com.abin.srpc.protocol;

public final class ProtocolConstant {
    private ProtocolConstant() {}

    public static final int MESSAGE_HEADER_LENGTH = 17;

    public static final byte PROTOCOL_MAGIC = 0x1;

    public static final byte PROTOCOL_VERSION = 0x1;
}
