package com.pactera.business.controller;

import com.pactera.business.service.LaunLayoutService;
import com.pactera.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LaunHotKeyword
 * @Description
 * @Author xukj
 * @Date 2019/3/4 10:33
 * @Version
 */
@RestController
public class LaunLayout {

    @Autowired
    private LaunLayoutService launLayoutService;

    @GetMapping("/layout")
    public ResultData query() {
        return new ResultData(launLayoutService.query());
    }
}
