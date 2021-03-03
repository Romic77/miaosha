package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.miaosha.miaoshaproduct.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * feigen sentinel fallback
 * 当ProductFeignService服务不可用的时候直接进入了fallback
 */
@Component
public class ProductServiceFallBack implements ProductFeignService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceFallBack.class);

    @Override
    public CommonResult findProductById(Long productId) {
        logger.error("ProductServiceFallBack fallback");
        return new CommonResult(ResultCode.REQUEST_FALLBACK.getCode(), ResultCode.REQUEST_FALLBACK.getMessage(), "productServiceFallBack");
    }
}
