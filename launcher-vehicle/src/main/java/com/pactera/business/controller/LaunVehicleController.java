package com.pactera.business.controller;

import com.pactera.business.service.ThemeService;
import com.pactera.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhaodong
 * @Description
 * @Date 2018/12/29 10:55
 */
@RestController
@RequestMapping("/theme")
public class LaunVehicleController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/{id}")
    ResultData theme(@PathVariable String id) {
        return new ResultData(themeService.detail(id));
    }

    /**
     * 主题搜索
     * @param value 关键字
     * @return 主题列表
     */
    @GetMapping("/search/{value}")
    ResultData search(@PathVariable String value) {
        themeService.search(value);
        return new ResultData();
    }

    /**
     * 统计下载（应用）量
     * @param id 主题id
     * @param type 0:下载 1：应用
     * @return 数量
     */
    @GetMapping("/count/{id}/{type}")
    ResultData count (@PathVariable String id, @PathVariable int type) {
        themeService.count(id, type);
        return new ResultData();
    }


}
