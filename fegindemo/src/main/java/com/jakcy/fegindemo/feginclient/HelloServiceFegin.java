package com.jakcy.fegindemo.feginclient;

import com.jakcy.fegindemo.fallbackImpl.HelloServiceFeginFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：lyj
 * @date ：2019/4/26 14:28
 */
//feign 整合了ribbon，具备负载均衡的功能
//这是一个feign客户端，调用name服务名的服务，轮询访问可用的服务器
@FeignClient(name = "author",fallback = HelloServiceFeginFallbackImpl.class)
@Component(value = "helloServiceFegin")
public interface HelloServiceFegin {

    //不支持@GetMapping()注解,使用requestMapping中的value属性去匹配方法，与本方法名无关，但是参数列表必须相同
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    String fasfsaf();

}



