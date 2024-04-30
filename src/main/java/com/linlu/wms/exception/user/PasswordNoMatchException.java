package com.linlu.wms.exception.user;

import com.linlu.wms.exception.BaseException;

/**
 * 密码异常
 *
 * @author xi
 */
public class PasswordNoMatchException extends BaseException {
    public PasswordNoMatchException(String module, Long code, Object[] args, String defaultMsg) {
        super(module, code, args, defaultMsg);
    }

    public PasswordNoMatchException(String module, Long code, Object[] args) {
        super(module, code, args);
    }

    public PasswordNoMatchException(String module, String defaultMsg) {
        super(module, defaultMsg);
    }

    public PasswordNoMatchException(Long code, Object[] args) {
        super(code, args);
    }
}
