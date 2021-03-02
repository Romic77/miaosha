package com.miaosha.miaoshaproduct.task;

import com.miaosha.miaoshaproduct.domain.dao.ProductMapper;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductJob {

    private static Logger logger = LoggerFactory.getLogger(ProductJob.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProductMapper productMapper;


    /**
     * 定时 把商品加入到redis 预热
     */
    @XxlJob("productJobHandler")
    public void productJobHandler() throws Exception {
        XxlJobHelper.log("start set product to redis");
        XxlJobHelper.log("任务参数为:{}",XxlJobHelper.getJobParam());
        String productId=XxlJobHelper.getJobParam();

        ProductDTO product = productMapper.selectByPrimaryKey(Long.valueOf(productId));
        if (product == null) {
            XxlJobHelper.log("该产品不存在,请确认productId");
            return;
        }
        RBucket<Integer> result = redissonClient.getBucket(productId);
        if (!result.isExists()) {
            result.set(product.getTotalStocks());
        }

        // default success
    }
}
