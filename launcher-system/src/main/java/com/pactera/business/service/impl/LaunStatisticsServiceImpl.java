package com.pactera.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunApplicationStatisticsMapper;
import com.pactera.business.dao.LaunCarStatisticsMapper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
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
	public Map<String, Object> selectCarStatistics(Long startTime, Long endTime, Long channel, Long version,
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
				if (0 == type) {
					// carNum
					carn.add(launCarStatistics.getCarNum());
				} else if (1 == type) {
					// 活跃车辆
					carn.add(launCarStatistics.getCarActive());
				} else if (2 == type) {
					// 启动次数
					carn.add(launCarStatistics.getCarStart());
				} else if (3 == type) {
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
			BigDecimal startUpNum = new BigDecimal(Double.toString(launCarStatistics.getStartUpNum()));
			String carStartProp = carStatistics.divide(startUpNum, 2, BigDecimal.ROUND_HALF_UP)
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> versionStatistics(Long startTime, Long endTime, int pageNum, int pageSize, Long type,
			String versions) {
		return null;
	}

}
