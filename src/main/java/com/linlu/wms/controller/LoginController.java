package com.linlu.wms.controller;

import com.linlu.wms.common.api.CommonResult;
import com.linlu.wms.domain.param.LoginParam;
import com.linlu.wms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录服务
 *
 * @author xi
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public CommonResult<?> login(@Validated @RequestBody LoginParam loginParam) {
        String token = loginService.login(loginParam.getUserName(), loginParam.getPassword());
        if (token == null) {
            return CommonResult.fail("登录失败", null);
        }
        return CommonResult.success(token);
    }
}
