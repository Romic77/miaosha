package com.miaosha.miaoshaproduct.utils;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    REQUEST_LIMIT(405,"请求量过大,限流了"),
    REQUEST_FALLBACK(406,"请求量过大,降级了"),
    PARAM_FLOW(407,"热点参数限流"),
    SYSTEM_BLOCK(408,"系统规则（负载不满足要求）;"),
    REQUEST_AUTHORITY(409,"请求量过大,降级了");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
