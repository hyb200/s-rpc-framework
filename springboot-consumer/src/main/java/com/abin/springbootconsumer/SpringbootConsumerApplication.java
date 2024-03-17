package com.abin.springbootconsumer;

import com.abin.rpcspringbootstarter.annotation.EnableRpc;
import com.abin.rpcspringbootstarter.utils.YamlConfigUtil;
import com.abin.srpc.RpcApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc(needServer = false)
public class SpringbootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootConsumerApplication.class, args);
    }

}
