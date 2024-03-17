package com.abin.rpcspringbootstarter.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.abin.srpc.config.RpcConfig;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;

public class YamlConfigUtil {
    public static final String PREFIX = "rpc";
    public static RpcConfig getYamlConfig() {
        boolean exist = FileUtil.exist("classpath:application.yml");

        RpcConfig rpcConfig = null;
        if (exist) {
            YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
            yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
            Map<String, Object> map = yamlMapFactoryBean.getObject();
            if (map != null) {
                rpcConfig = BeanUtil.toBean(map.get(PREFIX), RpcConfig.class);
            }
        } else {
            rpcConfig = new RpcConfig();
        }
        return rpcConfig;
    }

    public static void main(String[] args) {
        System.out.println(YamlConfigUtil.getYamlConfig());
    }
}
