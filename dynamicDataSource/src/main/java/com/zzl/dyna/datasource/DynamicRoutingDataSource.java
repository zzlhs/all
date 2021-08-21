package com.zzl.dyna.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		log.info("------ Current DataSource is [{}] ------", DynamicDataSourceContextHolder.getDataSourceKey());
		return DynamicDataSourceContextHolder.getDataSourceKey();
	}
}
