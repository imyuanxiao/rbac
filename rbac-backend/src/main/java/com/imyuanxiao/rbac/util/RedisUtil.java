package com.imyuanxiao.rbac.util;

import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.imyuanxiao.rbac.util.RedisConst.*;

/**
 * @version 1.0
 * @description Quick use of redistemplate
 * @author: imyuanxiao
 * @date: 2023/5/27 12:24
 **/
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void getCaptcha(String account, String captcha){
        String cache = stringRedisTemplate.opsForValue().get(REGISTER_CODE_KEY + account);
        // If no code or code is incorrect
        if(cache == null || !cache.equals(captcha)){
            throw new ApiException(ResultCode.PARAMS_ERROR, "验证码错误！");
        }
    }
    public void removeCaptcha(String account){
        stringRedisTemplate.delete(REGISTER_CODE_KEY + account);
    }

    public void saveCode(String account, String captcha){
        // Check whether there is code in redis
        String key = REGISTER_CODE_KEY + account;
        String exists = stringRedisTemplate.opsForValue().get(key);
        if(exists != null){
            throw new ApiException(ResultCode.CODE_EXISTS);
        }
        stringRedisTemplate.opsForValue().set(key, captcha, REGISTER_CODE_TTL, TimeUnit.MINUTES);
    }

    public void saveUserMap(Map<String, Object> userMap){
        String key = LOGIN_USER_KEY + userMap.get("username");
        stringRedisTemplate.opsForHash().putAll(key, userMap);
        // Set token expire time, which is consistent with jwt token expire time
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
    }

    public Map<Object, Object> getUserMap(String username){
        String key = LOGIN_USER_KEY + username;
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public void removeUserMap(){
        String username = SecurityContextUtil.getCurrentUserDetailsVO().getUser().getUsername();
        // Delete token from redis
        stringRedisTemplate.delete(LOGIN_USER_KEY + username);
    }

    public String refreshToken(){

        String username = SecurityContextUtil.getCurrentUserDetailsVO().getUser().getUsername();
        // Rename oldKey newKey
        String oldKey = LOGIN_USER_KEY + username;

        String newToken = JwtManager.generate(username);
        String newKey = LOGIN_USER_KEY + username;

        stringRedisTemplate.rename(oldKey, newKey);
        stringRedisTemplate.opsForHash().put(newKey, "token", newToken);

        // Reset expire time
        stringRedisTemplate.expire(newKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        return newToken;
    }

}
