package com.miaosha.miaoshaproduct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
public class RedissonCase {
    private RedissonClient redissonClient;
    private RedissonReactiveClient reactiveClient;
    private RedissonRxClient rxClient;

    @Before
    public void before() {
        Config config = new Config();
        //config.setCodec(new org.redisson.client.codec.StringCodec());
        config.useSingleServer().setAddress("redis://localhost:6379").setPassword("123456");
        redissonClient = Redisson.create(config);
        reactiveClient = Redisson.createReactive(config);
        rxClient = Redisson.createRx(config);
    }
    @After
    public void after() {
        redissonClient.shutdown();
        reactiveClient.shutdown();
        rxClient.shutdown();
    }

    @Test
    public void test1(){
        RList<String> list = redissonClient.getList("2001");
        for (int i = 0; i <= 10; i++) {
            list.add(i, "1");
        }
    }

    @Test
    public void test2(){
        RList<String> list = redissonClient.getList("2001");
    }

}