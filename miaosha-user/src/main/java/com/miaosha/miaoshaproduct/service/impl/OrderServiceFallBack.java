package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.miaosha.miaoshaproduct.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFallBack implements OrderFeignService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceFallBack.class);

    @Override
    public CommonResult placeOrder(OrderDTO orderDTO) {
        logger.error("OrderServiceFallBack fallback");
        return new CommonResult(ResultCode.REQUEST_FALLBACK.getCode(), ResultCode.REQUEST_FALLBACK.getMessage(), "OrderServiceFallBack");
    }
}
