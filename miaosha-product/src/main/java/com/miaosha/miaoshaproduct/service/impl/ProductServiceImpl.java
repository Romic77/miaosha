package com.miaosha.miaoshaproduct.service.impl;

import com.miaosha.miaoshaproduct.domain.dao.DuplicationMapper;
import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.domain.entity.Duplication;
import com.miaosha.miaoshaproduct.domain.entity.Product;
import com.miaosha.miaoshaproduct.service.IProductService;
import com.miaosha.miaoshaproduct.service.LeafFeignService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements IProductService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private LeafFeignService leafFeignService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DuplicationMapper duplicationMapper;

    @Override
    public String insertProduct(Product product) throws Exception {
        String id = leafFeignService.getSegmentId("leaf-segment-product");
        if (StringUtils.isBlank(id)) {
            throw new Exception("leafFeignService获取id为空,检查leaf服务器");
        }
        product.setProductId(Long.valueOf(id));
        productMapper.insert(product);
        return product.toString();
    }

    @Override
    public Product findProductById(Long productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public CommonResult<ProductDTO> updateByPrimaryKeySelective(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
        return CommonResult.success(null);
    }

    @Override
    public void updateProductStocks(OrderDTO orderDTO) {
        int count = duplicationMapper.countByServiceId(orderDTO.getOrderId(), "miaosha-order");
        if (count > 0) {
            logger.info("该消息已经被消费过:{}", orderDTO.getOrderId());
            return;
        }
        Product product = productMapper.selectByPrimaryKey(orderDTO.getProductId());
        product.setTotalStocks(product.getTotalStocks() - orderDTO.getProductNums());
        productMapper.updateByPrimaryKeySelective(product);

        Long duplicationId = Long.valueOf(leafFeignService.getSegmentId("leaf-segment-duplication"));
        if (duplicationId == null) {
            logger.error("get leaf-segment-duplication failed,duplicationId:{}", duplicationId);
            return;
        }
        Duplication duplication = new Duplication();
        duplication.setDuplicationId(duplicationId);
        duplication.setCreateTime(DateTimeConverterUtil.toDate(LocalDateTime.now()));
        duplication.setServiceName("miaosha-order");
        duplication.setServiceId(orderDTO.getOrderId());
        duplicationMapper.insertSelective(duplication);
    }
}
