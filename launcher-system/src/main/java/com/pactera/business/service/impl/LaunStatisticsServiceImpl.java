package com.pactera.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunAdverStatisticsMapper;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunCustomStatisticsMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunAdverStatistics;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunCustomStatistics;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.TimeUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional
public class LaunStatisticsServiceImpl implements LaunStatisticsService {

	@Autowired
	private LaunChannelMapper launChannelMapper;

	@Autowired
	private LaunWidgetStatisticsMapper launWidgetStatisticsMapper;

	@Autowired
	private LaunCarStatisticsMapper carStatisticsMapper;

	@Autowired
	private LaunApplicationStatisticsMapper launApplicationStatisticsMapper;

	@Autowired
	private LaunCustomStatisticsMapper customStatisticsMapper;

	@Autowired
	private LaunThemeStatisticsMapper launThemeStatisticsMapper;

	@Autowired
	private LaunAdverStatisticsMapper adverStatisticsMapper;

	/**
	 * 查询渠道列表
	 */
	@Override
	public PageInfo<LaunChannel> findChannelList() {
		List<LaunChannel> channelListList = launChannelMapper.findChannelList();
		return new PageInfo<>(channelListList);
	}

	/**
	 * widget使用情况-图表展示
	 */
	@Override
	public Map<String, Object> findWisdgetStatic(Long startTime, Long endTime, String channelId, String version) {
		Map<String, Object> map = null;
		map = new HashMap<>();
		ArrayList<Long> carList = new ArrayList<>();
		ArrayList<Long> clickList = new ArrayList<>();
		ArrayList<String> timeList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sTime = sdf.format(new Date(startTime)) + " " + "00:00:00";
		String eTime = sdf.format(new Date(endTime)) + " " + "23:59:59";
		List<LaunWidgetStatistics> widgetList = launWidgetStatisticsMapper.findWisdgetStatic(sTime, eTime, channelId,
				version);
		for (LaunWidgetStatistics launWidgetStatistics : widgetList) {
			carList.add(launWidgetStatistics.getCarNum());
			clickList.add(launWidgetStatistics.getStartUpNum());
			Date widgetTime = launWidgetStatistics.getWidgetTime();
			String stringTime = sdf.format(widgetTime);
			timeList.add(stringTime);
		}

		// map.put("widget", pageInfo);
		map.put("carList", carList);
		map.put("clickList", clickList);
		map.put("timeList", timeList);
		return map;
	}

	/**
	 * widget使用情况之概览列表
	 */
	@Override
	public PageInfo<LaunWidgetStatistics> findOverViewStatic(Integer pageNum, Integer pageSize, Long startTime,
			Long endTime, String channelId, String version) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sTime = sdf.format(new Date(startTime)) + " " + "00:00:00";
		String eTime = sdf.format(new Date(endTime)) + " " + "23:59:59";
		PageHelper.startPage(pageNum, pageSize);
		List<LaunWidgetStatistics> widgetOverViewList = launWidgetStatisticsMapper.findOverViewStatic(sTime, eTime,
				channelId, version);
		return new PageInfo<>(widgetOverViewList);
	}

	/**
	 * 应用分布-图表，概览列表
	 */
	@Override
	public Map<String, Object> findApplicationStatic(Long startTime, Long endTime, String channelId, String version) {
		ArrayList<String> nameList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sTime = sdf.format(new Date(startTime)) + " " + "00:00:00";
		String eTime = sdf.format(new Date(endTime)) + " " + "23:59:59";
		List<LaunApplicationStatistics> applicationOverViewList = launWidgetStatisticsMapper
				.findApplicationStatic(sTime, eTime, channelId, version);
		for (LaunApplicationStatistics launApplicationStatistics : applicationOverViewList) {
			nameList.add(launApplicationStatistics.getApplicationName());
		}
		map.put("nameList", nameList);
		map.put("overViewList", applicationOverViewList);
		return map;
	}

	/**
	 * 应用分布-详情列表
	 */
	@Override
	public PageInfo<LaunApplicationStatistics> findAppDetailStatic(Integer pageNum, Integer pageSize, Long startTime,
			Long endTime, String channelId, String version) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sTime = sdf.format(new Date(startTime)) + " " + "00:00:00";
		String eTime = sdf.format(new Date(endTime)) + " " + "23:59:59";
		PageHelper.startPage(pageNum, pageSize);
		List<LaunApplicationStatistics> appList = launApplicationStatisticsMapper.findAppDetailStatic(sTime, eTime,
				channelId, version);
		return new PageInfo<>(appList);
	}

	/**
	 * 查询具体概览列表
	 */
	// @Override
	// public PageInfo<LaunWidgetStatistics> findWisdgetDetailStatic(Integer
	// pageNum, Integer pageSize, Long startTime,
	// Long endTime, String channelId) {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// String sTime = sdf.format(new Date(startTime)) + " " + "00:00:00";
	// String eTime = sdf.format(new Date(endTime)) + " " + "23:59:59";
	// PageHelper.startPage(pageNum, pageSize);
	// List<LaunWidgetStatistics> widgetList =
	// launWidgetStatisticsMapper.findWisdgetDetailStatic(sTime, eTime,
	// channelId);
	// return new PageInfo<>(widgetList);
	// }

	@Override
	public Map<String, Object> selectCarStatistics(Long startTime, Long endTime, Long channel, String version,
			Long type) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 类型(0:新增车辆1:活跃车辆2:启动次数3:平均单次使用时长)
		Example example = new Example(LaunCarStatistics.class);
		Criteria or = example.or();
		// 时间转换
		if (null != startTime) {
			Date stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");
			or.andGreaterThanOrEqualTo("carTime", stime);
		}
		if (null != endTime) {
			Date etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
			or.andLessThanOrEqualTo("carTime", etime);
		}
		if (null != channel) {
			or.andEqualTo("channelId", channel);
		}
		if (null != version) {
			or.andEqualTo("version", version);
		}
		example.setOrderByClause("car_time asc");
		List<LaunCarStatistics> list = carStatisticsMapper.selectByExample(example);
		// 创建新增车辆的集合
		List<Object> carn = new ArrayList<>();
		List<Object> cart = new ArrayList<>();
		// 封装参数
		for (LaunCarStatistics launCarStatistics : list) {
			cart.add(TimeUtils.date2String(launCarStatistics.getCarTime(), "yyyy-MM-dd"));
			if (type != null) {
				// type判断的是表格的数量
				if (1 == type) {
					// carNum
					carn.add(launCarStatistics.getCarNum());
				} else if (2 == type) {
					// 活跃车辆
					carn.add(launCarStatistics.getCarActive());
				} else if (3 == type) {
					// 启动次数
					carn.add(launCarStatistics.getCarStart());
				} else if (4 == type) {
					// 平均时长
					carn.add(launCarStatistics.getCarAvgTime());
				}
			}
		}
		map.put("x", cart);
		map.put("y", carn);
		return map;
	}

	@Override
	public PageInfo<LaunCarStatistics> selectCarListStatistics(Long startTime, Long endTime, Long channel, Long version,
			Long type, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		// 类型(0:新增车辆1:活跃车辆2:启动次数3:平均单次使用时长)
		Example example = new Example(LaunCarStatistics.class);
		Criteria or = example.or();
		// 时间转换
		if (null != startTime) {
			Date stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");
			or.andGreaterThanOrEqualTo("carTime", stime);
		}
		if (null != endTime) {
			Date etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
			or.andLessThanOrEqualTo("carTime", etime);
		}
		if (null != channel) {
			or.andEqualTo("channelId", channel);
		}
		if (null != version) {
			or.andEqualTo("version", version);
		}
		example.setOrderByClause("car_time desc");
		List<LaunCarStatistics> list = carStatisticsMapper.selectByExample(example);
		// 封装参数
		for (LaunCarStatistics launCarStatistics : list) {
			// ("启动次数占比") String carStartProp;

			BigDecimal carStatistics = new BigDecimal(Double.toString(launCarStatistics.getCarStart()));
			BigDecimal carStart = new BigDecimal(Double.toString(launCarStatistics.getCarStart()));
			String carStartProp = carStatistics.divide(carStart, 2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100)).toString();
			launCarStatistics.setCarStartProp(carStartProp + "%");

			// "活跃车辆占比") String carActiveProp;
			BigDecimal carActive = new BigDecimal(Double.toString(launCarStatistics.getCarActive()));
			BigDecimal addUpNum = new BigDecimal(Double.toString(launCarStatistics.getAddUpNum()));
			String carActiveProp = carActive.divide(addUpNum, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
					.toString();
			launCarStatistics.setCarActiveProp(carActiveProp + "%");

			// ("新增车辆占比") String carProp;
			BigDecimal carNum = new BigDecimal(Double.toString(launCarStatistics.getCarNum()));
			BigDecimal addUpNum1 = new BigDecimal(Double.toString(launCarStatistics.getAddUpNum()));
			String carProp = carNum.divide(addUpNum1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
					.toString();
			launCarStatistics.setCarProp(carProp + "%");
		}
		return new PageInfo<>(list);

	}

	@Override
	public List<Map<String, Object>> selectThemeStatistics(String channelId, Integer type) {

		List<Map<String, Object>> returnList = new LinkedList<Map<String, Object>>();

		String starTime = "";
		String endTime = "";
		// 昨天日期
		Date dateReckon = TimeUtils.dateReckon(new Date(), -1);
		endTime = TimeUtils.date2String(dateReckon, "yyyy-MM-dd");
		// 定义时间期间
		if (type == 0) {
			// 昨天数据
			starTime = endTime;
		} else if (type == 1) {
			// 近一周数据
			Date oneWeekBefore = TimeUtils.dateReckon(dateReckon, -7);
			starTime = TimeUtils.date2String(oneWeekBefore, "yyyy-MM-dd");
		} else {
			// 近一个月数据
			Date oneMonthBefore = TimeUtils.dateReckon(dateReckon, -3);
			starTime = TimeUtils.date2String(oneMonthBefore, "yyyy-MM-dd");
		}

		List<LaunThemeStatistics> list = launThemeStatisticsMapper.selectThemeStatistics(channelId, starTime, endTime);

		Map<String, Object> map = null;
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			map = new HashMap<String, Object>();
			LaunThemeStatistics themeStatistics = list.get(i);
			if (i <= 4) {
				map.put("name", themeStatistics.getTitle());
				map.put("tiem", endTime);
				map.put("value", themeStatistics.getCount());
				returnList.add(map);

			} else {
				count += themeStatistics.getCount();
			}
		}

		if (list.size() > 5) {
			map = new HashMap<String, Object>();
			map.put("name", "其他");
			map.put("tiem", endTime);
			map.put("value", count);
			returnList.add(map);
		}
		return returnList;

	}

	/**
	 * 获取所有的版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月6日 下午3:14:29
	 * @param
	 */
	@Override
	public List<String> getVersion() {
		List<String> version = carStatisticsMapper.selectVersion();
		return version;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> versionStatistics(Long startTime, Long endTime, Long type, Long channel,
			String versions) {
		// 类型(1:新增车辆2:活跃车辆3:启动次数4:升级车辆5:累计车辆)
		Date stime = null;
		Date etime = null;
		// 时间转换
		if (null != startTime) {
			stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");

		}
		if (null != endTime) {
			etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
		}
		List<String> asList = new ArrayList<>();
		if (null != versions) {
			String[] split = versions.split(",");
			asList = Arrays.asList(split);
		}

		List<LaunCarStatistics> list = carStatisticsMapper.versionStatistics(stime, etime, asList, channel);
		Map<String, Object> map = new HashMap<>();
		// 时间
		map.put("data", (list.stream().map(LaunCarStatistics::getCarTime).collect(Collectors.toList())).stream()
				.distinct().collect(Collectors.toList()));

		// 判断参数，返回封装结果
		if (null != type && ConstantUtlis.STATISTICS_ONE.equals(type)) {
			// 新增车辆
			// 版本 arrayList Object = map< k ,v >
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			ArrayList<Object> arrayList1 = new ArrayList<>();// 这个是版本对应的集合数据
			for (LaunCarStatistics launCarStatistics : list) {
				Map<String, Object> map1 = new HashMap<>();
				if (arrayList.size() == 0) {
					map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarNum()));
				} else {
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						// 包含
						if (entry.getKey().contains(launCarStatistics.getVersion())) {
							ArrayList<Object> object = (ArrayList<Object>) map1.get(entry.getKey());
							object.add(launCarStatistics.getCarNum());
						} else {
							map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarNum()));
						}
					}
				}
				arrayList.add(map1);
			}

			map.put("attr", arrayList);

		}

		// 活跃车辆
		if (null != type && ConstantUtlis.STATISTICS_TWO.equals(type)) {
			// 版本 arrayList Object = map< k ,v >
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			ArrayList<Object> arrayList1 = new ArrayList<>();// 这个是版本对应的集合数据
			for (LaunCarStatistics launCarStatistics : list) {
				Map<String, Object> map1 = new HashMap<>();
				if (arrayList.size() == 0) {
					map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarActive()));
				} else {
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						// 包含
						if (entry.getKey().contains(launCarStatistics.getVersion())) {
							ArrayList<Object> object = (ArrayList<Object>) map1.get(entry.getKey());
							object.add(launCarStatistics.getCarNum());
						} else {
							map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarActive()));
						}
					}
				}
				arrayList.add(map1);
			}

			map.put("attr", arrayList);

		}

		// 启动次数
		if (null != type && ConstantUtlis.STATISTICS_THREE.equals(type)) {
			// 版本 arrayList Object = map< k ,v >
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			ArrayList<Object> arrayList1 = new ArrayList<>();// 这个是版本对应的集合数据
			for (LaunCarStatistics launCarStatistics : list) {
				Map<String, Object> map1 = new HashMap<>();
				if (arrayList.size() == 0) {
					map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarStart()));
				} else {
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						// 包含
						if (entry.getKey().contains(launCarStatistics.getVersion())) {
							ArrayList<Object> object = (ArrayList<Object>) map1.get(entry.getKey());
							object.add(launCarStatistics.getCarNum());
						} else {
							map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getCarStart()));
						}
					}
				}
				arrayList.add(map1);
			}

			map.put("attr", arrayList);

		}
		// 升级车辆
		if (null != type && ConstantUtlis.STATISTICS_FOUR.equals(type)) {
			// 版本 arrayList Object = map< k ,v >
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			ArrayList<Object> arrayList1 = new ArrayList<>();// 这个是版本对应的集合数据
			for (LaunCarStatistics launCarStatistics : list) {
				Map<String, Object> map1 = new HashMap<>();
				if (arrayList.size() == 0) {
					map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getUpGradeNum()));
				} else {
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						// 包含
						if (entry.getKey().contains(launCarStatistics.getVersion())) {
							ArrayList<Object> object = (ArrayList<Object>) map1.get(entry.getKey());
							object.add(launCarStatistics.getCarNum());
						} else {
							map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getUpGradeNum()));
						}
					}
				}
				arrayList.add(map1);
			}

			map.put("attr", arrayList);

		}

		// 累计车辆
		if (null != type && ConstantUtlis.STATISTICS_FIVE.equals(type)) {
			// 版本 arrayList Object = map< k ,v >
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			ArrayList<Object> arrayList1 = new ArrayList<>();// 这个是版本对应的集合数据
			for (LaunCarStatistics launCarStatistics : list) {
				Map<String, Object> map1 = new HashMap<>();
				if (arrayList.size() == 0) {
					map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getAddUpNum()));
				} else {
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						// 包含
						if (entry.getKey().contains(launCarStatistics.getVersion())) {
							ArrayList<Object> object = (ArrayList<Object>) map1.get(entry.getKey());
							object.add(launCarStatistics.getCarNum());
						} else {
							map1.put(launCarStatistics.getVersion(), arrayList1.add(launCarStatistics.getAddUpNum()));
						}
					}
				}
				arrayList.add(map1);
			}

			map.put("attr", arrayList);

		}

		return map;

	}

	/**
	 * 版本详情统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午3:51:45
	 * @param
	 */
	@Override
	public PageInfo<LaunCarStatistics> versionXiang(Long startTime, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(LaunCarStatistics.class);
		Date stime = null;
		if (null != startTime) {
			stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");
			example.createCriteria().andGreaterThanOrEqualTo("carTime", stime);
		}
		List<LaunCarStatistics> list = carStatisticsMapper.selectByExample(example);
		return new PageInfo<>(list);
	}

	/**
	 * 版本趋势统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午4:29:01
	 * @param
	 */
	@Override
	public List<LaunCarStatistics> versionTrend(Long startTime, Long endTime, String versions, Long channel) {
		Date stime = null;
		Date etime = null;
		// 时间转换
		if (null != startTime) {
			stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");

		}
		if (null != endTime) {
			etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
		}
		List<String> asList = new ArrayList<>();
		if (null != versions) {
			String[] split = versions.split(",");
			asList = Arrays.asList(split);
		}
		String string = TimeUtils.getNextDay(new Date());
		Date sdate = TimeUtils.string2Date(string + " 00:00:00");
		Date edate = TimeUtils.string2Date(string + " 23:59:59");
		List<LaunCarStatistics> list = carStatisticsMapper.selectVersionTrend(stime, etime, asList, sdate, edate,
				channel);
		return list;
	}

	/**
	 * 自定义事件 的 统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月10日 上午9:53:21
	 * @param
	 */
	@Override
	public PageInfo<LaunCustomStatistics> customStatic(Long startTime, Long endTime, Long channel, String version,
			String custom, int pageSize, int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(LaunCustomStatistics.class);
		Criteria createCriteria = example.createCriteria();
		if (null != startTime) {
			Date stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");
			createCriteria.andGreaterThanOrEqualTo("time", stime);
		}
		if (null != endTime) {
			Date etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
			createCriteria.andLessThanOrEqualTo("time", etime);
		}
		if (null != channel) {
			createCriteria.andEqualTo("channelId", channel);
		}
		if (null != version) {
			createCriteria.andEqualTo("version", version);
		}
		if (null != custom) {
			createCriteria.andEqualTo("customName", custom);
		}
		List<LaunCustomStatistics> list = customStatisticsMapper.selectByExample(example);
		return new PageInfo<>(list);
	}

	/**
	 * 根据事件的参数去查询详情
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月10日 下午2:33:57
	 * @param
	 */
	@Override
	public List<LaunCustomStatistics> customStaticById(String paramName) {
		Example example = new Example(LaunCustomStatistics.class);
		if (null != paramName) {
			example.createCriteria().andEqualTo("customParamName", paramName);
		}
		List<LaunCustomStatistics> list = customStatisticsMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据参数去查看详情
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月11日 上午10:38:14
	 * @param
	 */
	@Override
	public Map<String, Object> customXiangqing(Long startTime, Long endTime, Long channel, String version,
			String custom, Long type) {
		Map<String, Object> map = new HashMap<>();
		Example example = new Example(LaunCustomStatistics.class);
		Criteria createCriteria = example.createCriteria();
		if (null != startTime) {
			Date stime = TimeUtils.string2Date(TimeUtils.millis2String(startTime, "yyyy-MM-dd") + " 00:00:00");
			createCriteria.andGreaterThanOrEqualTo("time", stime);
		}
		if (null != endTime) {
			Date etime = TimeUtils.string2Date(TimeUtils.millis2String(endTime, "yyyy-MM-dd") + " 23:59:59");
			createCriteria.andLessThanOrEqualTo("time", etime);
		}
		if (null != channel) {
			createCriteria.andEqualTo("channelId", channel);
		}
		if (null != version) {
			createCriteria.andEqualTo("version", version);
		}
		if (null != custom) {
			createCriteria.andEqualTo("customName", custom);
		}
		List<LaunCustomStatistics> list = customStatisticsMapper.selectByExample(example);
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		if (list.size() > 0) {

			for (LaunCustomStatistics launCustomStatistics : list) {
				x.add(TimeUtils.date2String(launCustomStatistics.getTime(), "yyyy-MM-dd"));
				if (null != type && ConstantUtlis.STATISTICS_ONE.equals(type)) {
					y.add(launCustomStatistics.getCustomNum());
				}
				if (null != type && ConstantUtlis.STATISTICS_TWO.equals(type)) {
					y.add(launCustomStatistics.getCustomTime());
				}
			}
		}
		map.put("x", x);
		map.put("y", y);
		return map;
	}

	/**
	 * 今日概况
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月13日 下午4:11:08
	 * @param
	 */
	@Override
	public LaunCarStatistics yesCar(String channelId) {
		String nextDay = TimeUtils.date2String(new Date(), "yyyy-MM-dd");
		String stime = nextDay + " 00:00:00";
		String etime = nextDay + " 23:59:59";
		LaunCarStatistics carStatistics = null;
		if (null != channelId && !"".equals(channelId)) {
			carStatistics = carStatisticsMapper.selectYesCar(channelId, stime, etime);
		} else {
			carStatistics = carStatisticsMapper.selectYesCarByChannelId(stime, etime);
		}
		return carStatistics;
	}

	/**
	 * 近30天趋势
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月13日 下午4:11:17
	 * @param
	 */
	@Override
	public Map<String, Object> trendCar(String channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		if (null != type && (type == 1 || type == 2 || type == 3 || type == 4)) {
			map = returnCarStatis(channelId, type);
		}
		if (null != type && (type == 5 || type == 6)) {
			map = returnCarTheme(channelId, type);
		}

		if (null != type && (type == 7 || type == 8)) {
			map = returnAdver(channelId, type);
		}

		return map;
	}

	private Map<String, Object> returnAdver(String channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		String nextSanDay = TimeUtils.getNextSanDay(new Date());
		String stime = nextSanDay + " 00:00:00";
		String etime = TimeUtils.date2String(new Date(), "yyyy-MM-dd") + " 23:59:59";
		List<LaunAdverStatistics> list = new ArrayList<>();
		if (null != channelId && !"".equals(channelId)) {
			list = adverStatisticsMapper.selectByType(channelId, stime, etime);
		} else {
			list = adverStatisticsMapper.selectByTypeChannelId(stime, etime);
		}
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		if (list.size() > 0) {
			for (LaunAdverStatistics launAdverStatistics : list) {
				x.add(TimeUtils.date2String(launAdverStatistics.getAdverHour(), "yyyy-MM-dd"));
				// 7:广告点击次数
				if (null != type && type == 7) {
					y.add(launAdverStatistics.getAdverClick());
				}
				// 8:广告展示次数
				if (null != type && type == 8) {
					y.add(launAdverStatistics.getAdverDisplayNum());
				}

			}
		}
		map.put("x", x);
		map.put("y", y);
		return map;

	}

	public Map<String, Object> returnCarStatis(String channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		String nextSanDay = TimeUtils.getNextSanDay(new Date());
		String stime = nextSanDay + " 00:00:00";
		String etime = TimeUtils.date2String(new Date(), "yyyy-MM-dd") + " 23:59:59";
		List<LaunCarStatistics> list = new ArrayList<>();
		if (null != channelId && !"".equals(channelId)) {
			list = carStatisticsMapper.selectByType(channelId, type, stime, etime);
		} else {
			list = carStatisticsMapper.selectByChannelId(type, stime, etime);
		}
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		if (list.size() > 0) {

			for (LaunCarStatistics launCarStatistics : list) {
				x.add(TimeUtils.date2String(launCarStatistics.getCarTime(), "yyyy-MM-dd"));
				// 1:新增车辆
				if (null != type && type == 1) {
					y.add(launCarStatistics.getCarNum());
				}
				// 2:活跃车辆
				if (null != type && type == 2) {
					y.add(launCarStatistics.getCarActive());
				}
				// 3:启动次数
				if (null != type && type == 3) {
					y.add(launCarStatistics.getCarStart());
				}
				// 4:平均单次时长
				if (null != type && type == 4) {
					y.add(launCarStatistics.getCarAvgTime());
				}
			}
		}
		map.put("x", x);
		map.put("y", y);
		return map;
	}

	public Map<String, Object> returnCarTheme(String channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		String nextSanDay = TimeUtils.getNextSanDay(new Date());
		String stime = nextSanDay + " 00:00:00";
		String etime = TimeUtils.date2String(new Date(), "yyyy-MM-dd") + " 23:59:59";
		List<LaunThemeStatistics> list = new ArrayList<>();
		if (null != channelId && !"".equals(channelId)) {
			list = launThemeStatisticsMapper.selectByType(channelId, type, stime, etime);
		} else {
			list = launThemeStatisticsMapper.selectByChannelIdNull(stime, etime);
		}
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		if (list.size() > 0) {

			for (LaunThemeStatistics launThemeStatistics : list) {
				x.add(launThemeStatistics.getNumStartTime());
				// 5:主题使用次数
				if (null != type && type == 5) {
					y.add(launThemeStatistics.getCount());
				}
				// 6:有效主题
				if (null != type && type == 6) {
					y.add(launThemeStatistics.getEffeTheme());

				}

			}
		}
		map.put("x", x);
		map.put("y", y);
		return map;
	}

	/**
	 * top版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 上午11:45:46
	 * @param
	 */
	@Override
	public List<Object> topVersion(String channelId, Long type) {
		String day = TimeUtils.getNextDay(new Date());
		Date sdate = TimeUtils.string2Date(day + " 00:00:00");
		Date edate = TimeUtils.string2Date(day + " 23:59:59");
		List<LaunCarStatistics> list = carStatisticsMapper.selectTopVersion(channelId, sdate, edate);
		List<Object> y = new ArrayList<>();
		for (LaunCarStatistics launCarStatistics : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", launCarStatistics.getVersion());
			if (null != type && type == 1) {
				map.put("value", launCarStatistics.getCarNum());
			}
			if (null != type && type == 2) {
				map.put("value", launCarStatistics.getCarActive());

			}
			if (null != type && type == 3) {
				map.put("value", launCarStatistics.getCarStart());

			}
			if (null != type && type == 4) {
				map.put("value", launCarStatistics.getAddUpNum());
			}
			y.add(map);

		}

		return y;
	}

	@Override
	public Map<String, Object> topTheme(String channelId, Long type) {

		/**
		 * { y:[], yesterday:[ {value:123,parent:123} ], today:[ {
		 * value:123,parent:123 } ] }
		 */
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Date date = new Date();
		String timeString = TimeUtils.date2String(date, "yyyy-MM-dd");
		Date dateReckon = TimeUtils.dateReckon(date, -1);
		String yesTimeString = TimeUtils.date2String(dateReckon, "yyyy-MM-dd");

		// 查询今日主题排行
		List<LaunThemeStatistics> list = launThemeStatisticsMapper.selectTopTheme(timeString, channelId, 1);

		// 查询今日主题排行
		List<LaunThemeStatistics> yesList = launThemeStatisticsMapper.selectTopTheme(yesTimeString, channelId, 0);

		Map<Long, LaunThemeStatistics> yesMap = new HashMap<Long, LaunThemeStatistics>();
		// 变形key-value
		for (LaunThemeStatistics launThemeStatistics : yesList) {
			Long themeId = launThemeStatistics.getThemeId();
			if (yesMap.get(themeId) == null) {
				yesMap.put(themeId, launThemeStatistics);
			}
		}

		// returnBigDecimal();
		// y轴数据
		List<String> yList = new LinkedList<String>();

		// 总和
		Long count = 0L;

		Long yesCount = 0L;
		// 按照昨天数据的排序，将前日的数据排序统一
		yesList = new LinkedList<LaunThemeStatistics>();
		for (LaunThemeStatistics theme : list) {
			yList.add(theme.getTitle());

			if (type == 1) {
				count += theme.getCount();
			} else if (type == 2) {
				count += theme.getCountCar();
			}

			LaunThemeStatistics launThemeStatistics = yesMap.get(theme.getThemeId());
			if (launThemeStatistics != null) {
				yesList.add(launThemeStatistics);

				if (type == 1) {
					yesCount += launThemeStatistics.getCount();
				} else if (type == 2) {
					yesCount += launThemeStatistics.getCountCar();
				}
			} else {
				LaunThemeStatistics launTheme = new LaunThemeStatistics();
				launTheme.setCount(0L);
				launTheme.setCountCar(0L);
				launTheme.setTitle(theme.getTitle());
				launTheme.setThemeId(theme.getThemeId());
				yesList.add(launTheme);
			}
		}

		List<Map<String, Object>> packPageData = packPageData(count, list, type);
		List<Map<String, Object>> yesPackPageData = packPageData(yesCount, yesList, type);

		returnMap.put("y", yList.toArray());
		returnMap.put("today", packPageData);
		returnMap.put("yesterday", yesPackPageData);

		return returnMap;
	}

	/**
	 * 封装页面渲染需要格式
	 * 
	 * @author LL
	 * @date 2018年7月27日 下午5:55:44
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> packPageData(Long sumCount, List<LaunThemeStatistics> list, Long type) {

		List<Map<String, Object>> returnMap = new LinkedList<Map<String, Object>>();

		Map<String, Object> oneStatistics = null;
		Long count = null;
		for (LaunThemeStatistics launThemeStatistics : list) {
			oneStatistics = new HashMap<String, Object>();
			if (type == 1) {
				count = launThemeStatistics.getCount();
			} else if (type == 2) {
				count = launThemeStatistics.getCountCar();
			}
			String returnBigDecimal = returnBigDecimal(count, sumCount);
			oneStatistics.put("parent", returnBigDecimal);
			oneStatistics.put("value", count);
			returnMap.add(oneStatistics);
		}
		return returnMap;
	}

	/**
	 * 应用统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月27日 上午11:14:16
	 * @param
	 */
	@Override
	public Map<String, Object> topApplication(String channelId) {
		Map<String, Object> map = new HashMap<>();
		// qian
		String dayy = TimeUtils.getNextDay(TimeUtils.getNextDayDate(new Date()));
		Date sytime = TimeUtils.string2Date(dayy + " 00:00:00");
		Date eytime = TimeUtils.string2Date(dayy + " 23:59:59");
		// yes
		String day = TimeUtils.getNextDay(new Date());
		Date stime = TimeUtils.string2Date(day + " 00:00:00");
		Date etime = TimeUtils.string2Date(day + " 23:59:59");
		List<LaunApplicationStatistics> list = new ArrayList<>();
		List<LaunApplicationStatistics> listy = new ArrayList<>();
		if (null != channelId && !"".equals(channelId)) {
			list = launApplicationStatisticsMapper.topApplication(channelId, stime, etime);
			listy = launApplicationStatisticsMapper.topyApplication(channelId, sytime, eytime);
		} else {
			list = launApplicationStatisticsMapper.topApplicationByChannel(stime, etime);
			listy = launApplicationStatisticsMapper.topyApplicationByChannel(sytime, eytime);
		}
		LaunApplicationStatistics selectBySum = launApplicationStatisticsMapper.selectBySum(channelId, stime, etime);
		LaunApplicationStatistics selectBySumy = launApplicationStatisticsMapper.selectBySum(channelId, sytime, eytime);
		Long sum = 0L;
		Long sumy = 0L;
		if (selectBySum != null) {
			sum = selectBySum.getStartUpNum();
		}
		if (selectBySumy != null) {
			sumy = selectBySumy.getStartUpNum();
		}

		List<Object> y = new ArrayList<>();
		List<Object> ylist = new ArrayList<>();
		List<Object> xlist = new ArrayList<>();

		// 封装参数
		if (list.size() > 0) {

			for (LaunApplicationStatistics launApplicationStatistics : list) {
				Map<String, Object> ymap = new HashMap<>();
				Map<String, Object> xmap = new HashMap<>();
				ymap.put("value", launApplicationStatistics.getStartUpNum());
				y.add(launApplicationStatistics.getApplicationName());
				if (sum != 0) {
					String bigDecimal = returnBigDecimal(launApplicationStatistics.getStartUpNum(), sum);
					ymap.put("parent", bigDecimal);
				}
				if (listy.size() > 0) {
					for (LaunApplicationStatistics lass : listy) {
						if (launApplicationStatistics.getApplicationName().equals(lass.getApplicationName())) {
							xmap.put("value", lass.getStartUpNum());
							if (sumy != null && sumy != 0) {
								String returnBigDecimal = returnBigDecimal(lass.getStartUpNum(), sumy);
								xmap.put("parent", returnBigDecimal);
							}
						}
					}
					xlist.add(xmap);
				}
				ylist.add(ymap);
			}
		} else {
			if (listy.size() > 0) {
				for (LaunApplicationStatistics lass : listy) {
					y.add(lass.getApplicationName());
					Map<String, Object> xmap = new HashMap<>();
					xmap.put("value", lass.getStartUpNum());
					if (sumy != 0 && sumy != null) {
						String returnBigDecimal = returnBigDecimal(lass.getStartUpNum(), sumy);
						xmap.put("parent", returnBigDecimal);
					}
					xlist.add(xmap);
				}
			}
		}
		map.put("y", y);
		map.put("yesterday", ylist);
		map.put("qiantian", xlist);
		return map;
	}

	/**
	 * 渠道的top
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午3:26:24
	 * @param
	 */
	@Override
	public Map<String, Object> topChannel(Long type) {
		Map<String, Object> map = new HashMap<>();
		// qian
		String dayy = TimeUtils.getNextDay(TimeUtils.getNextDayDate(new Date()));
		Date sytime = TimeUtils.string2Date(dayy + " 00:00:00");
		Date eytime = TimeUtils.string2Date(dayy + " 23:59:59");
		// yes
		String day = TimeUtils.getNextDay(new Date());
		Date stime = TimeUtils.string2Date(day + " 00:00:00");
		Date etime = TimeUtils.string2Date(day + " 23:59:59");
		List<Object> qudao = new ArrayList<>();
		List<Object> zr = new ArrayList<>();
		List<Object> qr = new ArrayList<>();
		// 昨天
		List<LaunCarStatistics> car = carStatisticsMapper.selectByTopCar(stime, etime, type);
		LaunCarStatistics sum = carStatisticsMapper.selectSumCar(stime, etime);
		// 前天
		List<LaunCarStatistics> cary = carStatisticsMapper.selectByTopCar(sytime, eytime, type);
		LaunCarStatistics sumy = carStatisticsMapper.selectSumCar(sytime, eytime);
		if (car.size() > 0) {
			for (LaunCarStatistics launCarStatistics : car) {
				Map<String, Object> yesMap = new HashMap<>();
				Map<String, Object> qianMap = new HashMap<>();
				qudao.add(launCarStatistics.getCarAvgTime());
				// fengzhuangshuju
				if (null != type && type == 1) {
					// 昨日数据
					yesMap.put("value", launCarStatistics.getCarNum());
					// 昨日占比
					if (sum != null && sum.getCarNum() != 0) {

						String returnBigDecimal = returnBigDecimal(launCarStatistics.getCarNum(), sum.getCarNum());
						yesMap.put("parent", returnBigDecimal);
					}
					// 前日
					if (cary.size() > 0) {
						for (LaunCarStatistics ly : cary) {
							if (launCarStatistics.getCarAvgTime().equals(ly.getCarAvgTime())) {
								qianMap.put("value", ly.getCarNum());
								if (sumy != null && sumy.getCarNum() != 0) {
									String returnBigDecimal = returnBigDecimal(ly.getCarNum(), sumy.getCarNum());
									qianMap.put("parent", returnBigDecimal);
								}
							}
						}
						qr.add(qianMap);
					}
				}
				if (null != type && type == 2) {
					// 昨日数据
					yesMap.put("value", launCarStatistics.getCarActive());
					// 昨日占比
					if (sum != null && sum.getCarActive() != 0) {
						String returnBigDecimal = returnBigDecimal(launCarStatistics.getCarActive(),
								sum.getCarActive());
						yesMap.put("parent", returnBigDecimal);
					}
					// 前日
					if (cary.size() > 0) {
						for (LaunCarStatistics ly : cary) {
							if (launCarStatistics.getCarAvgTime().equals(ly.getCarAvgTime())) {
								qianMap.put("value", ly.getCarActive());
								if (sumy != null && sumy.getCarActive() != 0) {

									String returnBigDecimal = returnBigDecimal(ly.getCarActive(), sumy.getCarActive());
									qianMap.put("parent", returnBigDecimal);
								}
							}
						}
						qr.add(qianMap);
					}

				}
				if (null != type && type == 3) {

					// 昨日数据
					yesMap.put("value", launCarStatistics.getCarStart());
					// 昨日占比
					if (sum != null && sum.getCarStart() != 0) {
						String returnBigDecimal = returnBigDecimal(launCarStatistics.getCarStart(), sum.getCarStart());
						yesMap.put("parent", returnBigDecimal);
					}
					// 前日
					if (cary.size() > 0) {
						for (LaunCarStatistics ly : cary) {
							if (launCarStatistics.getCarAvgTime().equals(ly.getCarAvgTime())) {
								qianMap.put("value", ly.getCarStart());
								if (sumy != null && sumy.getCarStart() != 0) {
									String returnBigDecimal = returnBigDecimal(ly.getCarStart(), sumy.getCarStart());
									qianMap.put("parent", returnBigDecimal);
								}
							}
						}
						qr.add(qianMap);
					}

				}
				if (null != type && type == 4) {

					// 昨日数据
					yesMap.put("value", launCarStatistics.getAddUpNum());
					// 昨日占比
					if (sum != null && sum.getAddUpNum() != 0) {
						String returnBigDecimal = returnBigDecimal(launCarStatistics.getAddUpNum(), sum.getAddUpNum());
						yesMap.put("parent", returnBigDecimal);
					}
					// 前日
					if (cary.size() > 0) {
						for (LaunCarStatistics ly : cary) {
							if (launCarStatistics.getCarAvgTime().equals(ly.getCarAvgTime())) {
								qianMap.put("value", ly.getAddUpNum());
								if (sumy != null && sumy.getAddUpNum() != 0) {
									String returnBigDecimal = returnBigDecimal(ly.getAddUpNum(), sumy.getAddUpNum());
									qianMap.put("parent", returnBigDecimal);
								}
							}
						}
						qr.add(qianMap);
					}
				}

				zr.add(yesMap);

			}
		} else {
			if (cary.size() > 0) {
				for (LaunCarStatistics lay : cary) {
					qudao.add(lay.getCarAvgTime());
					Map<String, Object> qianMap = new HashMap<>();
					// ===========================================
					if (null != type && type == 1) {
						// 前日
						for (LaunCarStatistics ly : cary) {
							qianMap.put("value", ly.getCarNum());
							if (sumy != null && sumy.getCarNum() != 0) {
								String returnBigDecimal = returnBigDecimal(ly.getCarNum(), sumy.getCarNum());
								qianMap.put("parent", returnBigDecimal);
							}
						}
					}
					if (null != type && type == 2) {
						for (LaunCarStatistics ly : cary) {
							qianMap.put("value", ly.getCarActive());
							if (sumy != null && sumy.getCarActive() != 0) {
								String returnBigDecimal = returnBigDecimal(ly.getCarActive(), sumy.getCarActive());
								qianMap.put("parent", returnBigDecimal);
							}
						}

					}
					if (null != type && type == 3) {
						// 前日
						for (LaunCarStatistics ly : cary) {
							qianMap.put("value", ly.getCarStart());
							if (sumy != null && sumy.getCarStart() != 0) {
								String returnBigDecimal = returnBigDecimal(ly.getCarStart(), sumy.getCarStart());
								qianMap.put("parent", returnBigDecimal);
							}
						}

					}
					if (null != type && type == 4) {

						// 前日
						for (LaunCarStatistics ly : cary) {
							qianMap.put("value", ly.getAddUpNum());
							if (sumy != null && sumy.getAddUpNum() != 0) {
								String returnBigDecimal = returnBigDecimal(ly.getAddUpNum(), sumy.getAddUpNum());
								qianMap.put("parent", returnBigDecimal);
							}
						}

					}
					qr.add(qianMap);
				}

				// =============================

			}
		}
		map.put("qudao", qudao);
		map.put("yesterday", zr);
		map.put("qianri", qr);
		return map;

	}

	/**
	 * Y应用管理的top10
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月27日 上午11:16:14
	 * @param
	 */
	@Override
	public Map<String, Object> topTenAppli(String channelId) {
		Map<String, Object> map = new HashMap<>();
		String day = TimeUtils.getNextDay(new Date());
		Date stime = TimeUtils.string2Date(day + " 00:00:00");
		Date etime = TimeUtils.string2Date(day + " 23:59:59");
		List<LaunApplicationStatistics> applicationStatistics = launApplicationStatisticsMapper
				.selectByChannelApp(channelId, stime, etime);
		LaunApplicationStatistics selectBySum = launApplicationStatisticsMapper.selectBySum(channelId, stime, etime);
		Long startUpNum = 0L;
		if (null != selectBySum) {
			startUpNum = selectBySum.getStartUpNum();
		}
		List<Object> nameList = new ArrayList<>();
		List<Object> proList = new ArrayList<>();
		Map<String, Object> proMap = new HashMap<>();
		if (applicationStatistics.size() > 0) {
			for (LaunApplicationStatistics launApplicationStatistics : applicationStatistics) {
				if (startUpNum > 0) {
					String returnBigDecimal = returnBigDecimal(launApplicationStatistics.getStartUpNum(), startUpNum);
					nameList.add(launApplicationStatistics.getApplicationName());
					proMap.put("value", launApplicationStatistics.getStartUpNum());
					proMap.put("parent", returnBigDecimal);
				}
				proList.add(proMap);
			}
			map.put("x", proList);
			map.put("y", nameList);
		}
		return map;
	}

	public String returnBigDecimal(Long num, Long sum) {
		BigDecimal carStatistics = new BigDecimal(Double.toString(num));
		BigDecimal carStart = new BigDecimal(Double.toString(sum));
		String carStartProp = carStatistics.divide(carStart, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
				.toString();
		return carStartProp + "%";
	}

	@Override
	public Map<String, Object> themeZheStatistics(String channelIds, String starTime, String endTime) {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		List<String> channelIdsList = null;
		if (HStringUtlis.isNotBlank(channelIds)) {
			channelIdsList = Arrays.asList(channelIds.split(","));

		}
		// 计算时间数组
		List<String> timeList = new ArrayList<String>();

		int longOfTwoDate = TimeUtils.longOfTwoDate(starTime, endTime, "yyyy-MM-dd");
		for (int i = 0; i <= longOfTwoDate; i++) {
			Date dateReckon = TimeUtils.dateReckon(starTime, i, "yyyy-MM-dd");
			timeList.add(TimeUtils.date2String(dateReckon, "yyyy-MM-dd"));
		}

		List<LaunThemeStatistics> list = launThemeStatisticsMapper.selectHistoryEffe(channelIdsList, starTime, endTime);
		Map<String, Map<String, LaunThemeStatistics>> groupMap = new HashMap<String, Map<String, LaunThemeStatistics>>();
		// 根据渠道id分组
		for (LaunThemeStatistics launThemeStatistics : list) {
			String channelId = launThemeStatistics.getChannelName();
			Map<String, LaunThemeStatistics> map = groupMap.get(channelId);
			if (map != null) {
				map.put(launThemeStatistics.getStrNumStartTime(), launThemeStatistics);
			} else {
				Map<String, LaunThemeStatistics> map1 = new HashMap<String, LaunThemeStatistics>();
				map1.put(launThemeStatistics.getStrNumStartTime(), launThemeStatistics);
				groupMap.put(channelId, map1);
			}
		}

		Map<String, Object> returnMapOne = null;
		for (Entry<String, Map<String, LaunThemeStatistics>> etr : groupMap.entrySet()) {
			returnMapOne = new HashMap<>();
			Map<String, LaunThemeStatistics> value = etr.getValue();
			returnMapOne.put("name", etr.getKey());
			List<Long> effeCountList = new ArrayList<Long>();
			for (String dateStr : timeList) {
				LaunThemeStatistics launThemeStatistics = value.get(dateStr);
				if (launThemeStatistics != null) {
					effeCountList.add(launThemeStatistics.getEffeTheme());
				} else {
					effeCountList.add(0L);
				}
			}
			returnMapOne.put("data", effeCountList);
			returnList.add(returnMapOne);
		}
		returnMap.put("time", timeList);
		returnMap.put("list", returnList);

		// Date nowDate = new Date();
		// String nowString = TimeUtils.date2String(nowDate, "yyyy-MM-dd");
		// 判断时间是否大于当天
		// int term1 = TimeUtils.compareDate(starTime, nowString, "yyyy-MM-dd");
		// int term2 = TimeUtils.compareDate(endTime, nowString, "yyyy-MM-dd");

		return returnMap;
	}

	public static void main(String[] args) {
		String s = "2018-07-11";
		String e = "2018-07-15";
		int longOfTwoDate = TimeUtils.longOfTwoDate(s, e, "yyyy-MM-dd");
		// 计算时间数组
		List<String> timeList = new LinkedList<String>();
		for (int i = 0; i <= longOfTwoDate; i++) {
			Date dateReckon = TimeUtils.dateReckon(s, i, "yyyy-MM-dd");
			timeList.add(TimeUtils.date2String(dateReckon, "yyyy-MM-dd"));
		}
		for (String string : timeList) {
			System.out.println(string);
		}
		System.out.println(longOfTwoDate);
	}

}