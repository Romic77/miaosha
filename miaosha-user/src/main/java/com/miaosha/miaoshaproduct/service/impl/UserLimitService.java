package com.miaosha.miaoshaproduct.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.User;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.miaosha.miaoshaproduct.utils.ResultCode;
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
    private ProductFeignService productFeignService;

    @Autowired
    private IUserService iUserService;


    /**
     * 1. 限流http://localhost:8082/userLimit设置限流规则,大于规则的请求直接被拒绝了
     * 2. 降级
     *
     * @param productId
     * @return
     */
    @SentinelResource(value = "userLimit", blockHandler = "exceptionHandler", fallback = "userPlaceOrderFallback")
    public CommonResult<OrderDTO> userLimit(ProductDTO productDTO) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(productDTO.getProductId());
        orderDTO.setProductName(productDTO.getProductName());
        User user = iUserService.findUserById(1l);
        if (user == null) {
            return CommonResult.failed("findUserById error");
        }
        orderDTO.setUserId(user.getUserId() + "");
        orderDTO.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        orderDTO.setProductNums(1);
        orderDTO.setStatus(2);
        orderDTO.setTotal(productDTO.getProductPrice());


        return CommonResult.success(orderDTO);
    }

    /**
     * 进入限流
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/3 10:19
     */
    public CommonResult exceptionHandler(ProductDTO productDTO, BlockException ex) {
        logger.error("userLimit;被限流了");
        return new CommonResult(ResultCode.REQUEST_LIMIT.getCode(), ResultCode.REQUEST_LIMIT.getMessage(), "userLimit-exceptionHandler");
    }

    /**
     * 进入降级
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/3 10:19
     */
    public CommonResult userPlaceOrderFallback(ProductDTO productDTO) {
        logger.error("userLimit;被降级了");
        return new CommonResult(ResultCode.REQUEST_FALLBACK.getCode(), ResultCode.REQUEST_FALLBACK.getMessage(), "userLimit-userPlaceOrderFallback");
    }
}
