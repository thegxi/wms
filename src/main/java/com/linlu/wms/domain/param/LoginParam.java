package com.linlu.wms.domain.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginParam {
    /**
     * 加密的用户名
     */
    @NotEmpty
    private String encryptUsername;
    /**
     * 加密的密码
     */
    @NotEmpty
    private String encryptPasswd;
    /**
     * 验证码
     */
    private String code;
}
