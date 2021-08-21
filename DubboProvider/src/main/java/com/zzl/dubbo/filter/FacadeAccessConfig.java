package com.zzl.dubbo.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "provider")
public class FacadeAccessConfig {
	
	private String fundURLs;
}
