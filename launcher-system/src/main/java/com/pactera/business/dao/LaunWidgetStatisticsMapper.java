package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunWidgetStatistics;

public interface LaunWidgetStatisticsMapper extends BaseMapper<LaunWidgetStatistics> {
	
	
	
	List<LaunWidgetStatistics> queryWidgetGroup(@Param("channelId")String channelId,
												@Param("version")String version,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
	
	
	List<LaunWidgetStatistics> queryWidgetstartupnum(@Param("channelId")String channelId,
													 @Param("version")String version,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
	
	
	List<LaunWidgetStatistics> queryBywidget(@Param("channelId")String channelId,
			 @Param("version")String version,@Param("beginDate")String beginDate,@Param("endDate")String endDate,String codeId);

	/**
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param channelId
	 * @return
	 */
	List<LaunWidgetStatistics> findWisdgetStatic(@Param("sTime") String sTime, @Param("eTime") String eTime,
			@Param("channelId") String channelId, @Param("version") String version);

	/**
	 * 
	 * @param sTime
	 * @param eTime
	 * @param channelId
	 * @return
	 */
	List<LaunWidgetStatistics> findWisdgetDetailStatic(@Param("sTime") String sTime, @Param("eTime") String eTime,
			@Param("channelId") String channelId);

	/**
	 * 
	 * @param sTime
	 * @param eTime
	 * @param channelId
	 * @param version
	 * @return
	 */
	List<LaunWidgetStatistics> findOverViewStatic(@Param("sTime") String sTime, @Param("eTime") String eTime,
			@Param("channelId") String channelId, @Param("version") String version);

	/**
	 * 
	 * @param sTime
	 * @param eTime
	 * @param channelId
	 * @param version
	 * @return
	 */
	List<LaunApplicationStatistics> findApplicationStatic(@Param("sTime") String sTime, @Param("eTime") String eTime,
			@Param("channelId") String channelId, @Param("version") String version);

}
