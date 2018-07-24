package com.pactera.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunCustomStatisticsMapper;
import com.pactera.business.dao.LaunThemeStatisticsMapper;
import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunCustomStatistics;
import com.pactera.domain.LaunThemeStatistics;
import com.pactera.domain.LaunWidgetStatistics;
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
	public Map<String, Object> selectThemeStatistics(Long startTime, Long endTime, Long channel, Long version,
			Long type) {
		return null;
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
	public LaunCarStatistics yesCar(Long channelId) {
		String nextDay = TimeUtils.getNextDay(new Date());
		String stime = nextDay + " 00:00:00";
		String etime = nextDay + " 23:59:59";
		LaunCarStatistics carStatistics = carStatisticsMapper.selectYesCar(channelId, stime, etime);
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
	public Map<String, Object> trendCar(Long channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		if (null != type && (type == 1 || type == 2 || type == 3 || type == 4)) {
			map = returnCarStatis(channelId, type);
		}
		if (null != type && (type == 5 || type == 6)) {
			map = returnCarTheme(channelId, type);
		}

		return map;
	}

	public Map<String, Object> returnCarStatis(Long channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		List<LaunCarStatistics> list = carStatisticsMapper.selectByType(channelId, type);
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
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
		map.put("x", x);
		map.put("y", y);
		return map;
	}

	public Map<String, Object> returnCarTheme(Long channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		List<LaunThemeStatistics> list = launThemeStatisticsMapper.selectByType(channelId, type);
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		for (LaunThemeStatistics launThemeStatistics : list) {
			x.add(TimeUtils.date2String(launThemeStatistics.getNumStartTime(), "yyyy-MM-dd"));
			// 5:主题使用次数
			if (null != type && type == 5) {
				y.add(launThemeStatistics.getCount());
			}
			// 6:有效主题
			if (null != type && type == 6) {
				y.add(launThemeStatistics.getEffeTheme());

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
	public Map<String, Object> topVersion(Long channelId, Long type) {
		Map<String, Object> map = new HashMap<>();
		String day = TimeUtils.getNextDay(new Date());
		Date sdate = TimeUtils.string2Date(day + " 00:00:00");
		Date edate = TimeUtils.string2Date(day + " 23:59:59");
		List<LaunCarStatistics> list = carStatisticsMapper.selectTopVersion(channelId, sdate, edate);
		List<String> x = new ArrayList<>();
		List<Object> y = new ArrayList<>();
		for (LaunCarStatistics launCarStatistics : list) {
			x.add(launCarStatistics.getVersion());
			if (null != type && type == 1) {
				y.add(launCarStatistics.getCarNum());
			}
			if (null != type && type == 2) {
				y.add(launCarStatistics.getCarActive());

			}
			if (null != type && type == 3) {
				y.add(launCarStatistics.getCarStart());

			}
			if (null != type && type == 4) {
				y.add(launCarStatistics.getAddUpNum());

			}
		}
		map.put("x", x);
		map.put("y", y);
		return map;
	}

	@Override
	public Map<String, Object> topTheme(Long channelId, Long type) {

		return null;
	}

}
