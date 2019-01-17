package com.pactera.business.service;

import com.pactera.dto.ThemeDTO;
import com.pactera.result.ResultData;
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

@FeignClient("launcher-service")
public interface RemoteThemeService {

    @RequestMapping(method = RequestMethod.GET, value = "/theme/detail/{id}")
    ResultData<ThemeDTO> detail(@PathVariable("id") String id);
}
