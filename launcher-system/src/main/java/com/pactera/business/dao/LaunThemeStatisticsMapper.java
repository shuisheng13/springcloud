package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeStatistics;

public interface LaunThemeStatisticsMapper extends BaseMapper<LaunThemeStatistics> {

	/**
	 * @description 主题统计
	 * @author liudawei
	 * @since 2018年5月8日 下午3:25:03
	 * @param
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeStatistics> selectThemeStatistics(@Param("startTime") Long startTime, @Param("endTime") Long endTime,
			@Param("channel") Long channel, @Param("version") Long version, @Param("type") Long type);

	/**
	 * 近30天主题统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 上午11:15:16
	 * @param
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeStatistics> selectByType(@Param("channelId") String channelId, @Param("type") Long type,
			@Param("stime") String stime, @Param("etime") String etime);

	/**
	 * 渠道为空的
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月26日 上午10:42:27
	 * @param
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeStatistics> selectByChannelIdNull(@Param("stime") String stime, @Param("etime") String etime);

	/**
	 * 查询某日的主题top榜
	 * 
	 * @author LL
	 * @param isLimit是否分页
	 * @date 2018年7月27日 下午2:30:25
	 * @param timeString时间
	 * @param channelId渠道主键id
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeStatistics> selectTopTheme(@Param("timeString") String timeString,
			@Param("channelId") String channelId, @Param("isLimit") int isLimit);
}