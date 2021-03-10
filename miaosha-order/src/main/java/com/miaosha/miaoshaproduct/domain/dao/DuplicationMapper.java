package com.miaosha.miaoshaproduct.domain.dao;

import com.miaosha.miaoshaproduct.domain.entity.Duplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DuplicationMapper {
    int deleteByPrimaryKey(Long duplicationId);

    int insert(Duplication record);

    int insertSelective(Duplication record);

    Duplication selectByPrimaryKey(Long duplicationId);

    int updateByPrimaryKeySelective(Duplication record);

    int updateByPrimaryKey(Duplication record);

    int countByServiceId(@Param("serviceId")Long serviceId,@Param("serviceName") String serviceName);
}