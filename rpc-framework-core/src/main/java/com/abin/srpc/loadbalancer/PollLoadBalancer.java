package com.abin.srpc.loadbalancer;

import com.abin.srpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询
 */
public class PollLoadBalancer implements LoadBalancer{

    /**
     * 原子计数器，防止并发问题
     */
    private final AtomicInteger idx = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(Map<String, Object> params, List<ServiceMetaInfo> serviceMetaInfos) {
        if (serviceMetaInfos.isEmpty()) return null;

        int size = serviceMetaInfos.size();
        if (size == 1) return serviceMetaInfos.get(0);

        return serviceMetaInfos.get(idx.getAndIncrement() % size);
    }
}
