package com.abin.srpc.fault.tolerant;

import com.abin.srpc.fault.retry.RetryStrategy;
import com.abin.srpc.fault.retry.strategy.NoRetryStrategy;
import com.abin.srpc.fault.tolerant.strategy.FailFastTolerantStrategy;
import com.abin.srpc.spi.SpiLoader;

/**
 * 容错策略工厂
 */
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错
     */
    public static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
