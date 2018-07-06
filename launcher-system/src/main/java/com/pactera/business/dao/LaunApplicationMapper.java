package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunApplication;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.vo.LaunApplicationVo;

/**
 * 
 * @description:应用接口
 * @author:Scott
 * @since:2018年4月26日 下午2:27:20
 */
public interface LaunApplicationMapper extends BaseMapper<LaunApplication> {

	/**
	 * 根据条件查询应用列表
	 * 
	 * @param channelId
	 * @param keyword
	 * @return
	 */
	List<LaunApplicationVo> findByCondition(@Param("channelId") String channelId, @Param("keyword") String keyword);

	/**
	 * 根据应用id查询海报详情
	 * 
	 * @param id
	 * @param type
	 *            0 默认 1自定义
	 * @return
	 */
	List<LaunApplicationPoster> findPosterByAppIdAndType(@Param("id") Long id, @Param("type") Integer type);

	/**
	 * 
	 * @param appId
	 *            应用id
	 * @param listIds
	 *            渠道id集合
	 */
	void saveApplication2Channel(@Param("appId") Long appId, @Param("channelIds") List<String> channelIds);

	/**
	 * 查询自定义海报
	 * @param id
	 * @return
	 */
	List<LaunApplicationPoster> findCustomPosterByAppId(Long id);

	/**
	 * 保存海报详情
	 * @param launApplicationPoster
	 * @return
	 */
	Integer savePoster(LaunApplicationPoster launApplicationPoster);

	/**
	 * 根据应用id删除海报
	 * @param applicationId
	 */
	void deletePosterByAppId(Long applicationId);
}
