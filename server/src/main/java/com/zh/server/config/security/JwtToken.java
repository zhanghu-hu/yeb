package com.zh.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成工具
 *
 * @author ZH
 * @date 2021/1/19
 */
@Component
public class JwtToken {

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 密钥
     */
    private static Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        //过期时间
        long nowMillis = System.currentTimeMillis();
        Date expirDate = new Date(nowMillis + expiration * 1000);

        //根据荷载(Claims)生成JWT Token
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            //根据token拿到荷载(Claims)
            Claims claims = getClaims(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断token是否有效（1、是否过了有效时间 2、用户名是否存在）
     *
     * @param token
     * @param userDetails
     * @return true:有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Date expiration = getClaims(token).getExpiration();
        return username.equals(userDetails.getUsername()) && (new Date()).before(expiration);
    }

    /**
     * 是否过期
     * @param token
     * @return true：未过期
     */
    public boolean canRefersh(String token){
        Date expiration = getClaims(token).getExpiration();
        return (new Date()).before(expiration);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refershToken(String token){
        Claims claims=getClaims(token);
        UserDetails userDetails=new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return claims.getSubject();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        return getToken(userDetails);
    }

    /**
     * 根据token得到荷载（Claims）
     *
     * @param token
     * @return
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
