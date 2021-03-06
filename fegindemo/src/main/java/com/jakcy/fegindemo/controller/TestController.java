package com.jakcy.fegindemo.controller;

import com.jakcy.fegindemo.feginclient.HelloServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @date ：2019/4/26 14:30
 */
@RestController
public class TestController {

    /**
     * @Autowired 默认先按照类型在容器中找相应的bean，如果有多个该类型的bean就按照名称来装配
     */
    @Autowired
    HelloServiceFegin helloServiceFegin;

    @GetMapping("/test")
    public String test(){
        return helloServiceFegin.fasfsaf();
    }

}
