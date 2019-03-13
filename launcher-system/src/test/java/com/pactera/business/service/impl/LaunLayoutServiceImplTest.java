package com.pactera.business.service.impl;


import com.pactera.base.Tester;
import com.pactera.business.service.LaunLayoutService;
import com.pactera.domain.LaunLayout;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class LaunLayoutServiceImplTest extends Tester {

    @Autowired
    LaunLayoutService launLayoutService;

    @Test
    public void query() {
        List<LaunLayout> list = launLayoutService.query();
        Assert.assertEquals("格式列表数量与数据库不符合",3,list.size());
    }

    //@Test
    public void findById() {
    }
}
