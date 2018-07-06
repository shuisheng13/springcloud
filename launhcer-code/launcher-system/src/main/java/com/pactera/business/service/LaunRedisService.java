package com.pactera.business.service;

/**
 * @description:业务redis处理
 * @author:LL
 * @since:2018年6月26日 上午9:51:49
 */
public interface LaunRedisService {

	/**
	 * 根据渠道名称删除渠道
	 * 
	 * @author LL
	 * @date 2018年6月26日 上午10:07:59
	 * @param channelName渠道名称
	 * @return void
	 */
	void removeChannel(Long channelId);

	/**
	 * 新增渠道,加入redis
	 * 
	 * @author LL
	 * @date 2018年6月26日 上午10:13:50
	 * @param channelId渠道id
	 * @param name渠道名称
	 * @return void
	 */
	void addChannel(Long channelId, String name);

	/**
	 * 更新redis缓存
	 * @author LL
	 * @date 2018年7月2日 下午3:52:14
	 * @param 
	 * @return void
	 */
	void initThemeShop();

}
