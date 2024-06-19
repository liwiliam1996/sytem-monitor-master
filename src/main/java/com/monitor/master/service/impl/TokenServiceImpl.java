package com.monitor.master.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.monitor.master.domain.LoginUser;
import com.monitor.master.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lierqiang
 */
@Service
public class TokenServiceImpl implements TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.ttl}")
    private int ttl;

    //刷新token的时间阈值
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    public static final String CLAIM_USER_KEY = "user_key";

    Cache<String, LoginUser> localCache = Caffeine.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    @Override
    public String createToken(LoginUser loginUser) {

        if (Objects.nonNull(loginUser)) {
            //此token用于映射用户信息
            String userTokenKey = UUID.randomUUID().toString();
            localCache.put(userTokenKey, loginUser);
            loginUser.setToken(userTokenKey);

            Map<String, Object> claim = new HashMap<>(2);
            claim.put(CLAIM_USER_KEY, userTokenKey);

            return Jwts.builder().addClaims(claim).signWith(SignatureAlgorithm.HS256, secret).compact();

        }
        return null;
    }

    @Override
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginDate(new Date());
        loginUser.setExpireDate(new Date(System.currentTimeMillis() + ttl * 1000L));
        localCache.put(loginUser.getToken(), loginUser);
    }

    @Override
    public void verifyToken(LoginUser loginUser) {
        long currentTime = System.currentTimeMillis();
        if (loginUser.getExpireDate().getTime() - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {

        LoginUser loginUser = null;

        String jwtToken = request.getHeader("token");

        if (StringUtils.isNotBlank(jwtToken)) {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);

            if (Objects.nonNull(claimsJws)) {

                Claims body = claimsJws.getBody();

                String userKey = body.get(CLAIM_USER_KEY, String.class);

                loginUser = localCache.getIfPresent(userKey);

            }
        }


        return loginUser;

    }

}
