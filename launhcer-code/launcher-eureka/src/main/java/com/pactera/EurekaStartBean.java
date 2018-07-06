package com.pactera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableEurekaServer
@SpringBootApplication
public class EurekaStartBean {
	public static void main(String[] args) {
		SpringApplication.run(EurekaStartBean.class, args);
	}
}
