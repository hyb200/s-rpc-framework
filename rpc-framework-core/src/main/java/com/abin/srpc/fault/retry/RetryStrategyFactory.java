package com.abin.srpc.fault.retry;

import com.abin.srpc.fault.retry.strategy.NoRetryStrategy;
import com.abin.srpc.spi.SpiLoader;

public class RetryStrategyFactory {
    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    public static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
