package com.bubbleshop.config.redis;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTemplateProvider {
    private final RedisTemplate<String, Object> redisTemplate;

    public Object getRedisValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (RedisConnectionException e) {
            throw new ApiException(ResponseCode.SERVICE_UNAVAILABLE);
        }
    }

    public void setRedisValue(String key, String value, Long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }
}
