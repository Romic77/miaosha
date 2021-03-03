package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public User findUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
