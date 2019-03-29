package com.pactera.business.service.impl;


import com.pactera.business.service.LaunVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LaunVersionServiceImplTest {

    @Autowired
    LaunVersionService launVersionService;

    @Test
    public void add() {
        launVersionService.add(8.0,"v8.0","a","测试格式");
    }
}
