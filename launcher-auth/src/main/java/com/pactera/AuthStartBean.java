package com.pactera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description: 启动
 * @author:woqu
 * @since:2018年5月24日 下午7:17:50
 */
@EnableAsync
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.pactera.business.dao")
public class AuthStartBean {
	public static void main(String[] args) {
		SpringApplication.run(AuthStartBean.class, args);
	}
}
