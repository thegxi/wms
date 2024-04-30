package com.linlu.wms.exception.user;

import com.linlu.wms.exception.BaseException;

/**
 * 用户不存在异常
 *
 * @author xi
 */
public class UserNoExistException extends BaseException {
    public UserNoExistException(String module, Long code, Object[] args, String defaultMsg) {
        super(module, code, args, defaultMsg);
    }

    public UserNoExistException(String module, Long code, Object[] args) {
        super(module, code, args);
    }

    public UserNoExistException(String module, String defaultMsg) {
        super(module, defaultMsg);
    }

    public UserNoExistException(Long code, Object[] args) {
        super(code, args);
    }
}
