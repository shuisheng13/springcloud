package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.domain.LaunThemeEffe;

import tk.mybatis.mapper.common.Mapper;

public interface LaunThemeEffeMapper extends Mapper<LaunThemeEffe> {

	/**
	 * 查询主题折线统计(多渠道)
	 * 
	 * @author LL
	 * @date 2018年8月2日 下午5:16:44
	 * @param
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeEffe> selectHistoryEffe(@Param("channelIds") List<String> channelIds,
			@Param("starTime") String starTime, @Param("endTime") String endTime);

	/**
	 * 查询主题折线统计(单渠道)
	 * 
	 * @author LL
	 * @date 2018年8月2日 下午5:16:44
	 * @param
	 * @return List<LaunThemeStatistics>
	 */
	List<LaunThemeEffe> selectHistoryEffeOne(@Param("channelId") String channelId, @Param("starTime") String starTime,
			@Param("endTime") String endTime);
}