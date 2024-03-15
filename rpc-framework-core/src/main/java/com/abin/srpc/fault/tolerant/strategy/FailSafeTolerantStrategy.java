package com.abin.srpc.fault.tolerant.strategy;

import com.abin.srpc.fault.tolerant.TolerantStrategy;
import com.abin.srpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理，记录日志
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.error("静默异常处理", e);
        return new RpcResponse();
    }
}
