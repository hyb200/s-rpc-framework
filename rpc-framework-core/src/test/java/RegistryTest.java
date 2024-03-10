import com.abin.srpc.config.RegistryConfig;
import com.abin.srpc.model.ServiceMetaInfo;
import com.abin.srpc.registry.EtcdRegistry;
import com.abin.srpc.registry.Registry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RegistryTest {
    final Registry registry = new EtcdRegistry();

    @Before
    public void init() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setRegistry("http://localhost:2379");
        registry.init(registryConfig);
    }

    @Test
    public void registry() throws Exception {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("userService");
        serviceMetaInfo.setServiceVersion("3.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.register(serviceMetaInfo);
    }

    @Test
    public void serviceDiscovery() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("userService");
        serviceMetaInfo.setServiceVersion("3.0");
        String serviceKey = serviceMetaInfo.getServiceKey();

        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
        for (ServiceMetaInfo info : serviceMetaInfoList) {
            System.out.println(info.toString());
        }
        Assert.assertNotNull(serviceMetaInfoList);
    }

    @Test
    public void unRegistry() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("userService");
        serviceMetaInfo.setServiceVersion("3.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);

        registry.unRegister(serviceMetaInfo);
    }

    @Test
    public void heartBeat() throws Exception {
        registry();
        Thread.sleep(1000L * 60);
    }
}
