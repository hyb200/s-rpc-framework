import com.abin.srpc.loadbalancer.ConsistentHashLoadBalancer;
import com.abin.srpc.loadbalancer.LoadBalancer;
import com.abin.srpc.loadbalancer.PollLoadBalancer;
import com.abin.srpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadBalancerTest {
    final LoadBalancer loadBalancer = new PollLoadBalancer();

    @Test
    public void test() {
        Map<String, Object> params = new HashMap<>();
        params.put("methodName", "apple");

        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("serviceName");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);

        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("serviceName");
        serviceMetaInfo2.setServiceVersion("1.0");
        serviceMetaInfo2.setServiceHost("localhost");
        serviceMetaInfo2.setServicePort(2355);
        List<ServiceMetaInfo> list = Arrays.asList(serviceMetaInfo, serviceMetaInfo2);

        ServiceMetaInfo select = loadBalancer.select(params, list);
        System.out.println(select);
        System.out.println("=====");
        Assert.assertNotNull(select);
        select = loadBalancer.select(params, list);
        System.out.println(select);
        System.out.println("=====");
        Assert.assertNotNull(select);
        select = loadBalancer.select(params, list);
        System.out.println(select);
        System.out.println("=====");
        Assert.assertNotNull(select);
    }
}
