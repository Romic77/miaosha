package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.service.impl.OrderServiceFallBack;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="miaosha-order",fallback = OrderServiceFallBack.class)
public interface OrderFeignService {
    @RequestMapping("/order/placeOrder")
    public CommonResult placeOrder(OrderDTO orderDTO);
}
