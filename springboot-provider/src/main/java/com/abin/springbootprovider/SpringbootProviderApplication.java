package com.abin.springbootprovider;

import com.abin.rpcspringbootstarter.annotation.EnableRpc;
import com.abin.rpcspringbootstarter.utils.YamlConfigUtil;
import com.abin.srpc.RpcApplication;
import com.abin.srpc.config.RpcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc
public class SpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProviderApplication.class, args);
    }

}
