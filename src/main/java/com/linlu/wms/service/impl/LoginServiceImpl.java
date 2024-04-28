package com.linlu.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linlu.wms.domain.entity.User;
import com.linlu.wms.domain.param.LoginParam;
import com.linlu.wms.exception.user.UserException;
import com.linlu.wms.mapper.UserMapper;
import com.linlu.wms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void login(LoginParam loginParam) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", loginParam.getEncryptUsername());
        userQueryWrapper.eq("password", loginParam.getEncryptPasswd());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new UserException("user", 404L, null, "用户不存在或密码错误");
        }
    }
}
