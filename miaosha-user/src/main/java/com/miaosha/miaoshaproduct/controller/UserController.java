package com.miaosha.miaoshaproduct.controller;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务 下订单
 * @return 
 * @author chenqi
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
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户下订单
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
    */
    @RequestMapping(value = "/user/placeOrder",method = RequestMethod.GET)
    public String placeOrder(String productId){
        //1 根据productId查询redis库存

        System.out.println(redissonClient.getBucket("2001"));



        //2 多线程处理-用户下单
        //listeningExecutorService.submit();

        //Map map=new HashMap<>();

        //map.put("productId",productId);


        //orderFeignService.placeOrder();
        return "";
    }
}
