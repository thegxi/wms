package com.linlu.wms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linlu.wms.common.api.CommonResult;
import com.linlu.wms.domain.entity.User;
import com.linlu.wms.domain.param.UserParam;
import com.linlu.wms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("add")
    public CommonResult<String> add(@Validated UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        if (userService.checkUserNameUnique(user)) {
            return CommonResult.fail("失败,账号已存在");
        }
        if (userService.checkPhoneUnique(user)) {
            return CommonResult.fail("失败,手机号码已存在");
        }
        String result = userService.add(userParam);
        if (StringUtils.isEmpty(result)) {
            return CommonResult.fail("新增用户出错");
        }
        return CommonResult.success(result);
    }

    @DeleteMapping("delete/{id}")
    public CommonResult<Integer> delete(@PathVariable String id) {
        if (id.equals(userService.getCurrentUser().getUserId())) {
            return CommonResult.fail("不能删除当前用户");
        }
        return CommonResult.success(userService.delete(id));
    }

    @PutMapping("edit")
    public CommonResult<Integer> edit(@Validated @RequestBody UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        userService.checkerAllowed(user);
        if (userService.checkPhoneUnique(user)) {
            return CommonResult.fail("修改用户" + userParam.getUserName() + "失败,手机号码已经存在");
        }
        return CommonResult.success(userService.edit(user));
    }

    @GetMapping("all")
    public CommonResult<Page<User>> all(Integer current, Integer size) {
        return CommonResult.success(userService.all(current, size));
    }

    @GetMapping("info")
    public CommonResult<User> info(@NotNull String id) {
        return CommonResult.success(userService.infoById(id));
    }
}
