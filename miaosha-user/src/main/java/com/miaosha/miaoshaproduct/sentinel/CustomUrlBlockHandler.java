package com.miaosha.miaoshaproduct.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (ex instanceof FlowException) {
            msg = "访问人数过多,限流了;";
        } else if (ex instanceof DegradeException) {
            msg = "访问人数过多,降级了;";
        } else if (ex instanceof ParamFlowException) {
            msg = "热点参数限流;";
        } else if (ex instanceof SystemBlockException) {
            msg = "系统规则（负载/...不满足要求）;";
        } else if (ex instanceof AuthorityException) {
            msg = "授权规则不通过;";
        }

        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        StringBuilder sb = new StringBuilder();
        sb.append("msg:" + msg);
        sb.append("   status:" + httpServletResponse.getStatus());

        //如果加了转义符号看不明白
        //使用类创建就json对象
        httpServletResponse.getWriter().write(JSON.toJSONString(sb.toString()));
    }
}