package com.abin.srpc.loadbalancer;

import com.abin.srpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     * @param params    请求参数
     * @param serviceMetaInfos  可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> params, List<ServiceMetaInfo> serviceMetaInfos);
}
