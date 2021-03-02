package com.miaosha.miaoshaproduct.controller;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 用户服务 下订单
 *
 * @author chenqi
 * @return
 * @date 2021/3/1 15:11
 */
@RestController
public class UserController {
    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProductFeignService productFeignService;


    /**
     * 用户下订单
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
     */
    @RequestMapping(value = "/user/placeOrder", method = RequestMethod.GET)
    public String placeOrder(String productId) {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setProductId(Long.valueOf(productId));
        orderDTO.setUserId();

        orderDTO.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));


        // 多线程处理-用户下单
        listeningExecutorService.submit(()->{
            orderFeignService.placeOrder(orderDTO);
        });

        return "";
    }
}
