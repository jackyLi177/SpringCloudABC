package com.jacky.nacos_config_center;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@NacosPropertySource(dataId = "nacos-config-demo",autoRefreshed = true)
public class NacosConfigCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigCenterApplication.class, args);
    }

    @NacosValue(value = "${nacos.config.name:default}",autoRefreshed = true)
    private String name;

    @GetMapping("/getName")
    public String getName(){
        return name;
    }

}
