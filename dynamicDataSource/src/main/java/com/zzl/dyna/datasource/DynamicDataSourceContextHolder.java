package com.zzl.dyna.datasource;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicDataSourceContextHolder {

	/**
	 * 轮循计数
	 */
	private static int counter = 0;
	private static final ThreadLocal<Object> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceKey.master);

	/**
	 * DataSource List
	 */
	public static List<Object> dataSourceKeys = Lists.newArrayList();

	/**
	 * The constant slave
	 */
	public static List<Object> slaveDataSourceKeys = Lists.newArrayList();

	// 源锁
	private static Lock lock = new ReentrantLock();

	/**
	 * Use master data source
	 */
	public static void switchMasterDataSource() {
		CONTEXT_HOLDER.set(DataSourceKey.master);
	}

	/**
	 * 轮循选择数据源
	 */
	public static void switchSlaveDataSource() {
		lock.lock();
		try {
			int index = counter % slaveDataSourceKeys.size();
			CONTEXT_HOLDER.set(slaveDataSourceKeys.get(index));
			counter++;
		} catch (Exception e) {
			log.error("-----Switch slave datasource failed, error message is {} ------", e);
			switchMasterDataSource();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Check DataSource in current
	 */
	public static boolean containDataSourceKey(String key) {
		return dataSourceKeys.contains(key);
	}

	/**
	 * To switch DataSource
	 */
	public static void setDataSourceKey(String key) {
		CONTEXT_HOLDER.set(key);
	}

	/**
	 * Get current DataSource
	 */
	public static Object getDataSourceKey() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * To set DataSource as default
	 */
	public static void clearDataSourceKey() {
		CONTEXT_HOLDER.remove();
	}
}
