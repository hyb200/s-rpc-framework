package com.abin.srpc.registry;

import cn.hutool.json.JSONUtil;
import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EtcdRegistry implements Registry{

    private Client client;

    private KV kvClient;

    public static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) {

        //  创建 Lease 客户端
        Lease leaseClient = client.getLeaseClient();

        //  创建一个30s的租约
        long leaseId = 0;
        try {
            leaseId = leaseClient.grant(300).get().getID();
            //  设置键值对
            String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
            ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
            ByteSequence val = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

            //  绑定租约
            PutOption putOption = PutOption.builder()
                    .withLeaseId(leaseId)
                    .build();
            kvClient.put(key, val, putOption).get();
        } catch (Exception e) {
            log.error("服务 {} 注册失败", serviceMetaInfo.getServiceNodeKey());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        System.out.println(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey());
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        String searchPrefix = ETCD_ROOT_PATH + serviceKey + "/";

        try {
            GetOption getOption = GetOption.builder()
                    .isPrefix(true)
                    .build();

            List<KeyValue> kvs = kvClient.get(
                            ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                            getOption
                    )
                    .get()
                    .getKvs();

            return kvs.stream()
                    .map(keyValue -> {
                        String val = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(val, ServiceMetaInfo.class);
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destory() {
        log.info("当前 Etcd 服务节点下线");
        if (kvClient != null) {
            kvClient.close();
        }

        if (client != null) {
            client.close();
        }
    }
}
