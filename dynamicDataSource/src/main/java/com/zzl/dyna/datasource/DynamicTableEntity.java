package com.zzl.dyna.datasource;

import java.lang.reflect.Field;

import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.IDynamicTableName;

@Slf4j
public abstract class DynamicTableEntity implements IDynamicTableName {

	// 分表条件查询激活
	@Transient
	@Override
	public String getDynamicTableName() {
		if (!this.getClass().isAnnotationPresent(Table.class)) {
			return null;
		}
		Table table = this.getClass().getAnnotation(Table.class);
		if (table != null && table.name() != null) {
			StringBuilder tableName = new StringBuilder(table.name());
			if (this.getClass().isAnnotationPresent(HashPartition.class)) {
				HashPartition partition = this.getClass().getAnnotation(HashPartition.class);
				if (partition != null && partition.field() != null && partition.count() > 1) {
					try {
						Field field = this.getClass().getDeclaredField(partition.field());
						return getTableName(tableName, field, partition.count());
					} catch (Exception ex) {
						log.error("dynamic table exception ", ex);
					}
				}
			} else {
				// 注解到字段
				Field[] fields = this.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(HashPartition.class)
							&& field.getAnnotation(HashPartition.class).count() > 1) {
						try {
							return getTableName(tableName, field, field.getAnnotation(HashPartition.class).count());
						} catch (Exception ex) {
							log.error("dynamic table exception ", ex);
						}
					}
				}
			}
			return tableName.toString();
		}
		return null;
	}

	private String getTableName(StringBuilder tableName, Field field, int count) throws Exception {
		field.setAccessible(true);
		Object val = field.get(this);
		if (tableName != null && val != null) {
			int index = Math.abs(val.hashCode()) % count;
			tableName = tableName.append("_").append(index);
		}
		return tableName.toString();
	}
}
