package com.zzl.dyna.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
public class User {
	
	@Id
	private Integer id;
	
	private String name;
	
	private Integer age;
}
