package com.abin.srpc.fault.tolerant.strategy;

import com.abin.srpc.fault.tolerant.TolerantStrategy;
import com.abin.srpc.model.RpcResponse;

import java.util.Map;

/**
 * 故障转移，调用其它服务节点
 */
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //  todo 故障转移，调用其它服务节点
        return null;
    }
}
