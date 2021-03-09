package com.miaosha.miaoshaproduct.rocketmq;

import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "consumer_test_topic", topic = "test-topic1")
public class ReceiveService implements RocketMQListener<String> {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ReceiveService.class);
    @Autowired
    private ProductMapper productMapper;


    @Override
    public void onMessage(String s) {
        logger.info("开始消费消息:{}", s);
    }
}