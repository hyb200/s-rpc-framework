package com.abin.srpc.protocol;

import com.abin.srpc.model.RpcRequest;
import com.abin.srpc.model.RpcResponse;
import com.abin.srpc.protocol.enums.MessageSerializer;
import com.abin.srpc.protocol.enums.MessageType;
import com.abin.srpc.serializer.Serializer;
import com.abin.srpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

public class ProtocolMessageDecoder {

    /**
     * 解码
     * @param buffer
     * @return
     */
    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException {
        ProtocolMessage.Header header = buildHeader(buffer);

        //  解决粘包问题，只读取指定长度的数据
        byte[] bodyBytes = buffer.getBytes(ProtocolConstant.MESSAGE_HEADER_LENGTH, ProtocolConstant.MESSAGE_HEADER_LENGTH + header.getBodyLength());
        MessageSerializer serializerEnum = MessageSerializer.getEnumByKey(header.getSerializer());
        MessageType messageTypeEnum = MessageType.getEnumByKey(header.getType());

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getVal());
        switch (messageTypeEnum) {
            case REQUEST:
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                return new ProtocolMessage<>(header, request);
            case RESPONSE:
                RpcResponse response = serializer.deserialize(bodyBytes, RpcResponse.class);
                return new ProtocolMessage<>(header, response);
            case HEART_BEAT:
            case OTHERS:
            default:
                throw new RuntimeException("暂不支持该消息类型");
        }
    }

    private static ProtocolMessage.Header buildHeader(Buffer buffer) {
        //  | magic 1| version 1| serializer 1| type 1| status 1|
        //  |                   requestId                      8|
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        //  校验魔数
        if (magic != ProtocolConstant.PROTOCOL_MAGIC) {
            throw new RuntimeException("消息 magic 非法");
        }

        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5));
        header.setBodyLength(buffer.getInt(13));

        return header;
    }
}
