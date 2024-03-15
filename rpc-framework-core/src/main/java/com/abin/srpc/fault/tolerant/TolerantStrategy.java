package com.abin.srpc.fault.tolerant;

import com.abin.srpc.model.RpcResponse;

import java.util.Map;

public interface TolerantStrategy {

    /**
     * 容错
     * @param context 上下文
     * @param e 异常
     * @return  响应
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
