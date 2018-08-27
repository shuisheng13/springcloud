package com.pactera.business.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunTaskService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunAttributeVo;

import tk.mybatis.mapper.entity.Example;

@Component
public class DataStatisticsTask {

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunTaskService launTaskService;

	@Autowired
	private LaunThemeStatisticsMapper launThemeStatisticsMapper;

	@Autowired
	private LaunChannelService launChannelService;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	/**
	 * 定时获取每日使用数据（每天凌晨0点1分）
	 * 
	 * @author LL
	 * @date 2018年7月31日 下午2:49:33
	 * @return void
	 */
	// @Scheduled(cron = "${task.cron.getThemeStatistics}")
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
			String date2String = TimeUtils.date2String(TimeUtils.dateReckon(now, -i), "yyyy-MM-dd");
			timeList.add(date2String);
			dateList.add(TimeUtils.dateReckon(now, -i));
		}

		// 执行主体统计
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

	public void test() {
		// 拿到当前有效主题list
		List<LaunThemeAdministration> list = new ArrayList<LaunThemeAdministration>();
		for (LaunThemeAdministration theme : list) {

		}

		Map<String, Map<String, LaunThemeStatistics>> map = new HashMap<>();
		/**
		 * 初始化30天的数据。
		 */
		// 30天日期
		List<String> dateList = new ArrayList<>();

		Date now = new Date();
		for (int i = 0; i < 30; i++) {
			String dateStr = TimeUtils.date2String(TimeUtils.dateReckon(now, i), "yyyy-MM-dd");
			dateList.add(dateStr);
		}

		List<LaunChannel> findAll = launChannelService.findAll(null);

		Map<String, LaunThemeStatistics> date2Theme = new HashMap<>();
		LaunThemeStatistics oneObj = null;
		for (LaunChannel launChannel : findAll) {
			for (String string : dateList) {
				oneObj = new LaunThemeStatistics();
				oneObj.setChannelId(launChannel.getChannelId());
				oneObj.setEffeTheme(0L);
				oneObj.setNumStartTime(string);
				date2Theme.put(string, oneObj);
			}
			map.put(launChannel.getChannelId(), date2Theme);
		}

	}

	// 每天晚上11点55执行；计算当天实际有效主题数量
	public void getThemeEffeStatistics() {
		// 根据渠道查询有效主题数量
		List<Map<String, String>> list = launThemeService.getEffeCount();

		// 统计属于所有渠道的主题
		int allCount = 0;

		// 所属渠道统计list
		List<Map<String, String>> channelList = new LinkedList<Map<String, String>>();

		for (Map<String, String> map : list) {
			if (map.get("channel_id") == null) {
				allCount += Integer.parseInt(map.get("count"));
			} else {
				channelList.add(map);
			}
		}

		// 遍历更新统计表数据
		LaunThemeStatistics launThemeStatistics = null;
		String nowTime = TimeUtils.date2String(new Date(), "yyyy-MM-dd");
		for (Map<String, String> map : channelList) {
			launThemeStatistics = new LaunThemeStatistics();
			launThemeStatistics.setEffeTheme(Long.parseLong(map.get("count") + allCount));
			Example example = new Example(LaunThemeStatistics.class);
			example.createCriteria().andEqualTo("channelId", map.get("channel_id"));
			example.createCriteria().andEqualTo("numStartTime", nowTime);
			launThemeStatisticsMapper.updateByExampleSelective(launThemeStatistics, example);
		}

	}
}
