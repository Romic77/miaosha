package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dao.DuplicationMapper;
import com.miaosha.miaoshaproduct.domain.dao.OrderMapper;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Duplication;
import com.miaosha.miaoshaproduct.domain.entity.Order;
import com.miaosha.miaoshaproduct.rocketmq.SenderService;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.service.LeafFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements IOrderService {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private LeafFeignService leafFeignService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SenderService senderService;

    @Autowired
    private DuplicationMapper duplicationMapper;
    /**
     * 1. 用户下订单
     * 2. 基于seata AT 分布式事务
     *
     * @return boolean
     * @author chenqi
     * @date 2021/3/3 13:15
     */
    @Override
    //@GlobalTransactional(rollbackFor = Exception.class)
    public CommonResult placeOrder(OrderDTO orderDTO, ProductDTO productDTO) {
        Order order = new Order();
        Long orderId = Long.valueOf(leafFeignService.getSegmentId("leaf-segment-order"));
        if (orderId == null) {
            logger.error("get leaf-segment-order failed,orderId:{}", orderId);
            return CommonResult.failed("get leaf-segment-order failed");
        }
        order.setUserId(orderDTO.getUserId());
        order.setOrderId(orderId);
        order.setProductId(orderDTO.getProductId());
        order.setProductName(orderDTO.getProductName());
        order.setStatus(2);
        order.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        order.setProductNums(1);
        order.setPayTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        order.setIsPayed(true);
        order.setRefundSts(1);
        orderMapper.insertSelective(order);
        orderDTO.setOrderId(orderId);


        Long duplicationId = Long.valueOf(leafFeignService.getSegmentId("leaf-segment-duplication"));
        if (duplicationId == null) {
            logger.error("get leaf-segment-duplication failed,duplicationId:{}", duplicationId);
            return CommonResult.failed("get leaf-segment-duplication failed");
        }
        //幂等处理
        Duplication duplication=new Duplication();
        duplication.setDuplicationId(duplicationId);
        duplication.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        duplication.setServiceName("miaosha-order");
        duplication.setServiceId(orderId);
        duplicationMapper.insert(duplication);
        return CommonResult.success(null);
    }
}
