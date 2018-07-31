package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunAdverStatistics;

public interface LaunAdverStatisticsMapper extends BaseMapper<LaunAdverStatistics> {

	/**
	 * 广告统计的相关
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午9:36:58
	 * @param
	 * @return List<LaunAdverStatistics>
	 */
	List<LaunAdverStatistics> selectByType(@Param("channelId") String channelId, @Param("stime") String stime,
			@Param("etime") String etime);

	/**
	 * 渠道为空广告统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午9:51:24
	 * @param
	 * @return List<LaunAdverStatistics>
	 */
	List<LaunAdverStatistics> selectByTypeChannelId(@Param("stime") String stime, @Param("etime") String etime);
}