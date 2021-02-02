package com.mic.user.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mic.user.api.service"})
//@ComponentScan(basePackages = {"com.mic.user.api"})
public class MicUserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicUserCenterApplication.class, args);
    }

}
