package com.abin.srpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务注册信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRegisterInfo<T> {

    /**
     * 服务名称
     */
    public String serviceName;

    /**
     * 服务实现类
     */
    public Class<? extends T> serviceImplClass;
}
