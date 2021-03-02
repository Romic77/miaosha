package com.miaosha.miaoshaproduct.controller;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private IUserService iUserService;


    /**
     * 用户下订单
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
     */
    @RequestMapping(value = "/user/placeOrder", method = RequestMethod.GET)
    public String placeOrder(String productId) {
        //1. 限流 熔断 降级 限制90%的用户


        ProductDTO productDTO = productFeignService.findProductById(Long.valueOf(productId));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(Long.valueOf(productId));
        orderDTO.setUserId(iUserService.findUserById(1l) + "");
        orderDTO.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        orderDTO.setProductNums(1);
        orderDTO.setStatus(2);
        orderDTO.setTotal(productDTO.getProductPrice());

        // 多线程处理-用户下单
        listeningExecutorService.submit(() -> {
            orderFeignService.placeOrder(orderDTO);
        });

        return "";
    }

}
