package com.example.log;

import com.example.log.utils.IPUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class LogApplication {

	public static void main(String[] args) {
		System.setProperty("local-ip", IPUtils.getIpAddress());
//		System.setProperty("spring.cloud.bootstrap.enabled", "false");
		SpringApplication.run(LogApplication.class, args);
	}

}
