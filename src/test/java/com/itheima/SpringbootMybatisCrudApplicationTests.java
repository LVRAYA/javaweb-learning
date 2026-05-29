package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;          // JWT 构建器入口
import io.jsonwebtoken.security.Keys; // 密钥工具类，用于生成符合规范的 SecretKey
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey; // Java 标准库的密钥接口，JJWT 用它表示签名密钥
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class SpringbootMybatisCrudApplicationTests {
    public static void main(String[] args) {
        byte[] bytes = new byte[32]; // 32字节 = 256位，非常安全
        new SecureRandom().nextBytes(bytes);
        String secret = Base64.getEncoder().encodeToString(bytes);
        System.out.println("JWT 安全密钥：" + secret);
    }
    @Test
    public void jwtTest() {
        /*
         * ========== 第一部分：准备载荷（Payload / Claims） ==========
         * JWT 的载荷部分，存放你要传递的业务数据。
         * 注意：载荷仅做 Base64 编码，不做加密，不要放敏感信息（如密码）。
         */
        Map<String, Object> claims = new HashMap<>();  // 创建一个 Map 来存放自定义数据
        claims.put("id", 1);       // 存入用户 ID
        claims.put("name", "jack"); // 存入用户名

        /*
         * ========== 第二部分：准备签名密钥 ==========
         * JWT 的签名需要一个密钥，HS256 算法要求密钥长度 >= 256 位（32 字节）。
         * Keys.hmacShaKeyFor() 会根据传入的字节数组生成一个符合 HS256 规范的密钥对象。
         * 实际项目中，密钥字符串应写在配置文件里，不要硬编码在代码中。
         */
        SecretKey key = Keys.hmacShaKeyFor(
                "itheima1234567890itheima1234567890".getBytes() // 恰好 32 字节，满足 HS256 最低要求
        );

        /*
         * ========== 第三部分：构建 JWT ==========
         * Jwts.builder() 创建一个构建器，链式调用配置各项属性，最后 .compact() 生成 JWT 字符串。
         *
         * .claims(claims)        → 设置载荷，把刚才的 Map 放进去
         * .expiration(...)       → 设置过期时间，当前时间往后 3600 秒（1 小时）
         * .signWith(key)         → 用 HS256 密钥对 JWT 进行签名，防篡改
         * .compact()             → 将 Header + Payload + Signature 拼接成最终的 JWT 字符串
         *
         * JWT 的最终格式：Header.Payload.Signature （Base64 编码，点号分隔）
         */
        String token = Jwts.builder()                                    // 1. 获取构建器
                .claims(claims)                                          // 2. 设置载荷
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 3. 设置过期时间（当前时间 + 1 小时）
                .signWith(key)                                           // 4. 用 HS256 密钥签名
                .compact();                                              // 5. 生成 JWT 字符串

        // 打印生成的 JWT，验证效果
        System.out.println("token: " + token);

        /*
         * ========== 第四部分：解析 JWT（验证签名 + 读取载荷） ==========
         * 解析时必须使用和生成时相同的密钥，否则签名验证失败。
         */
        // 1. 构建解析器，传入密钥
        // 2. 解析 JWT 并验证签名
        // 3. 获取载荷（Claims）
        Claims parsedClaims = Jwts.parser()
                .verifyWith(key)           // 0.12.x 新 API，设置验签密钥（旧版 setSigningKey 已弃用）
                .build()                   // 构建解析器
                .parseSignedClaims(token)  // 解析 JWT 并验证签名，返回 Jws<Claims>
                .getPayload();             // 从解析结果中取出载荷部分

        // 从载荷中读取数据
        Integer userId = parsedClaims.get("id", Integer.class);   // 读取 id，指定返回类型
        String username = parsedClaims.get("name", String.class); // 读取 name

        System.out.println("解析成功 — id: " + userId + ", name: " + username);
    }
}