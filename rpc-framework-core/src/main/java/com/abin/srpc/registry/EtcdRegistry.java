package com.abin.srpc.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public class EtcdRegistry implements Registry{

    private Client client;

    private KV kvClient;

    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    public static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
        heartBeat();
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
            PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
            kvClient.put(key, val, putOption).get();

            localRegisterNodeKeySet.add(registerKey);
        } catch (Exception e) {
            log.error("服务 {} 注册失败", serviceMetaInfo.getServiceNodeKey());
        }
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        try {
            kvClient.delete(ByteSequence.from(registerKey, StandardCharsets.UTF_8)).get();
        } catch (Exception e) {
            throw new RuntimeException(registerKey + "注销失败");
        }
        localRegisterNodeKeySet.remove(registerKey);
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

        for (String key : localRegisterNodeKeySet) {
            try {
                kvClient.delete(ByteSequence.from(key, StandardCharsets.UTF_8)).get();
            } catch (Exception e) {
                throw new RuntimeException(key + "节点下线失败");
            }
        }

        if (kvClient != null) {
            kvClient.close();
        }

        if (client != null) {
            client.close();
        }
    }

    @Override
    public void heartBeat() {
        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                for (String key : localRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();

                        //  该节点已过期（需要重启节点）
                        if (CollUtil.isEmpty(keyValues)) {
                            continue;
                        }

                        //  节点未过期，重新注册
                        KeyValue keyValue = keyValues.get(0);
                        String val = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(val, ServiceMetaInfo.class);
                        register(serviceMetaInfo);
                    } catch (Exception e) {
                        throw new RuntimeException(key + "续签失败", e);
                    }
                }
            }
        });

        //  支持毫秒级任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}
