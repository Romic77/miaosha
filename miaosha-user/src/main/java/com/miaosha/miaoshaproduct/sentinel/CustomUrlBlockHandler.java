package com.miaosha.miaoshaproduct.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.miaosha.miaoshaproduct.utils.CommonResult;
import com.miaosha.miaoshaproduct.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义限流返回错误码
 *
 * @author chenqi
 * @return
 * @date 2021/3/2 23:53
 */

@Service
public class CustomUrlBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException ex) throws Exception {
        String msg = null;
        CommonResult commonResult = null;
        if (ex instanceof FlowException) {
            commonResult = new CommonResult(ResultCode.REQUEST_LIMIT.getCode(), ResultCode.REQUEST_LIMIT.getMessage(), null);
        } else if (ex instanceof DegradeException) {
            commonResult = new CommonResult(ResultCode.REQUEST_FALLBACK.getCode(), ResultCode.REQUEST_FALLBACK.getMessage(), null);
        } else if (ex instanceof ParamFlowException) {
            commonResult = new CommonResult(ResultCode.PARAM_FLOW.getCode(), ResultCode.PARAM_FLOW.getMessage(), null);
        } else if (ex instanceof SystemBlockException) {
            commonResult = new CommonResult(ResultCode.SYSTEM_BLOCK.getCode(), ResultCode.SYSTEM_BLOCK.getMessage(), null);
        } else if (ex instanceof AuthorityException) {
            commonResult = new CommonResult(ResultCode.REQUEST_AUTHORITY.getCode(), ResultCode.REQUEST_AUTHORITY.getMessage(), null);

        }

        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.setStatus(403);
        httpServletResponse.setCharacterEncoding("utf-8");


        httpServletResponse.getWriter().write(JSON.toJSONString(commonResult));
    }
}
