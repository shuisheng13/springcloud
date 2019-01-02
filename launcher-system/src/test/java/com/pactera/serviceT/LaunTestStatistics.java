package com.pactera.serviceT;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.business.service.LaunTaskService;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.domain.LaunChannel;
import com.pactera.utlis.JsonUtils;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunAttributeVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:应用相关测试
 * @author:Scott
 * @since:2018年4月26日 下午4:10:37
 */
public class LaunTestStatistics extends Tester {

	@Autowired
	private LaunTaskService launTaskService;

	@Autowired
	private LaunChannelService launChannelService;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	@Autowired
	private LaunStatisticsService launStatisticsService;

	public void taskStatistics() {

		// 获取渠道信息集合
		List<LaunChannel> channelList = launChannelService.findAll(null);

		// 获取版本信息集合
		List<LaunAttributeVo> findWidgetVersion = launWidgetManagerService.findWidgetVersion();
		List<String> versionList = findWidgetVersion.stream().map(LaunAttributeVo::getAttributeValue)
				.collect(Collectors.toList());

		// 获取请求数据时间集合
		List<String> timeList = new ArrayList<String>();
		List<Date> dateList = new ArrayList<Date>();
		Date now = new Date();
		for (int i = 1; i <= 7; i++) {
			String date2String = TimeUtils.date2String(TimeUtils.dateReckon(now, -i), "yyyyMMdd");
			timeList.add(date2String);
			dateList.add(TimeUtils.dateReckon(now, -i));
		}

		// 执行主题统计
		launTaskService.themeTaskStatistics(channelList, timeList);
		// 执行车辆统计
		launTaskService.carTaskStatistics(channelList, versionList, dateList);
		// 执行应用统计
		launTaskService.applicationTaskStatistics(channelList, versionList, dateList);
		// 执行广告统计
		launTaskService.adverTaskStatistics(dateList);
		// 执行widget统计
		launTaskService.widgetTaskStatistics(channelList, versionList, dateList);

	}

	public void today1() {

		// 获取渠道信息集合
		List<LaunChannel> channelList = launChannelService.findAll(null);
		// 执行widget统计
		launTaskService.todayTaskStatistics(channelList);
	}

	@Test
	public void today2() {

		// 获取渠道信息集合
		List<LaunChannel> channelList = launChannelService.findAll(null);
		for (LaunChannel launChannel : channelList) {
			Map<String, Long> yesCar = launStatisticsService.yesCar(launChannel.getChannelId());
			String objectToJson = JsonUtils.ObjectToJson(yesCar);
			System.out.println(objectToJson);
		}
		// 执行widget统计

	}

}
