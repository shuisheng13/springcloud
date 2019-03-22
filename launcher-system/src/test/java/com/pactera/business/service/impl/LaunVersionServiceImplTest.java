package com.pactera.business.service.impl;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunVersionService;
import com.pactera.vo.LaunPage;
import com.pactera.vo.LaunVersionsVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LaunVersionServiceImplTest extends Tester {
    @Autowired
    LaunVersionService launVersionService;

    @Test
    public void list() {
        List<LaunVersionsVo> result = launVersionService.list(190312826449271L);
        System.out.println(result);
    }

    @Test
    public void query() {
        LaunPage<LaunVersionsVo> query = launVersionService.query(1,10);
        System.out.println(query);
    }
    //@Test
    //public void describe() {
    //    launVersionService.describe(190303711929979L, "描述");
    //}
}
