package com.pactera.business.controller;

import com.pactera.business.service.ThemeService;
import com.pactera.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @Author zhaodong
 * @Description
 * @Date 2018/12/29 10:55
 */
@Validated
@RestController
@RequestMapping("/theme")
public class LaunVehicleController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/detail")
    ResultData detail(String id) {
        return new ResultData(themeService.detail(id));
    }

    /**
     * 主题搜索
     * @param value 关键字
     * @return 主题列表
     */
    @GetMapping("/search")
    ResultData search(String value, String apiKey,double version) {
        return new ResultData(themeService.search(value, apiKey,version));
    }

    /**
     * 统计下载（应用）量
     * @param id 主题id
     * @param type 0:下载 1：应用
     * @return 数量
     */
    @PostMapping("/count")
    ResultData count (@NotNull String id, @NotNull int type) {
        themeService.count(id, type);
        return new ResultData();
    }


}
