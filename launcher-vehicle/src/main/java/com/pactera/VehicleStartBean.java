package com.pactera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan(basePackages = "com.pactera.business.dao")
@Configuration
@EnableDiscoveryClient
@EnableFeignClients({"com.navinfo.wecloud.saas.api.facade","com.pactera.business.service"})

public class VehicleStartBean {

    public static void main(String[] args) {
        SpringApplication.run(VehicleStartBean.class, args);
    }

}

