package com.miaosha.miaoshaproduct.domain.dao;

import com.miaosha.miaoshaproduct.domain.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}