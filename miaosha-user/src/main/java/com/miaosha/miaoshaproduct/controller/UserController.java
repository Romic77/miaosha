package com.miaosha.miaoshaproduct.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.service.impl.UserLimitService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务 下订单
 *
 * @author chenqi
 * @return
 * @date 2021/3/1 15:11
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    private UserLimitService userLimitService;


    /**
     * 用户下订单
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
     */
    @RequestMapping(value = "/user/placeOrder", method = RequestMethod.POST)
    @SentinelResource(value = "/user/placeOrder",blockHandler = "exceptionHandler")
    public String placeOrder(String productId) {
        userLimitService.userLimit(productId);
       return "";
    }



}
