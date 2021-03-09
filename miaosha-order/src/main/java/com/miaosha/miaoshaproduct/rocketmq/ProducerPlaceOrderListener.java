package com.miaosha.miaoshaproduct.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.service.IOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-09 10:35
 */

@RocketMQTransactionListener
public class ProducerPlaceOrderListener implements RocketMQLocalTransactionListener {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ProducerPlaceOrderListener.class);

    @Autowired
    private IOrderService iOrderService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        logger.info("进入executeLocalTransaction");
        try {
            logger.info("进入executeLocalTransaction");
            //解析消息内容
            String jsonString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            OrderDTO orderDTO = jsonObject.getObject("orderDTO",OrderDTO.class);

            iOrderService.placeOrder(orderDTO, null);

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            logger.error("executeLocalTransaction 事务执行失败", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}
