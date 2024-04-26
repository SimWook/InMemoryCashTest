package com.example.inmemorycashtest.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ConfigurationでCacheManagerを登録して使用します。
 * ここでのCacheManagerはSimpleCacheManagerを実装体として使用し、コレクションに対して動作します。
 * ConcurrentMapCacheは、SimpleCacheManagerまたはConcurrentMapCacheManagerを通じて
 * 動作するコレクション形式のキャッシュです。
 */
//@Configuration
//@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(List.of(new ConcurrentMapCache("memberList"),
                new ConcurrentMapCache("memberOne")));
        return simpleCacheManager;
    }

}
