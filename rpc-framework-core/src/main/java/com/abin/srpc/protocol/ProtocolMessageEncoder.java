package com.abin.srpc.protocol;

import com.abin.srpc.protocol.enums.MessageSerializer;
import com.abin.srpc.serializer.Serializer;
import com.abin.srpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

public class ProtocolMessageEncoder {

    /**
     * 编码，将协议内容编码为 Buffer 发送
     * @param protocolMessage 消息
     * @return 编码后的 buffer
     * @throws IOException IO异常
     */
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        if (protocolMessage == null || protocolMessage.getHeader() == null) {
            return Buffer.buffer();
        }

        ProtocolMessage.Header header = protocolMessage.getHeader();
        //  向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        appendHeader(buffer, header);

        MessageSerializer serializerEnum = MessageSerializer.getEnumByKey(header.getSerializer());

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getVal());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());

        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }

    private static void appendHeader(Buffer buffer, ProtocolMessage.Header header) {
        //  | magic 1| version 1| serializer 1| type 1| status 1|
        //  |                   requestId                      8|
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());
    }
}
