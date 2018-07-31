package com.pactera.serviceT;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunClientThemeService;
import com.pactera.vo.LaunThemeShopVo;

/**
 * @description:应用相关测试
 * @author:Scott
 * @since:2018年4月26日 下午4:10:37
 */
public class ThemeServiceT extends Tester {

	@Autowired
	private LaunClientThemeService launClientThemeService;

	@Test
	public void test() {

		String channle = "13754Z";
		String version = "1";
		Long screenHeight = 720L;
		Long screenWidth = 1080L;
		Integer type = 1;
		String userId = null;
		String city = null;
		List<LaunThemeShopVo> themeList = launClientThemeService.getThemeList(channle, version, screenHeight,
				screenWidth, userId, city, type);

		System.out.println(themeList.size());
	}
}
