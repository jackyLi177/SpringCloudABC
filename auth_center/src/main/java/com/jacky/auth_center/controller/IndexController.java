package com.jacky.auth_center.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/2 5:40 下午
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index index";
    }

}
