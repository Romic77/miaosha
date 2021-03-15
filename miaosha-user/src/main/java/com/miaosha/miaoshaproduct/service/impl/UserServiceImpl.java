package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dao.UserMapper;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.User;
import com.miaosha.miaoshaproduct.service.IUserService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 10:34
 */

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * @param productDTO
     * @return com.miaosha.miaoshaproduct.utils.CommonResult<com.miaosha.miaoshaproduct.domain.dto.OrderDTO>
     * @author chenqi
     * @date 2021/3/3 21:13
     */
    @Override
    public CommonResult<OrderDTO> userPlaceOrder(ProductDTO productDTO) throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(productDTO.getProductId());
        orderDTO.setProductName(productDTO.getProductName());
        User user = userMapper.selectByPrimaryKey(1l);
        if (user == null) {
            return CommonResult.failed("findUserById error");
        }
        if (user.getBalance().compareTo(new BigDecimal("0")) < 0) {
            return CommonResult.failed(user.getUsername() + ":用户余额不足,请充值");
        }
        orderDTO.setUserId(user.getUserId() + "");
        orderDTO.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        orderDTO.setProductNums(1);
        orderDTO.setStatus(2);
        orderDTO.setTotal(productDTO.getProductPrice());

        return CommonResult.success(orderDTO);
    }

    @Override
    public User getUserById(Long id) {

        return userMapper.selectByPrimaryKey(id);
    }
}
