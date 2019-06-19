package com.agent.czb.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component("eHCacheUtils")
public class EHCacheUtils {
    public static final String SESSION_CACHE = "SESSION_CACHE";
    public static final String TEMP_CACHE = "TEMP_CACHE";

    private Map<String, CacheHelp> cacheMap;
    @Autowired
    private EhCacheCacheManager cacheManager;

    @PostConstruct
    private void init() {
        cacheMap = new HashMap<>();
        cacheMap.put(SESSION_CACHE, new CacheHelp(cacheManager.getCache(SESSION_CACHE)));
        cacheMap.put(TEMP_CACHE, new CacheHelp(cacheManager.getCache(TEMP_CACHE)));
    }

    public boolean isExist(String cacheName, String key) {
        return cacheMap.get(cacheName).isExist(key);
    }

    public void put(String cacheName, String key, Object val) {
        cacheMap.get(cacheName).put(key, val);
    }

    public <T> T get(String cacheName, String key, Class<T> t) {
        return cacheMap.get(cacheName).get(key, t);
    }

    public Cache getCache(String cacheName) {
        return cacheMap.get(cacheName).getCache();
    }

    public CacheHelp getCacheHelp(String cacheName) {
        return cacheMap.get(cacheName);
    }

    public static class CacheHelp {
        private Cache cache;

        public CacheHelp(Cache cache) {
            this.cache = cache;
        }

        public boolean isExist(String key) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            return !(valueWrapper == null || valueWrapper.get() == null);
        }

        public void put(String key, Object val) {
            cache.put(key, val);
        }

        public <T> T get(String key, Class<T> t) {
            return cache.get(key, t);
        }

        public Cache getCache() {
            return this.cache;
        }

    }
}