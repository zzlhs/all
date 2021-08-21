package com.zzl.dubbo.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.zzl.dubbo.service.DubboTestService;


@Service(interfaceClass = DubboTestService.class, delay = 100000, 
	timeout = 30000, retries = 3,
	filter = "validationFilter")
@Component
public class DubboTestServiceImpl implements DubboTestService {

	@Override
	public void providerService(Integer i) {
		System.out.println("DubboProvider -> DubboTestServiceImpl -> providerService " + i);
	}

}
