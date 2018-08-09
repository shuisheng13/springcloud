package com.pactera.business.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.utlis.HttpClientUtil;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.MsgResponseVO;

import tk.mybatis.mapper.entity.Example;

@Component
public class DataStatisticsTask {

	private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private LaunThemeStatisticsMapper launThemeStatisticsMapper;

	@Autowired
	private LaunChannelService launChannelService;

	/**
	 * 定时获取每日主题使用数据（每天凌晨2点 ）
	 * 
	 * @author LL
	 * @date 2018年7月31日 下午2:49:33
	 * @return void
	 */
	// @Scheduled(cron = "${task.cron.getThemeStatistics}")
	public void getThemeStatistics() {

		// 接口地址
		String httpUrl = "";
		// 接口参数
		String params = "";
		String res = HttpClientUtil.sendHttpGet(httpUrl, params);

		try {
			MsgResponseVO<LaunThemeStatistics> readValue = mapper.readValue(res,
					new TypeReference<MsgResponseVO<LaunThemeStatistics>>() {
					});

			if (readValue != null && "200".equals(readValue.getCode())) {
				List<LaunThemeStatistics> list = readValue.getData();

				// 业务逻辑处理
			}

			// 业务逻辑
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			String dateStr = TimeUtils.date2String(TimeUtils.dateReckon(now, 0), "yyyy-MM-dd");
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
