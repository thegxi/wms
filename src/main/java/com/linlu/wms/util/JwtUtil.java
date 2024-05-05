package com.linlu.wms.util;

import com.linlu.wms.constant.Constant;
import com.linlu.wms.exception.user.BusinessException;
import com.linlu.wms.security.SecurityUserDetail;
import com.linlu.wms.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.duration}")
    private Long duration;

    @Autowired
    private RedisService redisService;

    /**
     * 根据用户信息生成token
     *
     * @param securityUserDetail 用户信息
     * @return token
     */
    public String generateTokenFromUser(SecurityUserDetail securityUserDetail) {
        Map<String, Object> claim = new HashMap<>();
        String randomKey = RandomStringUtils.randomAlphanumeric(16) + securityUserDetail.getUserId();
        securityUserDetail.setTempKey(randomKey);
        claim.put(Constant.USER_CLAIM_KEY, randomKey);
        claim.put(Constant.USER_CREATE_TIME_CLAIM_KEY, new Date());
        return generateToken(claim);
    }

    /**
     * 从token中获取JWT中的负载
     *
     * @param token token
     * @return 负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("jwt token格式验证失败, token: {}", token);
        }
        return claims;
    }

    /**
     * 从token中获取用户id
     *
     * @param request token请求
     * @return userId
     */
    public SecurityUserDetail getUserByToken(HttpServletRequest request) {
        String token = null;
        try {
            token = getToken(request);
            return getUserByToken(token);
        } catch (Exception e) {
            log.info("token解析用户信息失败,token: {}", token);
            throw new BusinessException("token异常");
        }
    }

    /**
     * 从token中获取用户id
     *
     * @param token token
     * @return userId
     */
    public SecurityUserDetail getUserByToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            String userCacheKey = claims.get(Constant.USER_CLAIM_KEY, String.class);
            SecurityUserDetail securityUserDetail = redisService.getCacheObject(userCacheKey);
            return securityUserDetail;
        } catch (Exception e) {
            log.info("token解析用户信息失败,token: {}", token);
            throw new BusinessException("token异常");
        }
    }

    /**
     * 根据负载生成token
     *
     * @param claim 负载
     * @return token
     */
    private String generateToken(Map<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成jwt过期时间
     *
     * @return token过期日期
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 获取请求中的token
     *
     * @param request 请求
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constant.TOKEN_PREFIX)) {
            token = token.replace(Constant.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 从token中获取过期时间
     *
     * @param token token
     * @return date
     */
    private Date getExpirationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 验证令牌有效期,相差不足20分钟，自动延期
     */
    public void checkToken(String token) {
        long expiredTime = getExpirationFromToken(token).getTime();
        long currentTime = System.currentTimeMillis();
        if (expiredTime - currentTime <= duration) {
            refreshToken(token);
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return tru: 过期, false: 没有过期
     */
    private boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     *
     * @param token    原token
     * @param duration 指定时间（分钟）
     */
    private boolean isTokenRefreshOnDuration(String token, int duration) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(Constant.USER_CREATE_TIME_CLAIM_KEY, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(created) && refreshDate.before(DateUtils.addMinutes(created, duration))) {
            return true;
        }
        return false;
    }

    /**
     * token过期时间不足20分钟，则刷新token续期
     *
     * @param oldToken 原token
     * @return token
     */
    public String refreshToken(String oldToken) {
        if (StringUtils.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(Constant.TOKEN_PREFIX.length());
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        // 如果token已经过期，则不刷新token
        if (isTokenExpired(token)) {
            return null;
        }
        // 如果token在30分钟内刚刚刷新，返回原token
        if (isTokenRefreshOnDuration(token, 30)) {
            return token;
        } else {
            claims.put(Constant.USER_CREATE_TIME_CLAIM_KEY, new Date());
            return generateToken(claims);
        }
    }
}
