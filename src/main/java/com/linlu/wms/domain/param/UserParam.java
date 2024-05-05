package com.linlu.wms.domain.param;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserParam {
    private String id;
    @NotNull
    private String userName;
    @NotNull
    @Min(value = 8, message = "密码最低8位")
    private String password;
    @NotNull
    private String phoneNumber;
}
