package com.jacky.nacos_consumer.controller;

import com.jacky.nacos_consumer.feign.ProviderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Liyj
 * @Date 2020/9/30
 */
@RestController
public class HelloController {

    @Autowired
    private ProviderFeign providerFeign;

    @GetMapping("/hello")
    public String test(){
        return providerFeign.test();
    }

}
