package com.linlu.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linlu.wms.domain.entity.User;
import com.linlu.wms.service.UserService;
import com.linlu.wms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author xi
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-04-27 22:23:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByname(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        return userMapper.selectOne(userQueryWrapper);
    }
}




