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
}