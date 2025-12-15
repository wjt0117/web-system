package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGenerateJwt(){

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==")//指定签名算法和密钥
                .addClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 60*10000))//设置过期时间
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt(){
        // JWT token字符串，由三部分组成：header.payload.signature
        String token="eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc2NTQyNjQwN30.rCibgcBOO2HRcoh3aAwEkrRP6q9OM1eng4d56ATNfKo";
        Claims claims=Jwts.parser()
                // 设置签名验证密钥，用于验证token的签名是否有效
                .setSigningKey("aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==aXRoZWltYQ==")
                // 构建JWT解析器实例
                .build()
                // parseClaimsJws方法会：
                // 1. 验证token格式
                // 2. 验证签名是否匹配
                // 3. 验证是否过期（如果包含exp字段）
                .parseClaimsJws(token)
                // 获取token的数据
                .getPayload();
        System.out.println(claims);
    }


}
