package com.linlu.wms.security;

import com.linlu.wms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Security相关配置
 *
 * @author xi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private TokenFilter tokenFilter;
    @Autowired
    private IgnoredUrlConfig ignoredUrlConfig;

    /**
     * 配置PasswordEncoder(密码编码器)
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置token工具类
     *
     * @return JwtUtil
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    /**
     * 配置AuthenticationManager(认证管理器）
     *
     * @return ProviderManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    /**
     * 配置AuthenticationProvider
     * DaoAuthenticationProvider是一个AuthenticationProvider的实现，它使用UserDetailsService 和 PasswordEncoder来验证一个用户名和密码
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, TokenFilter tokenFilter) throws Exception {
        // 放行请求
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        for (String url : ignoredUrlConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        httpSecurity
                // CSRF禁用,不使用session
                .csrf().disable()
                // 基于token不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().anyRequest().authenticated();

        // 配置token校验过滤器
        httpSecurity.addFilterBefore(tokenFilter, AuthorizationFilter.class);
        return httpSecurity.build();
    }
}
