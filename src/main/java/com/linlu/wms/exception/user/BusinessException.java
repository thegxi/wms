package com.linlu.wms.exception.user;

/**
 * 业务异常
 *
 * @author xi
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Long code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，调试用
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BusinessException() {
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Long code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Long getCode() {
        return code;
    }

    public BusinessException setMessage(String message) {
        this.message = message;
        return this;
    }

    public BusinessException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
