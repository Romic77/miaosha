package com.miaosha.miaoshaproduct.service;


import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="miaosha-order")
public interface OrderFeignService {
    @RequestMapping(value="/order/placeOrder",produces = "application/json; charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public CommonResult placeOrder(@RequestBody OrderDTO orderDTO) ;
}
