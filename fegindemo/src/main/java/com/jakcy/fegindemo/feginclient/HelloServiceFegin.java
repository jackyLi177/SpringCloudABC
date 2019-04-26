package com.jakcy.fegindemo.feginclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：lyj
 * @date ：2019/4/26 14:28
 */
//这是一个feign客户端，调用name服务名的服务，轮询访问可用的服务器
@FeignClient(name = "hello-service-1")
public interface HelloServiceFegin {

    //不支持@GetMapping()注解
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    String index();

}
