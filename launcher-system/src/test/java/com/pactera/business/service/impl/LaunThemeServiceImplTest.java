package com.pactera.business.service.impl;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunThemeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;


public class LaunThemeServiceImplTest extends Tester {

    @Autowired
    LaunThemeService launThemeService;

    @Test
    public void selectById() {
        Map<String, Object> map = launThemeService.selectById("SYZTCS0931873");
        Assert.notEmpty(map,"结果为空");
        System.out.println(map);
    }
}
