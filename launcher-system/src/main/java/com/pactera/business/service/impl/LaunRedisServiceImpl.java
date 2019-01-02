package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunRedisService;
import com.pactera.domain.LaunChannel;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:业务redis实现
 * @author:LL
 * @since:2018年6月26日 上午9:53:42
 */
@Service
@Slf4j
public class LaunRedisServiceImpl implements LaunRedisService, InitializingBean {

	@Autowired
	private LaunChannelService launChannelService;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("项目启动加载-----------------------------------------");
		initThemeShop();
	}

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	/**
	 * 初始化，将渠道放入redis
	 *
	 * @param
	 * @return void
	 * @author LL
	 * @date 2018年6月26日 上午10:02:34
	 */
	public void initChannelList() {
		List<LaunChannel> findAll = launChannelService.findAll(null);
		for (LaunChannel launChannel : findAll) {
			valueOperations.set(launChannel.getName(), launChannel.getId());
		}
	}

	@Override
	public void removeChannel(Long channelId) {
		if (channelId == null) {
			log.error("redis渠道删除异常");
		}
		LaunChannel findById = launChannelService.findById(channelId.toString());
		redisTemplate.delete(findById.getName());
	}

	@Override
	public void addChannel(Long channelId, String name) {
		valueOperations.set(name, channelId);
	}

	/**
	 * 初始化主题商店，按渠道分类放入redis
	 *
	 * @param
	 * @return void
	 * @author LL
	 * @date 2018年6月26日 上午10:03:05
	 */
	@Override
	public void initThemeShop() {

	}
}

