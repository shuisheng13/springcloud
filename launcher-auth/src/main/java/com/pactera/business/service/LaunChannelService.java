package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunUser;
import com.pactera.vo.LaunAuthChannelVo;

/**
 * @description: 渠道管理的接口
 * @author:woqu
 * @since:2018年5月24日 下午7:05:02
 */
public interface LaunChannelService {

	/**
	 * 保存渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:05:12
	 * @param channel
	 * @param userId
	 * @param permissionids
	 * @return Integer
	 */
	Integer saveChannel(LaunChannel channel, Long userId, String[] permissionids);

	/**
	 * 删除
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:05:47
	 * @param id
	 * @return Integer
	 */
	Integer deleteChannel(Long id);

	/**
	 * 查询渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:06:00
	 * @param pageNum
	 * @param pageSize
	 * @return List<LaunChannel>
	 */
	List<LaunAuthChannelVo> findChannelList(Integer pageNum, Integer pageSize);

	/**
	 * 更新渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:06:17
	 * @param channel
	 * @param userId
	 * @param permissionids
	 * @return Integer
	 */
	Integer updateChannel(LaunChannel channel, Long userId, String[] permissionids);

	/**
	 * 选择
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:06:33
	 * @param channelName
	 * @param channelId
	 * @return Integer
	 */
	Integer checkRepeatChannel(String channelName, String channelId);

	/**
	 * 根据id去查询
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:06:51
	 * @param id
	 * @return LaunChannel
	 */
	LaunChannel findChannelById(Long id);

	/**
	 * 获取
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:07:11
	 * @param
	 * @return String
	 */
	String getChannelId();

	/**
	 * 批量更新渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:07:21
	 * @param channelList
	 * @return Integer
	 */
	Integer updateChannelList(String channelList);

	/**
	 * 查询所有的渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:07:37
	 * @param
	 * @return List<LaunChannel>
	 */
	List<LaunChannel> findChannelAll();

	/**
	 * 根据id去查询渠道
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:07:47
	 * @param channelId
	 * @return List<LaunUser>
	 */
	List<LaunUser> findUserByChannelId(Long channelId);

	/**
	 * 删除渠道提示语
	 * 
	 * @author LL
	 * @date 2018年8月8日 下午3:12:58
	 * @param id渠道主键id
	 * @return Integer
	 */
	Integer deleteChannelCue(Long id);

}
