package com.abin.srpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import com.abin.srpc.config.RpcConfig;

import java.util.Map;

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
        boolean exist = FileUtil.exist("application.yml");
        if (exist) {
            return loadFromYaml(tClass, prefix, env);
        } else
            return loadFromProperties(tClass, prefix, env);
    }

    public static <T> T loadFromProperties(Class<T> tClass, String prefix, String env) {
        StringBuilder sb = new StringBuilder("application");
        if (StrUtil.isNotBlank(env)) {
            sb.append("-").append(env);
        }
        sb.append(".properties");
        Props props = new Props(sb.toString());
        return props.toBean(tClass, prefix);
    }

    public static <T> T loadFromYaml(Class<T> tClass, String prefix, String env) {
        StringBuilder sb = new StringBuilder("application");
        if (StrUtil.isNotBlank(env)) {
            sb.append("-").append(env);
        }
        sb.append(".yml");
        T config = null;
        Map<String, Object> map = YamlUtil.loadByPath(sb.toString());
        if (map.get(prefix) != null) {
            config = (T) BeanUtil.toBean(map.get(prefix), tClass);
        }
        return config;
    }
}
