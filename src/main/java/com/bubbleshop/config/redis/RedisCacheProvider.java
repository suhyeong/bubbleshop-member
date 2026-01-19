package com.bubbleshop.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheProvider {
    private CacheManager cacheManager;

    /**
     * 캐시 조회
     * @param name
     * @param key
     * @return
     */
    public String getCache(String name, String key) {
        Cache cache = cacheManager.getCache(name);
        if(!ObjectUtils.isEmpty(cache)) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if(!ObjectUtils.isEmpty(valueWrapper)){
                return (String) valueWrapper.get();
            }
        }

        return null;
    }

    /**
     * 캐시 삭제
     * @param name
     * @param key
     */
    public void deleteCache(String name, String key) {
        Cache cache = cacheManager.getCache(name);
        if(!ObjectUtils.isEmpty(cache)) {
            cache.evictIfPresent(key);
        }
    }

    /**
     * 캐시 저장
     * @param name
     * @param key
     * @param value
     */
    public void setCache(String name, String key, String value) {
        Cache cache = cacheManager.getCache(name);
        if(!ObjectUtils.isEmpty(cache)) {
            cache.put(key, value);
        }
    }
}
