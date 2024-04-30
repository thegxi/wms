package com.linlu.wms.exception.user;

import com.linlu.wms.exception.BaseException;

/**
 * 用户异常
 *
 * @author xi
 */
public class UserException extends BaseException {
    public UserException(String module, Long code, Object[] args, String defaultMsg) {
        super(module, code, args, defaultMsg);
    }
}
