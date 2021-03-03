package com.miaosha.miaoshaproduct.controller;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class OrderController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/order/placeOrder", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public CommonResult placeOrder(@RequestBody OrderDTO orderDTO) {
        try {

            String productId = orderDTO.getProductId() + "";
            RAtomicLong atomicVar = redissonClient.getAtomicLong(productId);
            // 多线程调用 线程安全
            long stock = atomicVar.get();

            if (stock < 0) {
                return CommonResult.failed("库存不足,秒杀结束");
            }
            orderService.placeOrder(orderDTO);

            //调用消息中间件rocketMQ进行库存扣减


            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return CommonResult.failed("placeOrder业务异常");
        }
    }
}
