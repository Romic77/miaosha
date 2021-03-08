package com.miaosha.miaoshaproduct.rocketmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service

public class ReceiveService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ReceiveService.class);
    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        logger.info("input1 接收到了消息：" + receiveMsg);
    }

    @StreamListener("input2")
    public void receiveInput2(String receiveMsg) {
        logger.info("input2 接收到了消息：" + receiveMsg);
    }

    /*@StreamListener("input3")
    public void receiveInput3(@Payload Foo foo) {
        logger.info("input3 接收到了消息：" + foo);
    }*/
}