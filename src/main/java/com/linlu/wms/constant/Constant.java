package com.linlu.wms.constant;

/**
 * 常量类
 *
 * @author xi
 */
public class Constant {
    /**
     * 用户cache key
     */
    public static final String SECURITY_USER_KEY = "security:user:key";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * jwt claim user key
     */
    public static final String USER_CLAIM_KEY = "jwt:claim:user:key";

    /**
     * jwt claim create_time key
     */
    public static final String USER_CREATE_TIME_CLAIM_KEY = "jwt:claim:user:create:time:key";
}
