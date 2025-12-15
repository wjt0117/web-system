package com.itheima.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成和解析JWT令牌
 */
public class JwtUtils {

    /**
     * 签名密钥
     * 使用与测试类相同的密钥：aXRoZWltYQ== 重复5次
     * 注意：实际项目中应通过配置文件获取，而不是硬编码
     */
    private static final String SECRET_KEY = "aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==";

    /**
     * JWT过期时间（毫秒）
     * 12小时 = 12 * 60 * 60 * 1000 = 43200000毫秒
     */
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     *
     * @param claims 自定义声明信息
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        // 获取当前时间
        Date now = new Date();
        // 计算过期时间（当前时间 + 12小时）
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        // 构建JWT令牌
        return Jwts.builder()
                // 设置签名算法和密钥
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                // 添加自定义声明信息
                .addClaims(claims)
                // 设置签发时间
                .setIssuedAt(now)
                // 设置过期时间（12小时后）
                .setExpiration(expirationDate)
                // 生成紧凑的JWT字符串
                .compact();
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌字符串
     * @return 声明信息Claims对象
     * @throws io.jsonwebtoken.ExpiredJwtException 令牌已过期
     * @throws io.jsonwebtoken.SignatureException  签名验证失败
     * @throws io.jsonwebtoken.MalformedJwtException 令牌格式错误
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                // 设置签名验证密钥
                .setSigningKey(SECRET_KEY)
                // 构建解析器
                .build()
                // 解析并验证JWT
                .parseClaimsJws(token)
                // 获取载荷数据
                .getBody();
    }

    /**
     * 验证JWT令牌是否有效（不抛出异常）
     *
     * @param token JWT令牌字符串
     * @return true-有效，false-无效
     */
    public static boolean validateToken(String token) {
        try {
            // 尝试解析令牌
            parseToken(token);
            return true;
        } catch (Exception e) {
            // 捕获所有异常，令牌无效
            return false;
        }
    }

    /**
     * 从令牌中获取指定声明值
     *
     * @param token JWT令牌字符串
     * @param key   声明键名
     * @return 声明值
     */
    public static Object getClaim(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key);
    }

    /**
     * 从令牌中获取指定声明值（字符串类型）
     *
     * @param token JWT令牌字符串
     * @param key   声明键名
     * @return 声明值字符串
     */
    public static String getClaimAsString(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key, String.class);
    }

    /**
     * 从令牌中获取指定声明值（整数类型）
     *
     * @param token JWT令牌字符串
     * @param key   声明键名
     * @return 声明值整数
     */
    public static Integer getClaimAsInteger(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key, Integer.class);
    }

    /**
     * 获取令牌的过期时间
     *
     * @param token JWT令牌字符串
     * @return 过期时间Date对象
     */
    public static Date getExpirationDate(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * 检查令牌是否即将过期（在指定时间内）
     *
     * @param token      JWT令牌字符串
     * @param milliseconds 提前检查的毫秒数
     * @return true-即将过期，false-未过期
     */
    public static boolean isTokenExpiringSoon(String token, long milliseconds) {
        Date expiration = getExpirationDate(token);
        Date now = new Date();
        // 计算距离过期的时间
        long timeUntilExpiration = expiration.getTime() - now.getTime();
        return timeUntilExpiration <= milliseconds;
    }

    /**
     * 刷新令牌（生成新令牌，包含原声明的数据）
     *
     * @param token 原JWT令牌
     * @return 新的JWT令牌
     */
    public static String refreshToken(String token) {
        // 解析原令牌的声明
        Claims claims = parseToken(token);
        // 移除JWT标准声明，保留自定义声明
        claims.remove("exp");
        claims.remove("iat");

        // 生成新令牌
        return generateToken(claims);
    }
}