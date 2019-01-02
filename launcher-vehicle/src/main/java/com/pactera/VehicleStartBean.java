package com.pactera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = "com.pactera.business.dao")
@Configuration
public class VehicleStartBean {

    public static void main(String[] args) {
        SpringApplication.run(VehicleStartBean.class, args);
    }

}

