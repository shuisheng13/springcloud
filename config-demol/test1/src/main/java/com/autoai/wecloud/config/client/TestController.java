package com.autoai.wecloud.config.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @RequestMapping(value = "/test1")
    public String  test1(){
        return "consul is success";
    }
}
