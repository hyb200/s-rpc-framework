package com.abin.srpc.fault.tolerant.strategy;

import com.abin.srpc.fault.tolerant.TolerantStrategy;
import com.abin.srpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败，交由外层调用方处理
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务失败", e);
    }
}
