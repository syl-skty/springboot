package com.example.skty.springboot.configurations.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用redis作为缓存的相关配置
 */
@Configuration
@EnableCaching
public class CustomerRedisCacheConfiguration extends CachingConfigurerSupport {

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> new CacheKey(method, params);
    }
}
