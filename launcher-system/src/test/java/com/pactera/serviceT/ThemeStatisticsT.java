package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import com.pactera.base.Tester;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.dao.LaunWidgetPropertyMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.business.service.LaunThemeConfigService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.util.ThemeWidgetDetail;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.utlis.TimeUtils;

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
	private LaunThemeStatisticsMapper launThemeStatisticsMapper;

	@Autowired
	private LaunCarStatisticsMapper launCarStatisticsMapper;

	@Autowired
	private LaunApplicationStatisticsMapper launApplicationStatisticsMapper;

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Autowired
	private LaunChannelService launChannelService;

	/**
	 * 插入主题统计数据
	 * 
	 * @author LL
	 * @date 2018年8月21日 上午10:40:54
	 * @param
	 * @return void
	 */
	public void insertThemeStatistics() {

		// 9VXG7W BDL945 21795J 586472

		LaunThemeStatistics launThemeStatistics = null;
		String[] channels = new String[] { "9VXG7W", "BDL945", "21795J", "586472" };
		Long[] themeIds = new Long[] { 180800206786478L, 180725830221935L, 180716077521018L, 180718285715720L };

		// 日期为7月10日
		Date string2Date = TimeUtils.string2Date("2018-07-10", "yyyy-MM-dd");

		for (String channel : channels) {
			for (Long long1 : themeIds) {
				for (int i = 0; i < 20; i++) {
					String times = TimeUtils.date2String(TimeUtils.dateReckon(string2Date, i), "yyyy-MM-dd");

					launThemeStatistics = new LaunThemeStatistics();
					launThemeStatistics.setChannelId(channel);
					launThemeStatistics.setStrNumStartTime("2");
					launThemeStatistics.setNumStartTime(times);
					launThemeStatistics.setCount(getNum());
					launThemeStatistics.setEffeTheme(getNum());
					launThemeStatistics.setCountCar(getNum());
					launThemeStatistics.setThemeId(long1);
					launThemeStatistics.setId(IdUtlis.Id());
					launThemeStatisticsMapper.insertSelective(launThemeStatistics);
				}
			}
		}

	}

	/**
	 * 插入车辆统计数据
	 * 
	 * @author LL
	 * @date 2018年8月21日 上午10:40:54
	 * @param
	 * @return void
	 */
	public void insertCarStatistics() {

		// 9VXG7W BDL945 21795J 586472

		LaunCarStatistics launcarStatistics = null;
		String[] channels = new String[] { "9VXG7W", "BDL945", "21795J", "586472" };
		String[] versions = new String[] { "1.1.1.0", "1.1.2.0", "1.1.3.0", "1.1.4.0" };

		// 日期为7月10日
		Date string2Date = TimeUtils.string2Date("2018-07-10 10:00:00");

		for (String channel : channels) {
			for (String version : versions) {
				for (int i = 0; i < 20; i++) {

					launcarStatistics = new LaunCarStatistics();
					launcarStatistics.setChannelId(channel);
					launcarStatistics.setVersion(version);
					launcarStatistics.setAddUpNum(getNum());
					launcarStatistics.setCarActive(getNum());
					launcarStatistics.setCarAvgTime(getNum() + "");
					launcarStatistics.setCarNum(getNum());
					launcarStatistics.setCarStart(getNum());
					launcarStatistics.setUpGradeNum(getNum());
					launcarStatistics.setCarTime(TimeUtils.dateReckon(string2Date, i));
					launcarStatistics.setId(IdUtlis.Id());
					launCarStatisticsMapper.insertSelective(launcarStatistics);
				}
			}
		}

	}

	@Test
	public void removeRedis() {
		Map<String, Long> allMap = new HashMap<String, Long>();
		allMap.put("catNum", 0L);// 每日新增车辆
		allMap.put("carActive", 0L);// 活跃吃凉
		allMap.put("carStart", 0L);// 启动次数
		allMap.put("addUpNum", 0L);// 累计车辆
		// 获取渠道信息集合
		List<LaunChannel> channelList = launChannelService.findAll(null);
		for (LaunChannel launChannel : channelList) {
			valueOperations.set(ConstantUtlis.TODAY_STATISTICS + launChannel.getChannelId(), allMap);
		}
		valueOperations.set(ConstantUtlis.TODAY_STATISTICS, allMap);
	}

	public void insertApplicationStatistics() {

		// 9VXG7W BDL945 21795J 586472

		LaunApplicationStatistics launApplicationStatistics = null;
		String[] channels = new String[] { "9VXG7W", "BDL945" };

		// 日期为7月10日
		Date string2Date = TimeUtils.string2Date("2018-07-10 10:00:00");

		for (String channel : channels) {
			for (int i = 0; i < 20; i++) {
				launApplicationStatistics = new LaunApplicationStatistics();
				launApplicationStatistics.setApplicationId("zxxiaoshuodp.book");
				launApplicationStatistics.setChannelId(channel);
				launApplicationStatistics.setStartUpNum(getNum());
				launApplicationStatistics.setId(IdUtlis.Id());
				launApplicationStatistics.setApplicationTime(TimeUtils.dateReckon(string2Date, i));
				launApplicationStatisticsMapper.insertSelective(launApplicationStatistics);
			}
		}

	}

	public long getNum() {
		long i = (long) (Math.random() * 100);
		return i;
	}

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

	public void testThemeStatistics2() {

		List<Map<String, String>> effeCount = launThemeService.getEffeCount();
		for (Map<String, String> map : effeCount) {
			System.out.println(map.get("channel_id"));
			System.out.println(map.get("count"));
		}
	}
}
