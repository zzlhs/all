package com.zzl.dyna.config;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.zzl.dyna.datasource.DataSourceKey;
import com.zzl.dyna.datasource.DynamicDataSourceContextHolder;
import com.zzl.dyna.datasource.DynamicRoutingDataSource;
import com.zzl.dyna.datasource.MyMapper;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;


/**
 * note:多数据源映射配置
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class ManyDataSourceConfiguration {

	public final static String MAPPER = "com.report.core.dao";

	@Bean("master")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource master() {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}

	// 多源
	@Bean("slave")
	@ConfigurationProperties(prefix = "spring.slave-datasource")
	public DataSource slave() {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}

	//@Primary
	//@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));
		// 配置切换
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage(MAPPER);
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
		return sqlSessionFactoryBean.getObject();
	}

	//@Bean("sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
		return sqlSessionTemplate;
	}

	//@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		//mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setSqlSessionFactory(sqlSessionFactory());
		mapperScannerConfigurer.setBasePackage(MAPPER);
		Properties properties = new Properties();
		properties.setProperty("mappers", MyMapper.class.getName());
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}

	// 事务管理
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Primary
	@Bean("dataSource")
	public DynamicRoutingDataSource dataSource() {
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
		Map<Object, Object> targetDataSourceMap = Maps.newHashMap();
		targetDataSourceMap.put(DataSourceKey.master.name(), master());
		targetDataSourceMap.put(DataSourceKey.slave.name(), slave());
		dynamicRoutingDataSource.setDefaultTargetDataSource(master());

		// 数据源列表
		dynamicRoutingDataSource.setTargetDataSources(targetDataSourceMap);
		DynamicDataSourceContextHolder.dataSourceKeys.addAll(targetDataSourceMap.keySet());
		// slave数据源用于轮循
		DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(targetDataSourceMap.keySet());
		DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DataSourceKey.master.name());
		return dynamicRoutingDataSource;
	}
}