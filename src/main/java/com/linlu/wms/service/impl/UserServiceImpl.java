package com.linlu.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linlu.wms.domain.entity.User;
import com.linlu.wms.domain.param.PageParam;
import com.linlu.wms.domain.param.UserParam;
import com.linlu.wms.exception.user.BusinessException;
import com.linlu.wms.mapper.UserMapper;
import com.linlu.wms.security.SecurityUserDetail;
import com.linlu.wms.service.UserService;
import com.linlu.wms.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xi
 * @description 针对表【user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-04-27 22:23:18
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public String add(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        try {
            userMapper.insert(user);
            return user.getId();
        } catch (Exception e) {
            log.error("插入用户错误: {}, detail: {}", e.getMessage(), e.getCause());
            return null;
        }
    }

    @Override
    public int delete(String userId) {
        checkerAllowed(new User(userId));
        return userMapper.deleteById(userId);
    }

    @Override
    public boolean checkUserNameUnique(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userName", user.getUserName());
        User dbUser = userMapper.selectOne(userQueryWrapper);
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phoneNumber", user.getPhoneNumber());
        User dbUser = userMapper.selectOne(userQueryWrapper);
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public SecurityUserDetail getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }

    @Override
    public void checkerAllowed(User user) {
        if (user.isAdmin()) {
            throw new BusinessException("不允许操作管理员用户");
        }
    }

    @Override
    public int edit(User user) {
        user.setUpdateBy(getCurrentUser().getUserId());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateById(user);
    }

    @Override
    public Page<User> all(Integer current, Integer size) {
        Page<User> page = new PageParam(current, size);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("createTime");
        return userMapper.selectPage(page, userQueryWrapper);
    }

    @Override
    public User infoById(String id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        return userMapper.selectOne(queryWrapper);
    }
}




