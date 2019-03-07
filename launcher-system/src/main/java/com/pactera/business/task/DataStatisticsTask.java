package com.pactera.business.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pactera.business.dao.LaunThemeEffeMapper;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunTaskService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeEffe;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunAttributeVo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class DataStatisticsTask {

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunTaskService launTaskService;

	@Autowired
	private LaunThemeEffeMapper launThemeEffeMapper;

	@Autowired
	private LaunChannelService launChannelService;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	/**
	 * 定时获取每日使用数据（每天凌晨1点）
	 * 
	 * @author LL
	 * @date 2018年7月31日 下午2:49:33
	 * @return void
	 */
	//@Scheduled(cron = "${task.cron.getTodayStatistics}")
	@Async
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

		insertThirtyEffeTheme();
	}

	/**
	 * 今日概况数据获取（半个小时）
	 * 
	 * @author LL
	 * @date 2018年7月31日 下午2:49:33
	 * @return void
	 */
	//@Scheduled(cron = "${task.cron.getThemeStatistics}")
	@Async
	public void taskTodayNum() {
		// 获取渠道信息集合
		List<LaunChannel> channelList = launChannelService.findAll(null);
		// 执行widget统计
		launTaskService.todayTaskStatistics(channelList);
	}

	public void insertThirtyEffeTheme() {
		/**
		 * 初始化30天的数据。
		 */
		// 30天日期
		List<Date> dateList = new ArrayList<>();

		// 创建后30天的map集合
		Date now = new Date();
		for (int i = 0; i < 30; i++) {
			Date dateReckon = TimeUtils.dateReckon(now, i);
			dateList.add(dateReckon);
		}

		Example example = new Example(LaunThemeAdministration.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("status", 2);

		Map<Long, List<LaunThemeAdministration>> channelThemeMap = new HashMap<Long, List<LaunThemeAdministration>>();
		List<LaunThemeAdministration> list = launThemeMapper.selectByExample(example);
		for (LaunThemeAdministration theme : list) {
			List<LaunThemeAdministration> list2 = channelThemeMap.get(theme.getCreatorChannelId());
			if (list2 == null) {
				list2 = new ArrayList<>();
				list2.add(theme);
			} else {
				list2.add(theme);

			}
			channelThemeMap.put(theme.getCreatorChannelId(), list2);
		}

		List<LaunChannel> findAll = launChannelService.findAll(null);

		LaunThemeEffe record = null;
		for (LaunChannel launChannel : findAll) {
			for (Date date : dateList) {
				Long count = 0L;
				List<LaunThemeAdministration> themeList = channelThemeMap.get(launChannel.getId());
				for (LaunThemeAdministration theme : themeList) {
					boolean betweenDate = TimeUtils.isBetweenDate(theme.getStartTime(), theme.getEndTime(), date);
					if (betweenDate) {
						count++;
					}
				}
				record = new LaunThemeEffe();
				record.setChannelId(launChannel.getChannelId());
				record.setEffeTheme(count);
				record.setNumStartTime(TimeUtils.date2String(date, "yyyy-MM-dd"));
				launThemeEffeMapper.insertSelective(record);
			}
		}

	}

	/**
	 * 定时获取今日有效主题数量（每晚11点50分）
	 * 
	 * @author LL
	 * @date 2018年7月31日 下午2:49:33
	 * @return void
	 */
	@Async
	//@Scheduled(cron = "${task.cron.getThemeEffeStatistics}")
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
		LaunThemeEffe record = null;
		String nowTime = TimeUtils.date2String(new Date(), "yyyy-MM-dd");
		for (Map<String, String> map : channelList) {
			record = new LaunThemeEffe();
			record.setEffeTheme(Long.parseLong(map.get("count") + allCount));
			Example example = new Example(LaunThemeStatistics.class);
			example.createCriteria().andEqualTo("channelId", map.get("channel_id"));
			example.createCriteria().andEqualTo("numStartTime", nowTime);
			launThemeEffeMapper.updateByExampleSelective(record, example);
		}

	}
}
