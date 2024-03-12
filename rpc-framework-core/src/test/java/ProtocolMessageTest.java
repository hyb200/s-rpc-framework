import cn.hutool.core.util.IdUtil;
import com.abin.srpc.constant.RpcConstant;
import com.abin.srpc.model.RpcRequest;
import com.abin.srpc.protocol.ProtocolConstant;
import com.abin.srpc.protocol.ProtocolMessage;
import com.abin.srpc.protocol.ProtocolMessageDecoder;
import com.abin.srpc.protocol.ProtocolMessageEncoder;
import com.abin.srpc.protocol.enums.ProtocolMessageSerializerEnum;
import com.abin.srpc.protocol.enums.ProtocolMessageStatusEnum;
import com.abin.srpc.protocol.enums.ProtocolMessageTypeEnum;
import io.vertx.core.buffer.Buffer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ProtocolMessageTest {
    @Test
    public void testEncodeAndDecode() throws IOException {
        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getVal());
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(0);

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("service");
        rpcRequest.setMethodName("method");
        rpcRequest.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        rpcRequest.setParameterTypes(new Class[] {String.class});
        rpcRequest.setArgs(new Object[] {"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);

        Buffer encode = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encode);
        Assert.assertNotNull(message);
    }
}
