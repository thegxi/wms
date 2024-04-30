package com.linlu.wms.mapper;

import com.linlu.wms.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
* @author xi
* @description 针对表【user(用户信息表)】的数据库操作Mapper
* @createDate 2024-04-27 22:23:17
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户id获取用户的所有权限
     *
     * @param userId 用户id
     * @return 权限
     */
    List<GrantedAuthority> listUserAllPermission(String userId);
}




