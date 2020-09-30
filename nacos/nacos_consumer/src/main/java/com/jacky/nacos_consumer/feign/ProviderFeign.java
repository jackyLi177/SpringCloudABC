package com.jacky.nacos_consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("nacos-provider")
public interface ProviderFeign {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String test();

}

