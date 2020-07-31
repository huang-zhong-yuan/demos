package com.example.springcache.constant;

import com.google.common.collect.ImmutableMap;

public class CacheConstant {

    public static final String DEFAULT_CACHE_PREFIX = "lending";
    public static final String DEFAULT_CACHE_SEPARATOR = ":";

    public static final String CACHE_1_SECONDS = "cache1s";
    public static final String CACHE_2_SECONDS = "cache2s";
    public static final String CACHE_5_SECONDS = "cache5s";
    public static final String CACHE_10_SECONDS = "cache10s";
    public static final String CACHE_1_MINUTE = "cache1m";
    public static final String CACHE_5_MINUTES = "cache5m";
    public static final String CACHE_PROJECT_LIST = "cacheProjectList";
    public static final String CACHE_DAILY_PRODUCT_LIST = "cacheDailyProductList";
    public static final String CACHE_MGMT_QUERY_LIST = "cacheMgmtQueryList";

    public static final ImmutableMap<String, Long> CACHE_MAP = ImmutableMap.<String, Long>builder()
            .put(CACHE_1_SECONDS, 1l)
            .put(CACHE_2_SECONDS, 2l)
            .put(CACHE_5_SECONDS, 5l)
            .put(CACHE_10_SECONDS, 10l)
            .put(CACHE_1_MINUTE, 60l)
            .put(CACHE_5_MINUTES, 5 * 60l)
            .put(CACHE_PROJECT_LIST, 5l)
            .put(CACHE_DAILY_PRODUCT_LIST, 5l)
            .put(CACHE_MGMT_QUERY_LIST, 60L)
            .build();

}
