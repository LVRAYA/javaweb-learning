package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成 JWT 令牌
     * @param claims 载荷数据（比如用户 id、用户名）
     * @return JWT 字符串
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 JWT 令牌（验证签名 + 过期时间）
     * @param token JWT 字符串
     * @return 载荷数据，解析失败返回 null
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从 JWT 中提取单个值
     * @param token JWT 字符串
     * @param name  载荷中的 key
     * @param clazz 值的类型
     * @return 对应的 value，解析失败返回 null
     */
    public <T> T getClaim(String token, String name, Class<T> clazz) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get(name, clazz);
    }
}