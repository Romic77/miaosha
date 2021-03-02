package com.miaosha.miaoshaproduct.domain.dao;

import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(ProductDTO record);

    int insertSelective(ProductDTO record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(ProductDTO record);

    int updateByPrimaryKeyWithBLOBs(ProductDTO record);

    int updateByPrimaryKey(ProductDTO record);
}