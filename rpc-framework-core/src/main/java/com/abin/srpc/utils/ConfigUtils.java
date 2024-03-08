package com.abin.srpc.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类，用于读取配置文件
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持多环境
     * @param tClass
     * @param prefix
     * @param env
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String env) {
        StringBuilder sb = new StringBuilder("application");
        if (StrUtil.isNotBlank(env)) {
            sb.append("-").append(env);
        }
        sb.append(".properties");
        Props props = new Props(sb.toString());
        return props.toBean(tClass, prefix);
    }
}
