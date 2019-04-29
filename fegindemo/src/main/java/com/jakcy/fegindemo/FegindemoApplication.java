package com.jakcy.fegindemo;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients /* 开启fegin功能 */
public class FegindemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FegindemoApplication.class, args);
    }

    @Bean
    public IRule randonRule(){
        return new RandomRule();
    }

}
