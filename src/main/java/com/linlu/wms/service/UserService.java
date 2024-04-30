package com.linlu.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linlu.wms.domain.entity.User;

/**
* @author xi
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-04-27 22:23:17
*/
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return User
     */
    User getUserByname(String userName);
}
