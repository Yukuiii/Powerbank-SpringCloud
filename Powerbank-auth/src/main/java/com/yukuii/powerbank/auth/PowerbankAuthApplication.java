package com.yukuii.powerbank.auth;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;




@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.yukuii.powerbank.auth.mapper")
public class PowerbankAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PowerbankAuthApplication.class, args);
    }
}
