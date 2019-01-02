package com.pactera.business.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName ThemeService
 * @Description
 * @Author xukj
 * @Date 2018/12/29 16:32
 * @Version
 */

@FeignClient(url="http://127.0.0.1:9091/", name="system")
public interface RemoteThemeService {

    @RequestMapping(method = RequestMethod.GET, value = "/theme/detail/{id}")
    String detail(@PathVariable("id") String id);
}
