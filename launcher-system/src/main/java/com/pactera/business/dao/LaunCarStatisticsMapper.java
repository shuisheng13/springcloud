package com.pactera.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunCarStatistics;

public interface LaunCarStatisticsMapper extends BaseMapper<LaunCarStatistics> {

	/**
	 * 查询版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月5日 上午10:37:46
	 * @param
	 * @return List<String>
	 */
	List<String> selectVersion();

	/**
	 * 版本统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月6日 下午3:36:31
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> versionStatistics(@Param("stime") Date stime, @Param("etime") Date etime,
			@Param("asList") List<String> asList, @Param("channel") Long channel);

	/**
	 * 版本趋势统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午5:28:10
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> selectVersionTrend(@Param("stime") Date stime, @Param("etime") Date etime,
			@Param("asList") List<String> asList, @Param("sdate") Date sdate, @Param("edate") Date edate,
			@Param("channel") Long channel);

	/**
	 * 今日概况
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月13日 下午3:44:21
	 * @param
	 * @return LaunCarStatistics
	 */
	LaunCarStatistics selectYesCar(@Param("channelId") String channelId, @Param("stime") String stime,
			@Param("etime") String etime);

	/**
	 * 近30日详情车辆统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 上午10:54:56
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> selectByType(@Param("channelId") String channelId, @Param("type") Long type,
			@Param("stime") String stime, @Param("etime") String etime);

	/**
	 * top版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 上午11:54:23
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	/**
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午11:17:00
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> selectTopVersion(@Param("channelId") String channelId, @Param("sdate") Date sdate,
			@Param("edate") Date edate);

	/**
	 * 渠道为空的时候的今日概况
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午10:03:54
	 * @param
	 * @return LaunCarStatistics
	 */
	LaunCarStatistics selectYesCarByChannelId(@Param("stime") String stime, @Param("etime") String etime);

	/**
	 * 渠道为空的30天
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午10:11:49
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> selectByChannelId(@Param("type") Long type, @Param("stime") String stime,
			@Param("etime") String etime);

	/**
	 * 昨天渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午3:31:54
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> selectByTopCar(@Param("start") Date start, @Param("end") Date end,
			@Param("type") Long type);

	/**
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午3:52:48
	 * @param
	 * @return Long
	 */
	LaunCarStatistics selectSumCar(@Param("start") Date start, @Param("end") Date end);

}