package com.linlu.wms.controller;

import com.linlu.wms.common.api.CommonResult;
import com.linlu.wms.domain.param.LoginParam;
import com.linlu.wms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public CommonResult<?> login(@Validated LoginParam loginParam) {

        return CommonResult.success(null);
    }
}
