package com.pactera.business.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunCustomStatistics;
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

	/**
	 * 自定义事件
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月10日 上午9:52:30
	 * @param
	 * @return PageInfo<LaunCustomStatistics>
	 */
	PageInfo<LaunCustomStatistics> customStatic(Long startTime, Long endTime, Long channel, String version,
			String custom, int pageSize, int pageNum);

	/**
	 * 根据时间的参数去查询详情
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月10日 下午2:33:15
	 * @param
	 * @return List<LaunCustomStatistics>
	 */
	List<LaunCustomStatistics> customStaticById(String paramName);

	/**
	 * 根据参数去查看详情
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月11日 上午10:37:42
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> customXiangqing(Long startTime, Long endTime, Long channel, String version, String custom,
			Long type);

	/**
	 * 今日概况
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月13日 下午3:25:13
	 * @param
	 * @return LaunCarStatistics
	 */
	LaunCarStatistics yesCar(Long channelId);

	/**
	 * 近30天的趋势
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月13日 下午4:10:35
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> trendCar(Long channelId, Long type);

	/**
	 * top版本
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 上午11:45:19
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> topVersion(Long channelId, Long type);

	/**
	 * top主题
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月18日 下午2:04:28
	 * @param
	 * @return Map<String,Object>
	 */
	Map<String, Object> topTheme(Long channelId, Long type);

}
