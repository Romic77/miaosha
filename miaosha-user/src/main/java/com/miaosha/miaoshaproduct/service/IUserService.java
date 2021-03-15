package com.miaosha.miaoshaproduct.service;

import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.User;
import com.miaosha.miaoshaproduct.utils.CommonResult;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 10:33
 */
public interface IUserService {

    public CommonResult<OrderDTO> userPlaceOrder(ProductDTO productDTO) throws Exception;

    User getUserById(Long id);
}
