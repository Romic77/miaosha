package com.miaosha.miaoshaproduct.controller;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.service.IOrderService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/order/placeOrder")
    public CommonResult placeOrder(OrderDTO orderDTO) {
        RAtomicLong atomicVar  = redissonClient.getAtomicLong(orderDTO.getProductId()+"");
        // 多线程调用 线程安全
        long stock = atomicVar.get();
        if(stock <0){
            return CommonResult.failed("库存不足,秒杀结束");
        }
        orderService.placeOrder(orderDTO);

        //调用消息中间件rocketMQ进行库存扣减


        return CommonResult.success(null);
    }
}
