package com.linlu.wms.service.impl;

import com.linlu.wms.domain.entity.User;
import com.linlu.wms.security.SecurityUserDetail;
import com.linlu.wms.service.LoginService;
import com.linlu.wms.service.UserService;
import com.linlu.wms.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        Assert.notNull(authenticate, "用户名或密码错误");
        if (authenticate.getPrincipal() instanceof SecurityUserDetail userDetail) {
            String token = jwtUtil.generateTokenFromUser(userDetail);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("登录成功, user: {}/{}", userDetail.getUserId(), userDetail.getUsername());
            return token;
        } else {
            log.error("登录异常，从上下文获取用户信息失败， authenticate:{}", authenticate);
            return null;
        }
    }

    /**
     * SecurityUserDetail转换成User
     *
     * @param userDetails
     * @return
     */
    private User changeToUser(SecurityUserDetail userDetails) {
        User user = new User();
        user.setId(userDetails.getUserId());
        user.setUserName(user.getUserName());
        user.setPassword(user.getPassword());
        return user;
    }
}
