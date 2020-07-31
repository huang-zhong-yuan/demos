package com.example.springcache.helper;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
public class RedisHelper {
    private static final String PURCHASE_LOCK_PREFIX = "purchase_lock:";
    private static final String DAILY_PURCHASE_LOCK_PREFIX = "daily_purchase_lock:";
    private static final String TRANSFER_REGULAR_LOCK_PREFIX = "transfer_from_regular_lock:";
    private static final String DAILY_REDEMPTION_LOCK_PREFIX = "daily_redemption_lock:";
    private static final String DAILY_EXPERIENCE_COUPON_LOCK_PREFIX = "daily_experience_coupon_lock:";


    private static final String DAILY_INTEREST_STATUS_MAP_NAME = "lending_daily_interest_status_map:";
    private static final String DAILY_INTEREST_STATUS_LAST_SUCCESS_DAY_KEY_NAME = "last_success_day:";
    private static final String DAILY_INTEREST_STATUS_LAST_NEED_TO_RECALCULATE_DAY_KEY_NAME = "last_need_to_recalculate_day:";

    private static final String DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_MAP_NAME = "lending_daily_experience_coupon_interest_status_map:";
    private static final String DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_LAST_SUCCESS_DAY_KEY_NAME = "last_experience_coupon_success_day:";
    private static final String DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_LAST_NEED_TO_RECALCULATE_DAY_KEY_NAME = "last_experience_coupon_need_to_recalculate_day:";

    @Value("${purchase.lock.time.in.seconds:3}")
    private int purchaseLockTimeInSeconds;

    @Value("${purchase.lock.wait.time.in.seconds:1}")
    private int purchaseLockWaitTimeInSeconds;

    @Autowired
    private RedissonClient redissonClient;

    public void putLastSuccessDayToDailyInsertMap(LocalDate day){
        redissonClient.getMapCache(DAILY_INTEREST_STATUS_MAP_NAME, StringCodec.INSTANCE).put(DAILY_INTEREST_STATUS_LAST_SUCCESS_DAY_KEY_NAME,day,24,TimeUnit.HOURS);
    }

    public void putLastNeedToRecalculateDayToDailyInsertMap(LocalDate day){
        redissonClient.getMapCache(DAILY_INTEREST_STATUS_MAP_NAME, StringCodec.INSTANCE).put(DAILY_INTEREST_STATUS_LAST_NEED_TO_RECALCULATE_DAY_KEY_NAME,day,24,TimeUnit.HOURS);
    }

    public void putLastSuccessDayToDailyExCouponInsertMap(LocalDate day){
        redissonClient.getMapCache(DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_MAP_NAME, StringCodec.INSTANCE).put(DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_LAST_SUCCESS_DAY_KEY_NAME,day,24,TimeUnit.HOURS);
    }

    public void putLastNeedToRecalculateDayToDailyExCouponInsertMap(LocalDate day){
        redissonClient.getMapCache(DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_MAP_NAME, StringCodec.INSTANCE).put(DAILY_EXPERIENCE_COUPON_INTEREST_STATUS_LAST_NEED_TO_RECALCULATE_DAY_KEY_NAME,day,24,TimeUnit.HOURS);
    }

    private boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isExists() && lock.isLocked();
    }

    private String getLockKey(Long userId, String targetId, String prefix) {
        return prefix + userId + "," + targetId;
    }

}
