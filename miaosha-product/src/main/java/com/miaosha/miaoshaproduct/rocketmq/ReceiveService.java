package com.miaosha.miaoshaproduct.rocketmq;

import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReceiveService {
    @Autowired
    private ProductMapper productMapper;

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

    @StreamListener("input3")
    public void receiveInput3(@Payload ProductDTO productDTO) {
        logger.info("input3 接收到了消息：" + productDTO);
        productMapper.updateByPrimaryKeySelective(productDTO);
    }
}