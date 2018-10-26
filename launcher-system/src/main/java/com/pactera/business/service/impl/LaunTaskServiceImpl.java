package com.pactera.business.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.business.dao.LaunAdverStatisticsMapper;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.business.service.LaunTaskService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunAdverStatistics;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.utlis.HttpClientUtil;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.MsgResponseVO;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 定时任务业务类
 * 
 * @author:LL
 * @since:2018年8月22日 上午9:39:06
 */
@Service
@Slf4j
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

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Value("${system.conf.statisticsUrl}")
	private String statisticsUrl;

	@Value("${system.conf.adverUrl}")
	private String adverUrl;

	@Override
	public void themeTaskStatistics(List<LaunChannel> channelList, List<String> timeList) {

		List<String> paramList = null;
		// 接口域
		String spaceName = "theme";
		String httpUrl = "";
		String params = "";
		String res = "";

		Example example = new Example(LaunThemeStatistics.class);
		String dateReckon = TimeUtils.date2String(TimeUtils.dateReckon(new Date(), -7), "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("numStartTime", dateReckon);
		// 删除前一周的数据
		launThemeStatisticsMapper.deleteByExample(example);

		for (int i = 0; i < timeList.size(); i++) {
			for (LaunChannel channel : channelList) {
				String time = timeList.get(i);
				String channelId = channel.getChannelId();
				paramList = new ArrayList<>();
				paramList.add(spaceName);
				paramList.add("themeClick");
				paramList.add(time);
				paramList.add(channelId.toString());
				try {
					params = HttpClientUtil.convertRestfulParamter(paramList);
					httpUrl = statisticsUrl + params;
					res = HttpClientUtil.sendHttpGet(httpUrl, null);

					MsgResponseVO<LaunThemeStatistics> readValue = mapper.readValue(res,
							new TypeReference<MsgResponseVO<LaunThemeStatistics>>() {
							});

					if (readValue != null && "1".equals(readValue.getCode())) {
						List<LaunThemeStatistics> list = readValue.getData();

						for (LaunThemeStatistics launThemeStatistics : list) {
							launThemeStatistics.setChannelId(channelId);
							launThemeStatistics.setId(IdUtlis.Id());
							launThemeStatistics.setNumStartTime(time);
							launThemeStatisticsMapper.insertSelective(launThemeStatistics);
						}
					}
				} catch (IOException e) {
					log.error("【主题统计接口异常】----{}", e);
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void carTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> timeList) {

		Example example = new Example(LaunCarStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("carTime", dateReckon);
		// 删除前一周的数据
		launCarStatisticsMapper.deleteByExample(example);

		// 接口地址
		String httpUrl = "";
		// 接口域
		String params = "";
		String res = "";

		List<String> paramList = null;

		for (int i = 0; i < timeList.size(); i++) {
			for (LaunChannel channel : channelList) {
				for (String version : versionList) {
					Date time = timeList.get(i);
					String channelId = channel.getChannelId();
					paramList = new ArrayList<>();
					paramList.add("car");
					paramList.add("20181001");// TimeUtils.date2String(time,
												// "yyyyMMdd")
					paramList.add("version3");
					paramList.add("chanel_id0");
					try {
						params = HttpClientUtil.convertRestfulParamter(paramList);
						httpUrl = statisticsUrl + params;
						res = HttpClientUtil.sendHttpGet(httpUrl, null);

						LaunCarStatistics launCarStatistics = null;
						Map<String, Object> jsonToMap = (Map<String, Object>) JsonUtils.JsonToMap(res);
						if (jsonToMap != null) {
							if (jsonToMap.get("data") != null) {
								Map<String, Object> object = (Map<String, Object>) jsonToMap.get("data");
								launCarStatistics = new LaunCarStatistics();
								launCarStatistics.setVersion(version);
								launCarStatistics.setChannelId(channelId);
								launCarStatistics.setCarTime(time);
								launCarStatistics.setId(IdUtlis.Id());
								launCarStatistics.setCarNum(Long.parseLong(object.get("catNum").toString()));
								launCarStatistics.setCarActive(Long.parseLong(object.get("carActive").toString()));
								launCarStatistics.setCarStart(Long.parseLong(object.get("carStart").toString()));
								launCarStatistics.setAddUpNum(Long.parseLong(object.get("addUpNum").toString()));
								launCarStatisticsMapper.insertSelective(launCarStatistics);
							}
						}
					} catch (Exception e) {
						log.error("【车辆统计接口异常】----{}", e);
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

		// 接口地址
		String httpUrl = "";
		// 接口域
		String params = "";
		String res = "";
		List<String> paramList = null;

		for (int i = 0; i < dateList.size(); i++) {
			for (LaunChannel channel : channelList) {
				// for (String version : versionList) {
				try {
					Date time = dateList.get(i);
					String channelId = channel.getChannelId();
					paramList = new ArrayList<>();
					paramList.add("app");
					paramList.add("appClick");
					paramList.add(TimeUtils.date2String(time, "yyyy-MM-dd"));
					paramList.add(channelId);
					params = HttpClientUtil.convertRestfulParamter(paramList);
					httpUrl = statisticsUrl + params;
					res = HttpClientUtil.sendHttpGet(httpUrl, null);

					MsgResponseVO<LaunApplicationStatistics> readValue = mapper.readValue(res,
							new TypeReference<MsgResponseVO<LaunApplicationStatistics>>() {
							});
					for (LaunApplicationStatistics launApp : readValue.getData()) {
						// launApp.setVersion(version);
						launApp.setId(IdUtlis.Id());
						launApp.setChannelId(channelId);
						launApp.setApplicationTime(time);
						launApplicationStatisticsMapper.insertSelective(launApp);
					}
				} catch (JsonParseException e) {
					log.error("【应用统计接口异常】----{}", e);
				} catch (JsonMappingException e) {
					log.error("【应用统计接口异常】----{}", e);
				} catch (IOException e) {
					log.error("【应用统计接口异常】----{}", e);
				} catch (Exception e) {
					log.error("【应用统计接口异常】----{}", e);
				}
				// }
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
		String params = "";
		String res = "";

		for (Date date : dateList) {
			map.put("time", TimeUtils.date2String(date, "yyyy-MM-dd"));
			params = HttpClientUtil.convertStringParamter(map);
			res = HttpClientUtil.sendHttpGet(adverUrl, params);

			try {
				MsgResponseVO<LaunAdverStatistics> readValue = mapper.readValue(res,
						new TypeReference<MsgResponseVO<LaunAdverStatistics>>() {
						});

				for (LaunAdverStatistics launAdver : readValue.getData()) {
					launAdver.setId(IdUtlis.Id());
					launAdver.setAdverHour(date);
					launAdverStatisticsMapper.insertSelective(launAdver);
				}
			} catch (JsonParseException e) {
				log.error("【广告统计接口异常】----{}", e);
			} catch (JsonMappingException e) {
				log.error("【广告统计接口异常】----{}", e);
			} catch (IOException e) {
				log.error("【广告统计接口异常】----{}", e);
			} catch (Exception e) {
				log.error("【广告统计接口异常】----{}", e);
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void widgetTaskStatistics(List<LaunChannel> channelList, List<String> versionList, List<Date> dateList) {
		Example example = new Example(LaunWidgetStatistics.class);
		Date dateReckon = TimeUtils.dateReckon(TimeUtils.date2String(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd");

		example.createCriteria().andGreaterThanOrEqualTo("widgetTime", dateReckon);
		// 删除前一周的数据
		launWidgetStatisticsMapper.deleteByExample(example);

		// 获取基础widget数据
		Example example2 = new Example(LaunWidget.class);
		example2.createCriteria().andEqualTo("type", 0);
		List<LaunWidget> widgetList = launWidgetManagerMapper.selectByExample(example2);

		// 接口地址
		String httpUrl = "";
		// 接口域
		String spaceName = "widget";
		String params = "";
		String res = "";

		LaunWidgetStatistics launWidgetStatistics = null;
		List<String> paramList = null;
		for (LaunWidget launWidget : widgetList) {
			for (int i = 0; i < dateList.size(); i++) {
				for (LaunChannel channel : channelList) {
					for (String version : versionList) {

						paramList = new ArrayList<>();
						Date time = dateList.get(i);
						String channelId = channel.getChannelId();
						paramList.add(spaceName);
						paramList.add("widgetClick");
						paramList.add(launWidget.getCodeId());
						paramList.add(TimeUtils.date2String(time, "yyyy-MM-dd"));
						paramList.add(version);
						paramList.add(channelId);
						try {
							params = HttpClientUtil.convertRestfulParamter(paramList);
							httpUrl = statisticsUrl + params;
							res = HttpClientUtil.sendHttpGet(httpUrl, null);
							Map<String, Object> jsonToMap = (Map<String, Object>) JsonUtils.JsonToMap(res);
							Map<String, Object> object = (Map<String, Object>) jsonToMap.get("data");
							launWidgetStatistics = new LaunWidgetStatistics();
							launWidgetStatistics.setId(IdUtlis.Id());
							launWidgetStatistics.setWidgetTime(time);
							launWidgetStatistics.setChannelId(channelId);
							launWidgetStatistics.setVersion(version);
							launWidgetStatistics.setApplicationId(launWidget.getCodeId());
							launWidgetStatistics.setStartUpNum(Long.parseLong(object.get("startUpNum").toString()));
							launWidgetStatistics.setCarNum(Long.parseLong(object.get("carNum").toString()));
							launWidgetStatisticsMapper.insertSelective(launWidgetStatistics);
						} catch (Exception e) {
							log.error("【widget统计接口异常】-url{}---{}", e, httpUrl);
						}

					}
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void todayTaskStatistics(List<LaunChannel> channelList) {
		Map<String, Long> allMap = new HashMap<String, Long>();
		allMap.put("catNum", 0L);// 每日新增车辆
		allMap.put("carActive", 0L);// 活跃吃凉
		allMap.put("carStart", 0L);// 启动次数
		allMap.put("addUpNum", 0L);// 累计车辆

		// 接口地址
		String httpUrl = "";
		// 接口域
		String spaceName = "car";
		String params = "";
		String res = "";
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> paramList = null;
		String channelId = "";
		for (LaunChannel launChannel : channelList) {
			paramList = new ArrayList<>();
			channelId = launChannel.getChannelId();
			paramList.add(spaceName);
			paramList.add("today");
			paramList.add(channelId);
			try {
				params = HttpClientUtil.convertRestfulParamter(paramList);
				httpUrl = statisticsUrl + params;
				res = HttpClientUtil.sendHttpGet(httpUrl, null);
				Map<String, Object> jsonToMap = (Map<String, Object>) JsonUtils.JsonToMap(res);
				Map<String, Object> object = (Map<String, Object>) jsonToMap.get("data");
				list.add(object);
				valueOperations.set(ConstantUtlis.TODAY_STATISTICS + channelId, object);
			} catch (Exception e) {
				log.error("今日概况获取异常----【渠道】{}---------{}", channelId, httpUrl);
			}
		}

		for (Map<String, Object> map : list) {
			Long addUpNum = Long.parseLong(map.get("addUpNum").toString());
			Long carNum = Long.parseLong(map.get("catNum").toString());
			Long carActive = Long.parseLong(map.get("carActive").toString());
			Long carStart = Long.parseLong(map.get("carStart").toString());
			if (addUpNum != null) {
				allMap.put("addUpNum", allMap.get("addUpNum") + addUpNum);
			}
			if (carNum != null) {
				allMap.put("catNum", allMap.get("catNum") + carNum);
			}
			if (carActive != null) {
				allMap.put("carActive", allMap.get("carActive") + carActive);
			}
			if (carStart != null) {
				allMap.put("carStart", allMap.get("carStart") + carStart);
			}
		}
		valueOperations.set(ConstantUtlis.TODAY_STATISTICS, allMap);
	}
}
