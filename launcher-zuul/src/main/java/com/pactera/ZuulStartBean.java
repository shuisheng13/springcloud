package com.pactera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulStartBean {
	public static void main(String[] args) {
		SpringApplication.run(ZuulStartBean.class, args);
	}
}
