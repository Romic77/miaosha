package com.miaosha.miaoshaproduct.domain.dao;

import com.miaosha.miaoshaproduct.domain.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    int updateProductStocks(Product product);
}