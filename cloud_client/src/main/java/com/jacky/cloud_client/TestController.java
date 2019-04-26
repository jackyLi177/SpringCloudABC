package com.jacky.cloud_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @date ：2019/4/26 11:34
 */
@RestController
public class TestController {

    @Value(value = "${server.port}")
    private Integer port;

    @GetMapping("/index")
    public String index(){
        return "client_1" + this.port;
    }

}
