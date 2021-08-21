package com.zzl.dyna.datasource;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@RegisterMapper
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
	
}
