package com.abin.springbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ConsumerExampleTest {
    @Resource
    private ConsumerExample consumerExample;

    @Test
    public void test() {
        consumerExample.test();
    }
}
