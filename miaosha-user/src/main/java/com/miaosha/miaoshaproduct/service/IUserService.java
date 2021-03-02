package com.miaosha.miaoshaproduct.service;

import com.miaosha.miaoshaproduct.domain.entity.User;

/**
 * @Author: chenq
 * @Description:
 * @Date: Created in 2021-03-02 10:33
 */
public interface IUserService {

    public User findUserById(Long userId);
}
