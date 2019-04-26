package com.jakcy.fegindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients /* 开启fegin功能 */
public class FegindemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FegindemoApplication.class, args);
    }

}
