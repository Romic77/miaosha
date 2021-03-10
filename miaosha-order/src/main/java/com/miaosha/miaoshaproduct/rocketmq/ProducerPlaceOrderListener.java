package com.miaosha.miaoshaproduct.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.miaoshaproduct.domain.dao.DuplicationMapper;
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

    @Autowired
    private DuplicationMapper duplicationMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            logger.info("进入executeLocalTransaction");
            //解析消息内容
            String jsonString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            OrderDTO orderDTO = jsonObject.getObject("orderDTO", OrderDTO.class);

            iOrderService.placeOrder(orderDTO, null);

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            logger.error("executeLocalTransaction 事务执行失败", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * rocketmq 回查,保证消息已经到了rocketmq
     * 从上游到下游 通过OrderId保证消息是唯一的,不被重复消费
     * @return org.apache.rocketmq.spring.core.RocketMQLocalTransactionState
     * @author chenqi
     * @date 2021/3/10 09:40
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        RocketMQLocalTransactionState state;
        //解析消息内容
        String jsonString = new String((byte[]) message.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        OrderDTO orderDTO = jsonObject.getObject("orderDTO", OrderDTO.class);

        int count = duplicationMapper.countByServiceId(orderDTO.getOrderId(), "miaosha-order");

        if (count > 0) {
            state = RocketMQLocalTransactionState.COMMIT;
        } else {
            state = RocketMQLocalTransactionState.ROLLBACK;
        }
        return state;
    }
}
