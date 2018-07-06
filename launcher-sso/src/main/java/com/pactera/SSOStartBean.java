package com.pactera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.pactera.business.dao")
public class SSOStartBean {
	public static void main(String[] args) {
		SpringApplication.run(SSOStartBean.class, args);
	}
}
