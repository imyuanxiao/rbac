package com.imyuanxiao.rbac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @version 1.0
 * @description Test redis connection
 * @author: imyuanxiao
 * @date: 2023/5/26 22:48
 **/

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisConnection() {
        String key = "testKey";
        String value = "testValue";
        redisTemplate.opsForValue().set(key, value);
        String retrievedValue = redisTemplate.opsForValue().get(key);
        System.out.println("Retrieved value: " + retrievedValue);
    }
}
