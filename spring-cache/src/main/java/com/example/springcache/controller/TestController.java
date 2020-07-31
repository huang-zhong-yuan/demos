package com.example.springcache.controller;

import com.example.springcache.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/cache5s")
    @Cacheable(CacheConstant.CACHE_5_SECONDS)
    public String testCache() {
        log.info("testCache.success");
        return "success";
    }

    @GetMapping("/redisson-cache")
    public String testCache(@RequestParam("command") String command) {
        if ("evict".equals(command)) {
            redissonClient.getBucket("key").delete();
        } else {
            redissonClient.getBucket("key").set("abc_value");
        }
        log.info("testCache.success");
        return "success";
    }

    @GetMapping("/redis-cache")
    public String redisCache(@RequestParam("command") String command) {
        if ("evict".equals(command)) {
            redisTemplate.delete("redis.key");
        } else {
            redisTemplate.opsForValue().set("redis.key", "redis.value");
        }
        log.info("testCache.success");
        return "success";
    }
}
