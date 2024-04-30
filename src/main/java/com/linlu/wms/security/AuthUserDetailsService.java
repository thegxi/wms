package com.linlu.wms.security;

import com.linlu.wms.domain.entity.User;
import com.linlu.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Security用户密码授权
 *
 * @author xi
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByname(username);
        Assert.notNull(user, "用户不存在或密码错误");
        SecurityUserDetail userDetail = new SecurityUserDetail();
        userDetail.setUserId(user.getId());
        userDetail.setUsername(user.getUserName());
        userDetail.setPassword(user.getPassword());
        return userDetail;
    }
}
