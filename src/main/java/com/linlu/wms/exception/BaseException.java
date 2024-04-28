package com.linlu.wms.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Long code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMsg;

    public BaseException(String module, Long code, Object[] args, String defaultMsg) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMsg = defaultMsg;
    }

    public BaseException(String module, Long code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMsg) {
        this(module, null, null, defaultMsg);
    }

    public BaseException(Long code, Object[] args) {
        this(null, code, args, null);
    }
}
