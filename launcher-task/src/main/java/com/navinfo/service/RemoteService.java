package com.navinfo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("launcher-service")
public interface RemoteService {

    @RequestMapping(method = RequestMethod.GET, value = "/theme/autoUpDown")
    void autoUpDown(long timestamp);
}
