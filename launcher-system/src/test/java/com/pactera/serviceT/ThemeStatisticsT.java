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

	@Autowired
	private LaunThemeService launThemeService;

	public void testTopTheme() {

		String channelId = null;
		Long type = null;
		Map<String, Object> topTheme = launStatisticsService.topTheme(channelId, type);

		System.out.println(JsonUtils.ObjectToJson(topTheme));
	}

	public void testThemeStatistics() {

		String channelId = null;
		Integer type = 2;
		List<Map<String, Object>> selectThemeStatistics = launStatisticsService.selectThemeStatistics(channelId, type);

		System.out.println(JsonUtils.ObjectToJson(selectThemeStatistics));
	}

	public void testThemeStatistics1() {

		String channelId = "13754Z,33HV98";
		String stiem = "2018-07-29";
		String etime = "2018-07-30";
		Map<String, Object> themeZheStatistics = launStatisticsService.themeZheStatistics(channelId, stiem, etime);

		System.out.println(JsonUtils.ObjectToJson(themeZheStatistics));
	}

	@Test
	public void testThemeStatistics2() {

		List<Map<String, String>> effeCount = launThemeService.getEffeCount();
		for (Map<String, String> map : effeCount) {
			System.out.println(map.get("channel_id"));
			System.out.println(map.get("count"));
		}
	}
}
