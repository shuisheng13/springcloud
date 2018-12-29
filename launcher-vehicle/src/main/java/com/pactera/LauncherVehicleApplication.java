package com.pactera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.pactera.business.dao")
public class LauncherVehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LauncherVehicleApplication.class, args);
    }

}

