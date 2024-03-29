# S-RPC framework

### 项目介绍

S-RPC 是一款基于 Vert.x + Etcd 的轻量级 RPC 框架。该框架提供服务注册、发现，自定义协议，负载均衡，容错机制等功能，
并且使用了 Java SPI 机制提供了标准服务接口，开发者能够根据需求对框架进行扩展。


通过学习该项目，可以了解到 RPC 框架的底层实现原理，下面是 RPC 框架调用流程图：
![image.png](https://git.acwing.com/Mendicus/image/-/raw/main/Java/20240316170702.png)

### 功能设计

#### 目录结构
```text
|-- common                  -- 示例代码的一些依赖，包括接口、model
|-- rpc-framework-core      -- 框架核心实现
|-- rpc-spring-boot-starter -- 注解驱动，可在 Spring Boot 项目中使用 
|-- service-consumer        -- 服务消费者示例
|-- service-provider        -- 服务提供者示例
|-- springboot-consumer     -- 使用注解的服务消费者示例
`-- springboot-provider     -- 使用注解的服务提供在示例
```

#### 具体功能

- 支持 `application.properties` 和 `application.yml` 配置文件
- 使用 Vert.x 框架进行网络通信
- Java SPI 机制
- 提供接口的 Mock 服务 
- **服务中心：** 提供 Etcd 和 Zookeeper 两种实现
- **序列化器：** 提供多种序列化器
  - Json 序列化
  - Kryo 序列化
  - Hessian 序列化
- **自定义传输协议**
- **负载均衡：** 实现轮询、随机、一致性哈希等策略
- **容错机制：** 静默处理
- **注解驱动设计**
