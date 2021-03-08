package com.miaosha.miaoshaproduct;

import com.miaosha.miaoshaproduct.rocketmq.MySource;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableAutoDataSourceProxy
@EnableBinding({MySource.class})
public class MiaoshaOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaOrderApplication.class, args);
    }

}