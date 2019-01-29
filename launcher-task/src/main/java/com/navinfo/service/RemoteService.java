package com.navinfo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("launcher-service")
public interface RemoteService {

    @RequestMapping(method = RequestMethod.POST, value = "/theme/autoUpDown")
    void autoUpDown(@RequestParam("timestamp") String timestamp);
}
