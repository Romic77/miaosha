package com.miaosha.miaoshaproduct.controller;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.miaosha.miaoshaproduct.domain.dto.OrderDTO;
import com.miaosha.miaoshaproduct.domain.dto.ProductDTO;
import com.miaosha.miaoshaproduct.service.OrderFeignService;
import com.miaosha.miaoshaproduct.service.ProductFeignService;
import com.miaosha.miaoshaproduct.service.impl.UserLimitService;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户服务 下订单
 *
 * @author chenqi
 * @return
 * @date 2021/3/1 15:11
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserLimitService userLimitService;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private ProductFeignService productFeignService;

    /**
     * 用户下订单
     *
     * @return java.lang.String
     * @author chenqi
     * @date 2021/3/1 15:13
     */
    @RequestMapping(value = "/user/placeOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public CommonResult placeOrder(String productId) {
        try {
            CommonResult<ProductDTO> productDTOCommonResult = productFeignService.findProductById(Long.valueOf(productId));
            if (productDTOCommonResult.getCode() != 200) {
                return productDTOCommonResult;
            }
            ProductDTO productDTO = productDTOCommonResult.getData();

            CommonResult<OrderDTO> commonResult = userLimitService.userLimit(productDTO);

            // 多线程处理-用户下单
            //listeningExecutorService.submit();
        /*final ListenableFuture<CommonResult> listenableFuture = (ListenableFuture<CommonResult>) listeningExecutorService.submit(() -> {
            orderFeignService.placeOrder(commonResult.getData());
        });
        listenableFuture.get();*/

            listeningExecutorService.submit(() -> {
                orderFeignService.placeOrder(commonResult.getData());
            });
            return CommonResult.success(null);
        } catch (Exception e) {
            logger.error("用户服务异常;", e);
            return CommonResult.failed("用户服务异常");
        }
    }


}
