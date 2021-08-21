package com.zzl.dubbo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestCorsController {
	
	
	@PostMapping("/login")
	public void login() {
		System.out.println(">>> " + 1);
	}
	
	@GetMapping("/test")
	public String login1() {
		System.out.println(">>> " + 1);
		return "zyf";
	}
}
