package com.miaosha.miaoshaproduct.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLimitService {
    private static final Logger logger = LoggerFactory.getLogger(UserLimitService.class);

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private OrderFeignService orderFeignService;

    // 限流与阻塞处理
    @SentinelResource(value = "userLimit", blockHandler = "exceptionHandler", fallback = "userPlaceOrderFallback")
    public String userLimit(String productId) {
        ProductDTO productDTO = productFeignService.findProductById(Long.valueOf(productId));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(Long.valueOf(productId));
        orderDTO.setUserId(iUserService.findUserById(1l) + "");
        orderDTO.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        orderDTO.setProductNums(1);
        orderDTO.setStatus(2);
        orderDTO.setTotal(productDTO.getProductPrice());

        // 多线程处理-用户下单
        /*listeningExecutorService.submit(() -> {
            orderFeignService.placeOrder(orderDTO);
        });*/
        return "成功";
    }


    public String exceptionHandler(String productId, BlockException ex) {
        //ex.printStackTrace();
        return "userLimit;被限流了";
    }

    public String userPlaceOrderFallback(String productId) {
        return "fallback";
    }


}
