package com.pactera.business.controller;

import com.pactera.business.service.LaunVersionService;
import com.pactera.result.ResultData;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LaunVersionController
 * @Description
 * @Author xukj
 * @Date 2019/1/7 14:18
 * @Version
 */
@Validated
@RestController
@RequestMapping("/version")
public class LaunVersionController {


    @Autowired
    LaunVersionService launVersionService;

    /**
     * 车机端上报
     * @param version
     * @return
     */
    @PostMapping("/add")
    ResultData add(@NonNull double version, @NonNull String apiKey, @NonNull String versionName) {
        return new ResultData(launVersionService.add(version, versionName, apiKey));
    }
}
