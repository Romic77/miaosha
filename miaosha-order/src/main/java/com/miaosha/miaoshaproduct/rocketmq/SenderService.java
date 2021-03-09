package com.miaosha.miaoshaproduct.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class SenderService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送字符串
     *
     * @param msg
     */
    public void send(String destination, String msg) {
        Message message = MessageBuilder.withPayload(msg)
                .build();

        rocketMQTemplate.syncSend(destination, message);
    }

    /**
     * 发送带tag的字符串
     *
     * @param msg
     * @param tag
     */
    public void sendWithTags(String destination, String msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .build();
        rocketMQTemplate.syncSend(destination, message);
    }

    /**
     * 发送对象
     *
     * @param msg
     * @param tag
     * @param <T>
     */
    public <T> void sendObject(String destination, T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        rocketMQTemplate.send(destination, message);
    }


    /**
     * 发送事务消息
     *
     * @param msg
     * @param <T>
     */
    public <T> void sendMessageInTransaction(String destination,T msg) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        rocketMQTemplate.sendMessageInTransaction(destination, message, null);
    }
}