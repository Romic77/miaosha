package com.miaosha.miaoshaproduct.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.IProductService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-09 10:28
 */
@Service
@RocketMQMessageListener(topic = "miaosha_placeorder_topic",consumerGroup = "consumer_txmsg_group")
public class ConsumerTransactionListener implements RocketMQListener<String> {
    /** logger */
    private static final Logger logger =LoggerFactory.getLogger(ConsumerTransactionListener.class);

    @Autowired
    private IProductService productService;

    @Override
    public void onMessage(String str) {
        logger.info("开始消费消息:{}",str);
        //解析消息内容
        /*JSONObject jsonObject = JSONObject.parseObject(str);
        OrderDTO orderDTO = jsonObject.getObject("orderDTO", OrderDTO.class);

        //根据orderDTO的orderId实现幂等,保证Duplication表的orderId只有一个

        productService.updateProductStocks(orderDTO);*/
    }
}

