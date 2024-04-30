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
    private String username;
    /**
     * 加密的密码
     */
    @NotEmpty
    private String password;
    /**
     * 验证码
     */
    private String code;
}
