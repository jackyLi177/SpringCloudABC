package com.jakcy.fegindemo.controller;

import com.jakcy.fegindemo.feginclient.HelloServiceFegin;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @date ：2019/4/26 14:30
 */
@RestController
public class TestController {

    @Autowired
    HelloServiceFegin helloServiceFegin;

    @GetMapping("/test")
    public String test(){
        return helloServiceFegin.index();
    }

}
