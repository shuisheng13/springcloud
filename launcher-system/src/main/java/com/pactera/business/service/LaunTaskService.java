package com.pactera.business.service;

import java.util.Date;
import java.util.List;

import com.pactera.domain.LaunChannel;

/**
 * 定时任务业务类
 * 
 * @description:
 * @author:LL
 * @since:2018年8月22日 上午9:35:14
 */
public interface LaunTaskService {

	/**
	 * 主题定时任务拉取统计数据
	 * 
	 * @author LL
	 * @date 2018年8月22日 上午9:57:37
	 * @param channelList渠道集合
	 * @param versionList版本集合
	 * @param timeList时间集合
	 * @return void
	 */
	void themeTaskStatistics(List<LaunChannel> channelList, List<String> timeList);

	/**
	 * 车辆相关数量统计
	 * 
	 * @author LL
	 * @date 2018年8月22日 上午9:57:37
	 * @param channelList渠道集合
	 * @param versionList版本集合
	 * @param timeList时间集合
	 * @return void
	 */
	void carTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> dateList);

	/**
	 * 车辆应用使用数量统计
	 * 
	 * @author LL
	 * @date 2018年8月22日 上午9:57:37
	 * @param channelList渠道集合
	 * @param versionList版本集合
	 * @param timeList时间集合
	 * @return void
	 */
	void applicationTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> dateList);

	/**
	 * 广告统计使用详情
	 * 
	 * @author LL
	 * @date 2018年8月23日 下午3:48:01
	 * @param dateList日期列表
	 * @return void
	 */
	void adverTaskStatistics(List<Date> dateList);

	/**
	 * widget数量统计
	 * 
	 * @author LL
	 * @date 2018年8月22日 上午9:57:37
	 * @param channelList渠道集合
	 * @param versionList版本集合
	 * @param timeList时间集合
	 * @return void
	 */
	void widgetTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> dateList);
}
