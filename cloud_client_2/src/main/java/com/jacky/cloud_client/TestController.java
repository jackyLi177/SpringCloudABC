package com.jacky.cloud_client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @date ：2019/4/26 11:33
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public String index(){
        return "client_2";
    }

}
