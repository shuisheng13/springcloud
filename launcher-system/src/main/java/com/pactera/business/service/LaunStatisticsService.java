package com.pactera.business.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunWidgetStatistics;

public interface LaunStatisticsService {

	/**
	 * @description:查询渠道列表
	 * @author:wangyaqun
	 * @return
	 */
	PageInfo<LaunChannel> findChannelList();

	/**
	 * @description:widget使用情况-图表展示
	 * @author:wangyaqun
	 * @param version
	 * @return
	 */
	Map<String, Object> findWisdgetStatic(Long startTime, Long endTime, String channelId, String version);

	/**
	 * @description:查询widget使用情况详情
	 * @author:wangyaqun
	 * @return
	 */
	// PageInfo<LaunWidgetStatistics> findWisdgetDetailStatic(Integer pageNum,
	// Integer pageSize, Long startTime,
	// Long endTime, String channelId);

	/**
	 * @description 列表
	 * @author liudawei
	 * @since 2018年5月7日 下午6:24:09
	 * @param
	 * @return PageInfo<LaunCarStatistics>
	 */
	PageInfo<LaunCarStatistics> selectCarListStatistics(Long startTime, Long endTime, Long channel, Long version,
			Long type, int pageNum, int pageSize);

	Map<String, Object> selectCarStatistics(Long startTime, Long endTime, Long channel, String version, Long type);

	/**
	 * @description
	 * @author liudawei 主题统计
	 * @since 2018年5月8日 下午2:50:05
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> selectThemeStatistics(Long startTime, Long endTime, Long channel, Long version, Long type);

	/**
	 * @description:widget使用情况-概览列表
	 * @author:wangyaqun
	 * @param version
	 * @return
	 */
	PageInfo<LaunWidgetStatistics> findOverViewStatic(Integer pageNum, Integer pageSize, Long startTime, Long endTime,
			String channelId, String version);

	/**
	 * @description:应用分布-图表，概览列表
	 * @param startTime
	 * @param endTime
	 * @param channelId
	 * @param version
	 * @return
	 */
	Map<String, Object> findApplicationStatic(Long startTime, Long endTime, String channelId, String version);

	/**
	 * @description:应用分布-详情列表
	 * @param pageNum
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param channelId
	 * @param version
	 * @return
	 */
	PageInfo<LaunApplicationStatistics> findAppDetailStatic(Integer pageNum, Integer pageSize, Long startTime,
			Long endTime, String channelId, String version);

	/**
	 * 版本统计相关
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月4日 下午3:43:55
	 * @param
	 * @return Map<String,Object>
	 */

	Map<String, Object> versionStatistics(Long startTime, Long endTime, Long type, Long channel, String versions);

	/**
	 * 查询所有的版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月5日 上午10:31:04
	 * @param
	 * @return List<String>
	 */
	List<String> getVersion();

	/**
	 * 查询详情统计版本的分页
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午3:51:12
	 * @param
	 * @return PageInfo<LaunCarStatistics>
	 */
	PageInfo<LaunCarStatistics> versionXiang(Long startTime, int pageNum, int pageSize);

	/**
	 * 版本趋势统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午4:27:42
	 * @param
	 * @return List<LaunCarStatistics>
	 */
	List<LaunCarStatistics> versionTrend(Long startTime, Long endTime, String versions, Long channel);

}
