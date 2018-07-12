package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunChannel;

public interface LaunChannelMapper extends BaseMapper<LaunChannel> {

	/**
	 * @description:查询渠道列表
	 * @author:wangyaqun
	 * @return
	 */
	List<LaunChannel> findChannelList();

	/**
	 * 根据主键id查询渠道
	 * 
	 * @author LL
	 * @date 2018年6月26日 上午11:42:07
	 * @param channelId渠道主键
	 * @return LaunChannel
	 */
	LaunChannel selectById(@Param("id") Long channelId);

}