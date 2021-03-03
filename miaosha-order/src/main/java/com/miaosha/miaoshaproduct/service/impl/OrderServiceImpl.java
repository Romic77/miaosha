package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dao.OrderMapper;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.entity.Order;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.service.LeafFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private LeafFeignService leafFeignService;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 用户下订单
     *
     * @return boolean
     * @author chenqi
     * @date 2021/3/3 13:15
     */
    public CommonResult placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        Long orderId = Long.valueOf(leafFeignService.getSegmentId("leaf-segment-order"));
        if (orderId == null) {
            logger.error("get leaf-segment-order failed,orderId:{}", orderId);
            return CommonResult.failed("get leaf-segment-order failed");
        }
        order.setOrderId(orderId);
        order.setProductId(orderDTO.getProductId());
        order.setProdName(orderDTO.getProdName());
        order.setStatus(2);
        order.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        order.setProductNums(1);
        order.setPayTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        order.setIsPayed(true);
        order.setRefundSts(1);
        orderMapper.insertSelective(order);

        return CommonResult.success(null);
    }
}
