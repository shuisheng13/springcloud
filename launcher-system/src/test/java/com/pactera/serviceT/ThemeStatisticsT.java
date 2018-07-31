package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.base.Tester;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.dao.LaunWidgetPropertyMapper;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.business.service.LaunThemeConfigService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.util.ThemeWidgetDetail;
import com.pactera.utlis.JsonUtils;

/**
 * 测试widget信息转为前端可用json
 * 
 * @author lzp
 *
 */
@SuppressWarnings("all")
public class ThemeStatisticsT extends Tester {

	@Autowired
	private LaunStatisticsService launStatisticsService;

	@Test
	public void testTopTheme() {

		String channelId = null;
		Long type = null;
		Map<String, Object> topTheme = launStatisticsService.topTheme(channelId, type);

		System.out.println(JsonUtils.ObjectToJson(topTheme));
	}
}
