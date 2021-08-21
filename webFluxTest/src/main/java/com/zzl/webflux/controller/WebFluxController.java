package com.zzl.webflux.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class WebFluxController {
	
	// 普通的SpringMVC方法
	@GetMapping("/1")
	private String get1() {
	    log.info("get1 start");
	    String result = createStr();
	    log.info("get1 end.");
	    return result;
	}

	// WebFlux(返回的是Mono)
	@GetMapping("/2")
	private Mono<String> get2() {
	    log.info("get2 start");
	    Mono<String> result = Mono.fromSupplier(() -> createStr());
	    log.info("get2 end.");
	    return result;
	}
	
	private String createStr() {
	    try {
	        TimeUnit.SECONDS.sleep(5);
	    } catch (InterruptedException e) {
	    }
	    return "some string";
	}

}
