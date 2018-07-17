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
	LaunCarStatistics selectYesCar(@Param("channelId") Long channelId, @Param("stime") Date stime,
			@Param("etime") Date etime);
}