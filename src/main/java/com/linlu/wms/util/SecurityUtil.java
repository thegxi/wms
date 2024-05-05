package com.linlu.wms.util;

import com.linlu.wms.common.api.ResultCode;
import com.linlu.wms.exception.user.BusinessException;
import com.linlu.wms.security.SecurityUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取当前登陆用户
     *
     * @return user
     */
    public static SecurityUserDetail getCurrentUser() {
        try {
            return (SecurityUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BusinessException("获取当前用户信息异常", ResultCode.UNAUTHORIZED.getCode());
        }
    }
}
