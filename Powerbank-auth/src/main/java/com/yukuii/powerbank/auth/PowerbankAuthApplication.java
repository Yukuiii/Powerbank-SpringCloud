package com.yukuii.powerbank.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;




@SpringBootApplication
@EnableDiscoveryClient
public class PowerbankAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PowerbankAuthApplication.class, args);
    }
}
