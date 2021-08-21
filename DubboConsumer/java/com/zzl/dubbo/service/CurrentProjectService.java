package com.zzl.dubbo.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service
public class CurrentProjectService {
	
	@Reference(check = false)
	private DubboTestService dubboTestService;
	
	public void methodA() {
		System.out.println("DubboConsumer -> CurrentProjectService -> methodA");
		dubboTestService.providerService(1);
	}
}
