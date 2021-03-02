package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("miaosha-order")
public interface OrderFeignService {
    @RequestMapping("/order/placeOrder")
    public String placeOrder(OrderDTO orderDTO);
}
