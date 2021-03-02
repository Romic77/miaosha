package com.miaosha.miaoshaproduct.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("miaosha-order")
public interface OrderFeignService {
    @RequestMapping("/order/placeOrder")
    public boolean placeOrder(String orderDTO);
}
