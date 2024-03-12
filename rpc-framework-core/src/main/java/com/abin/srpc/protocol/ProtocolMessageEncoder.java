package com.abin.srpc.protocol;

import com.abin.srpc.protocol.enums.ProtocolMessageSerializerEnum;
import com.abin.srpc.serializer.Serializer;
import com.abin.srpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

public class ProtocolMessageEncoder {

    /**
     * 编码，将协议内容编码为 Buffer 发送
     * @param protocolMessage
     * @return
     * @throws IOException
     */
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        if (protocolMessage == null || protocolMessage.getHeader() == null) {
            return Buffer.buffer();
        }

        ProtocolMessage.Header header = protocolMessage.getHeader();
        //  向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());

        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("序列化消息协议不存在");
        }

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getVal());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());

        buffer.appendInt(header.getBodyLength());
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}
