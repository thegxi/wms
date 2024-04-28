package com.linlu.wms.service;

import com.linlu.wms.domain.param.LoginParam;

public interface LoginService {
    /**
     * 用户名和密码登陆
     */
    void login(LoginParam loginParam);
}
