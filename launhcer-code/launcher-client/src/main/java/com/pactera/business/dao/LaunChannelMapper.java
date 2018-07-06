package com.pactera.business.dao;

import java.util.List;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunChannel;

public interface LaunChannelMapper extends BaseMapper<LaunChannel> {

	/**
	 * @description:查询渠道列表
	 * @author:wangyaqun
	 * @return
	 */
	List<LaunChannel> findChannelList();

}