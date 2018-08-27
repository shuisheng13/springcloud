package com.pactera.business.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.business.dao.LaunAdverStatisticsMapper;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.business.service.LaunTaskService;
import com.pactera.domain.LaunAdverStatistics;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.domain.LaunWidget;
import com.pactera.utlis.HttpClientUtil;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.MsgResponseVO;

import tk.mybatis.mapper.entity.Example;

/**
 * 定时任务业务类
 * 
 * @author:LL
 * @since:2018年8月22日 上午9:39:06
 */
@Service
public class LaunTaskServiceImpl implements LaunTaskService {

	private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@Autowired
	private LaunThemeStatisticsMapper launThemeStatisticsMapper;

	@Autowired
	private LaunCarStatisticsMapper launCarStatisticsMapper;

	@Autowired
	private LaunApplicationStatisticsMapper launApplicationStatisticsMapper;

	@Autowired
	private LaunAdverStatisticsMapper launAdverStatisticsMapper;

	@Autowired
	private LaunWidgetStatisticsMapper launWidgetStatisticsMapper;

	@Autowired
	private LaunWidgetManagerMapper launWidgetManagerMapper;

	@Override
	public void themeTaskStatistics(List<LaunChannel> channelList, List<String> timeList) {

		Map<String, String> map = new HashMap<String, String>();
		// 接口地址
		String httpUrl = "";
		String params = "";
		String res = "";

		map.put("eid", "themeClick");

		for (int i = 0; i < timeList.size(); i++) {
			for (LaunChannel channel : channelList) {
				String time = timeList.get(i);
				String channelId = channel.getChannelId();
				map.put("time", time);
				map.put("channelId", channelId);
				params = HttpClientUtil.convertStringParamter(map);
				res = HttpClientUtil.sendHttpGet(httpUrl, params);

				try {
					MsgResponseVO<LaunThemeStatistics> readValue = mapper.readValue(res,
							new TypeReference<MsgResponseVO<LaunThemeStatistics>>() {
							});

					if (readValue != null && "200".equals(readValue.getCode())) {
						List<LaunThemeStatistics> list = readValue.getData();

						if (i == 0) {// 昨日的数据走插入
							for (LaunThemeStatistics launThemeStatistics : list) {
								launThemeStatistics.setChannelId(channelId);
								launThemeStatistics.setNumStartTime(time);
								launThemeStatisticsMapper.insertSelective(launThemeStatistics);
							}
						} else {// 更新
							for (LaunThemeStatistics launThemeStatistics : list) {

								Example example = new Example(LaunThemeStatistics.class);
								example.createCriteria().andEqualTo("channelId", channelId)
										.andEqualTo("numStartTime", time)
										.andEqualTo("themeId", launThemeStatistics.getThemeId());
								launThemeStatisticsMapper.updateByExampleSelective(launThemeStatistics, example);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void carTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> timeList) {

		Example example = new Example(LaunCarStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("carTime", dateReckon);
		// 删除前一周的数据
		launCarStatisticsMapper.deleteByExample(example);

		Map<String, String> map = new HashMap<String, String>();
		// 接口地址
		String httpUrl = "";
		String params = "";
		String res = "";

		map.put("eid", "themeClick");

		for (int i = 0; i < timeList.size(); i++) {
			for (LaunChannel channel : channelList) {
				for (String version : versionList) {
					Date time = timeList.get(i);
					String channelId = channel.getChannelId();
					map.put("time", TimeUtils.date2String(time, "yyyy-MM-dd"));
					map.put("channelId", channelId);
					map.put("version", version);
					params = HttpClientUtil.convertStringParamter(map);
					res = HttpClientUtil.sendHttpGet(httpUrl, params);
					// TODO

					List<LaunCarStatistics> list = new ArrayList<>();

					if (i == 0) {// 昨日的数据走插入
						for (LaunCarStatistics launCarStatistics : list) {
							launCarStatistics.setVersion(version);
							launCarStatistics.setChannelId(channelId);
							launCarStatistics.setCarTime(time);
							launCarStatisticsMapper.insertSelective(launCarStatistics);
						}
					}
				}
			}
		}

	}

	@Override
	public void applicationTaskStatistics(List<LaunChannel> channelList, List<String> versionList,
			List<Date> dateList) {

		Example example = new Example(LaunApplicationStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("applicationTime", dateReckon);
		// 删除前一周的数据
		launApplicationStatisticsMapper.deleteByExample(example);

		Map<String, String> map = new HashMap<String, String>();
		// 接口地址
		String httpUrl = "";
		String params = "";
		String res = "";

		map.put("eid", "themeClick");

		for (int i = 0; i < dateList.size(); i++) {
			for (LaunChannel channel : channelList) {
				for (String version : versionList) {
					Date time = dateList.get(i);
					String channelId = channel.getChannelId();
					map.put("time", TimeUtils.date2String(time, "yyyy-MM-dd"));
					map.put("channelId", channelId);
					map.put("version", version);
					params = HttpClientUtil.convertStringParamter(map);
					res = HttpClientUtil.sendHttpGet(httpUrl, params);
					// TODO

					List<LaunApplicationStatistics> list = new ArrayList<>();
					for (LaunApplicationStatistics launApp : list) {
						launApp.setVersion(version);
						launApp.setChannelId(channelId);
						launApp.setApplicationTime(time);
						launApplicationStatisticsMapper.insertSelective(launApp);
					}
				}
			}
		}
	}

	@Override
	public void adverTaskStatistics(List<Date> dateList) {

		Example example = new Example(LaunAdverStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("adverHour", dateReckon);
		// 删除前一周的数据
		launAdverStatisticsMapper.deleteByExample(example);

		Map<String, String> map = new HashMap<String, String>();
		// 接口地址
		String httpUrl = "";
		String params = "";
		String res = "";

		for (Date date : dateList) {
			map.put("time", TimeUtils.date2String(date, "yyyy-MM-dd"));
			params = HttpClientUtil.convertStringParamter(map);
			res = HttpClientUtil.sendHttpGet(httpUrl, params);

			List<LaunAdverStatistics> list = new ArrayList<>();

			for (LaunAdverStatistics launAdver : list) {

				launAdver.setAdverHour(date);
				launAdverStatisticsMapper.insertSelective(launAdver);
			}
		}
	}

	@Override
	public void widgetTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> dateList) {
		Example example = new Example(LaunWidgetStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("adverHour", dateReckon);
		// 删除前一周的数据
		launWidgetStatisticsMapper.deleteByExample(example);

		// 获取基础widget数据
		Example example2 = new Example(LaunWidget.class);
		example2.createCriteria().andEqualTo("type", 0);
		List<LaunWidget> widgetList = launWidgetManagerMapper.selectByExample(example);

		Map<String, String> map = new HashMap<String, String>();
		// 接口地址
		String httpUrl = "";
		String params = "";
		String res = "";

		for (LaunWidget launWidget : widgetList) {
			map.put("eid", "widgetClick-" + launWidget.getCodeId());
			for (int i = 0; i < dateList.size(); i++) {
				for (LaunChannel channel : channelList) {
					for (String version : versionList) {
						Date time = dateList.get(i);
						String channelId = channel.getChannelId();
						map.put("time", TimeUtils.date2String(time, "yyyy-MM-dd"));
						map.put("channelId", channelId);
						map.put("version", version);
						params = HttpClientUtil.convertStringParamter(map);
						res = HttpClientUtil.sendHttpGet(httpUrl, params);

						List<LaunWidgetStatistics> list = new ArrayList<>();

						for (LaunWidgetStatistics launAdver : list) {
							launAdver.setWidgetTime(time);
							launAdver.setChannelId(channelId);
							launAdver.setVersion(version);
							launWidgetStatisticsMapper.insertSelective(launAdver);
						}
					}
				}
			}
		}

	}
}
