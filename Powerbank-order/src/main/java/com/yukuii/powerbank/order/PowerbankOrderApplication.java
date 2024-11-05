package com.yukuii.powerbank.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PowerbankOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PowerbankOrderApplication.class, args);
    }
} 