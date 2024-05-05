package com.linlu.wms.common.api;

import lombok.Getter;

/**
 * 返回状态码
 *
 * @author xi
 */
@Getter
public enum ResultCode {
    SUCCESS(200L, "操作成功"),
    FAIL(500L, "操作失败"),
    VALIDATE_FAILED(4000L, "参数校验失败"),
    UNAUTHORIZED(401L, "未授权"),
    FORBIDDEN(403L, "没有相关权限");

    private Long code;
    private String msg;

    ResultCode(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
