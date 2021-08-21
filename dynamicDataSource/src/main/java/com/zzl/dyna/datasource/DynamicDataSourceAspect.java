package com.zzl.dyna.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zzl.dyna.config.ManyDataSourceConfiguration;

import lombok.extern.slf4j.Slf4j;

//根据查询方法来切换源
@Slf4j
@Aspect
@Order(1)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class DynamicDataSourceAspect {

	@Pointcut("execution(public * " + ManyDataSourceConfiguration.MAPPER + ".*.*(..))")
	public void daoDBAspect() {}

	@Before("daoDBAspect()")
	public void switchDataSource(JoinPoint point) {
		//Class<?> classObject = point.getTarget().getClass();
		Class<?> classObject = point.getSignature().getDeclaringType();
		log.info("db aspect " + classObject.toString());
		try {
			boolean isMethod = false;
			RoutingDataSource routing = classObject.getAnnotation(RoutingDataSource.class);
			if (routing != null && routing.value() != null) {
				DynamicDataSourceContextHolder.setDataSourceKey(routing.value().name());
				return;
			} else if(isMethod){
				// 方法的参数类型
				Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
				String methodName = point.getSignature().getName();
				Method method = classObject.getMethod(methodName, argClass);
				if (method.isAnnotationPresent(RoutingDataSource.class)) {
					RoutingDataSource annotation = method.getAnnotation(RoutingDataSource.class);
					DataSourceKey dataSource = annotation.value();
					// 切换slave
					DynamicDataSourceContextHolder.setDataSourceKey(dataSource.name());
					log.info("Switch DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
					return;
				}
			}
			//default db
			DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.master.name());
		} catch (Exception ex) {
			log.error("routing datasource exception " + classObject, ex);
		}
	}

	@After("daoDBAspect()")
	public void restoreDataSource(JoinPoint point) {
		// 切换时才激活
		// DynamicDataSourceContextHolder.clearDataSourceKey();
		log.info("Restore DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
	}
}
