package com.webfactory.exchangerateservice.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;

import com.webfactory.exchangerateservice.ExchangeRateServiceApplication;

public class CacheUtil {
	public static void addToCache(CacheManager cacheManager, String key, Object data) throws Exception {
		Cache<String, Object> cache = cacheManager.getCache(ExchangeRateServiceApplication.cacheName, String.class, Object.class);
		cache.put(key, data);
	}

	public static Object getFromCache(CacheManager cacheManager, String key) {
		Cache<String, Object> cache = cacheManager.getCache(ExchangeRateServiceApplication.cacheName, String.class, Object.class);
		return cache.get(key);
	}
}
