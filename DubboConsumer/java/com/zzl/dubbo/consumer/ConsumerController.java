package com.zzl.dubbo.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzl.dubbo.service.CurrentProjectService;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	
	@Autowired
	private CurrentProjectService currentProjectService;
	
	@GetMapping("/test")
	public void m1() {
		System.out.println("DubboConsumer -> ConsumerController -> m1");
		currentProjectService.methodA();
	}
}
