package com.pactera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableEurekaClient
public class LogsStartBean {
	public static void main(String[] args) {
		SpringApplication.run(LogsStartBean.class, args);
	}
}
