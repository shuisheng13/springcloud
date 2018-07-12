package com.pactera;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestDomain.class)
@SpringBootApplication
public class TestDomain {

	@Test
	public void contextLoads() {
		System.out.println("******************************OK!*****************************");
	}

}
