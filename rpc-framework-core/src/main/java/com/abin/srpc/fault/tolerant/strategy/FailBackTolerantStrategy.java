package com.abin.srpc.fault.tolerant.strategy;

import com.abin.srpc.fault.tolerant.TolerantStrategy;
import com.abin.srpc.model.RpcResponse;

import java.util.Map;

/**
 * 降级服务策略
 */
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //  todo 获取降级服务并调用
        return null;
    }
}
