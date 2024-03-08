package com.abin.srpc;

import com.abin.srpc.config.RpcConfig;
import com.abin.srpc.constant.RpcConstant;
import com.abin.srpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 相当于holder，存放项目全局用到的变量。双检锁单例模式
 */
@Slf4j
public class RpcApplication {
    
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持自定义配置
     * @param config
     */
    public static void init(RpcConfig config) {
        rpcConfig = config;
        log.info("Rpc init, config = {}", config.toString());
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newConfig;
        try {
            newConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            //  加载失败，使用默认值
            newConfig = new RpcConfig();
        }
        init(newConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
