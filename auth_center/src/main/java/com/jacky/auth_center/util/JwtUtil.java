package com.jacky.auth_center.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jacky.auth_center.common.JWTToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 5:41 下午
 */
public class JwtUtil {

    /**
     * token 过期时间（min）
     */
    public static long expire = 30;

    /**
     * 生成签名,expireTime后过期
     *
     * @param username 用户名
     * @param salt 盐
     * @param time     过期时间 min
     * @return 加密的token
     */
    public static String sign(String username, String salt, long time) {
        Date date = new Date(System.currentTimeMillis() + time * 60 * 1000);
        Algorithm algorithm = Algorithm.HMAC256(salt);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 验证token是否有效
     * @param token
     * @param username
     * @param salt
     * @return
     */
    public static boolean verify(String token,String username,String salt){
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     * @param token
     */
    public static String getUsername(JWTToken token) {
        try {
            DecodedJWT jwt = JWT.decode(token.getToken());
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 生成随机盐,长度32位
     *
     * @return
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }

}
