import com.abin.srpc.fault.retry.RetryStrategy;
import com.abin.srpc.fault.retry.strategy.FixedIntervalRetryStrategy;
import com.abin.srpc.fault.retry.strategy.NoRetryStrategy;
import com.abin.srpc.model.RpcResponse;
import org.junit.Test;

public class RetryTest {
    public final RetryStrategy strategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = strategy.doRetry(() -> {
                System.out.println("模拟重试");
                throw new RuntimeException("模拟异常");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
