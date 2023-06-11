package com.imyuanxiao.rbac.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.Claims;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @description  Generation and management util for jwt token
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
public final class JwtManager {
    /**
     * This secret key is crucial to prevent tampering of the JWT.
     */
    private final static byte[] secretKeyBytes = "my_secret_key".getBytes();


    /**
     * The expiration time is currently set to 30 minutes,
     * this configuration depends on business requirements.
     */
    private final static Integer EXPIRATION = 30;

    /**
     * @description Generate jwt token
     * @author imyuanxiao
     * @param username account
     * @return jwt token
     **/
    public static String generate(String username, Long userId) {
        DateTime now = DateUtil.date();
        DateTime ddl = DateUtil.offsetMinute(now, EXPIRATION);
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, now);
                put(JWTPayload.EXPIRES_AT, ddl);
                put(JWTPayload.NOT_BEFORE, now);
                put(JWTPayload.SUBJECT, username); //put account in 'sub'
                put(JWTPayload.JWT_ID, userId);
            }
        };
        return "Bearer " + JWTUtil.createToken(map, secretKeyBytes);
    }

    /**
     * @description Verify token
     * @author imyuanxiao
     * @param token jwt token
     * @throws RuntimeException Throw an exception if verification fails.
     **/
    public static void verifyToken(String token) {
        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败

        //验证签名
        boolean verify = JWTUtil.verify(token, JWTSignerUtil.hs256(secretKeyBytes));
        if(!verify) {
            throw new ApiException(ResultCode.INVALID_TOKEN);
        }
        // 验证算法和时间
        JWTValidator validator = JWTValidator.of(token);
        // 验证算法
        try {
            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));
        } catch (ValidateException e) {
            throw new ApiException(ResultCode.INVALID_TOKEN);
        }
        // 验证时间
        try {
            JWTValidator.of(token).validateDate();
        } catch (ValidateException e) {
            throw new ApiException(ResultCode.TOKEN_EXPIRED);
        }
    }

    /**
     * @description Parse token
     * @author imyuanxiao
     * @param token token to parse
     **/
    private static Claims extractAllClaims(String token) {
        verifyToken(token);
        return JWTUtil.parseToken(token).getPayload();
    }

    /**
     * Extract username from token
     * @author imyuanxiao
     * @date 14:56 2023/5/7
     * @param token token to parse
     * @return Return username if successful
     **/
    public static String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.getClaim(JWTPayload.SUBJECT));
    }

    public static String extractUserID(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.getClaim(JWTPayload.JWT_ID));
    }

}