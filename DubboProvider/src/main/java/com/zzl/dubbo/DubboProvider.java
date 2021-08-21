package com.zzl.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class DubboProvider {
	
	public static void main(String[] args) {
		SpringApplication.run(DubboProvider.class, args);
	}
}
