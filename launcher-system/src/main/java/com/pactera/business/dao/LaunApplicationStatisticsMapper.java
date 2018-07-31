package com.pactera.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunApplicationStatistics;

public interface LaunApplicationStatisticsMapper extends BaseMapper<LaunApplicationStatistics> {

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

	/**
	 * 应用有渠道的 昨天
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午1:04:24
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunApplicationStatistics> topApplication(@Param("channelId") String channelId, @Param("stime") Date stime,
			@Param("etime") Date etime);

	/**
	 * 应用有渠道的前天
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午1:05:12
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunApplicationStatistics> topyApplication(@Param("channelId") String channelId, @Param("sytime") Date sytime,
			@Param("eytime") Date eytime);

	/**
	 * 应用全部渠道 昨天
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午1:05:47
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunApplicationStatistics> topApplicationByChannel(@Param("stime") Date stime, @Param("etime") Date etime);

	/**
	 * 应用全部渠道前天
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午1:06:10
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunApplicationStatistics> topyApplicationByChannel(@Param("sytime") Date sytime,
			@Param("eytime") Date eytime);

	/**
	 * 总数
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 下午2:33:47
	 * @param
	 * @return Long
	 */
	LaunApplicationStatistics selectBySum(@Param("channelId") String channelId, @Param("start") Date start,
			@Param("end") Date end);

	/**
	 * 应用管理的top10
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月27日 上午11:16:34
	 * @param
	 * @return List<LaunApplicationStatistics>
	 */
	List<LaunApplicationStatistics> selectByChannelApp(@Param("channelId") String channelId, @Param("stime") Date stime,
			@Param("etime") Date etime);

}
