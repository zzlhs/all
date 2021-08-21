package com.zzl.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * 单机版的redis分布式锁
// * 
// * @date 2019年7月6日下午3:37:41
// */
//@Component
//public class RedisLock {
//
//	@Resource
//	private StringRedisTemplate stringRedisTemplate;
//
//	private final int timeout = 10;// 超时时间为10秒
//
//	public boolean tryLock(String key, String value) {
//		return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
//	}
//
//	public void unLock(String key, String value) {
//		String curVal = stringRedisTemplate.opsForValue().get(key);
//		if (StringUtils.isNotEmpty(curVal) && curVal.equals(value)) {
//			stringRedisTemplate.opsForValue().getOperations().delete(key);
//		}
//	}
//}
