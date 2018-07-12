package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunChannel;

/**
 * @description:渠道管理Service
 * @author:LL
 * @since:2018年4月26日 下午11:42:14
 */
public interface LaunChannelService {

	/**
	 * 查询所有渠道(无分页)
	 * 
	 * @author LL
	 * @date 2018年4月27日 上午10:20:46
	 * @param name模糊查询name字段
	 * @return List<LaunChannel>
	 */
	List<LaunChannel> findAll(String name);

	/**
	 * 根据id获取渠道详情
	 * 
	 * @param id
	 * @return
	 */
	LaunChannel findById(String id);

	/**
	 * 根据名称查询渠道
	 * 
	 * @author LL
	 * @date 2018年6月1日 下午3:07:11
	 * @param channleName渠道名称
	 * @return LaunChannel
	 */
	LaunChannel findByName(String channleName);

}
