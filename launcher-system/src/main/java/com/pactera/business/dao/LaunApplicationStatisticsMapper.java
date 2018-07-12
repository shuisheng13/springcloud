package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.domain.LaunApplicationStatistics;

public interface LaunApplicationStatisticsMapper {

	/**
	 * 
	 * @param sTime
	 * @param eTime
	 * @param channelId
	 * @param version
	 * @return
	 */
	List<LaunApplicationStatistics> findAppDetailStatic(@Param("sTime") String sTime, @Param("eTime") String eTime,
			@Param("channelId") String channelId, @Param("version") String version);

}
