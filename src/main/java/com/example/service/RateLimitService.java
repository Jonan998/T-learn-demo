package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean isRateLimitExceeded(int userId, int limit, Duration duration) {
        String key = "rate_limit:" + userId;
        Integer count = (Integer) redisTemplate.opsForValue().get(key);

        if (count == null) {
            redisTemplate.opsForValue().set(key, 1, duration);
            return false;
        }

        if (count >= limit) {
            return true;
        }

        redisTemplate.opsForValue().increment(key);
        return false;
    }
}
