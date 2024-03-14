package com.abin.srpc.fault.retry.strategy;

import com.abin.srpc.fault.retry.RetryStrategy;
import com.abin.srpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试策略
 */
public class NoRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
