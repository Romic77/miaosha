package com.miaosha.miaoshaproduct.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-09 10:28
 */

@RocketMQMessageListener(topic = "placeOrder-topic",consumerGroup = "consumer_txmsg_group")
public class ConsumerTransactionListener implements RocketMQListener<String> {
    /** logger */
    private static final Logger logger =LoggerFactory.getLogger(ConsumerTransactionListener.class);

    @Override
    public void onMessage(String str) {
        logger.info("开始消费消息:{}",str);
    }
}

