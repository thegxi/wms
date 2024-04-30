package com.linlu.wms.security;

import com.linlu.wms.util.JwtUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 令牌服务
 *
 * @author xi
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityUserDetail securityUserDetail = jwtUtil.getUserByToken(request);
        if (ObjectUtils.allNotNull(securityUserDetail, securityUserDetail.getAuthorities())) {
            jwtUtil.checkToken(jwtUtil.getToken(request));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUserDetail, null, securityUserDetail.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
