package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.business.service.LaunRedisService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeAdministration;

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
	 * @author LL
	 * @date 2018年6月26日 上午10:02:34
	 * @param
	 * @return void
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
	 * @author LL
	 * @date 2018年6月26日 上午10:03:05
	 * @param
	 * @return void
	 */
	@Override
	public void initThemeShop() {

		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();

		Integer status = 2;// 已上架的主题状态
		List<LaunThemeAdministration> byExample = launThemeMapper.selectByStatus(status);

		// 按照渠道进行分类
		Map<Long, List<LaunThemeAdministration>> map = new HashMap<Long, List<LaunThemeAdministration>>();

		for (LaunThemeAdministration launThemeAdministration : byExample) {
			Long channleId = launThemeAdministration.getCreatorChannelId();
			if (map.get(channleId) != null) {
				map.get(channleId).add(launThemeAdministration);
			} else {
				List<LaunThemeAdministration> value = new ArrayList<LaunThemeAdministration>();
				value.add(launThemeAdministration);
				map.put(channleId, value);
			}
		}

		// 所有渠道都拥有的主题
		List<LaunThemeAdministration> list = map.get(0L);

		Map<String, List<LaunThemeAdministration>> redisMap = new HashMap<String, List<LaunThemeAdministration>>();
		if (map.size() > 0) {
			for (Entry<Long, List<LaunThemeAdministration>> launThemeAdministration : map.entrySet()) {
				Long key = launThemeAdministration.getKey();
				List<LaunThemeAdministration> value = launThemeAdministration.getValue();
				if (!key.equals(0L)) {
					if (list != null) {
						value.addAll(list);
					}
					LaunChannel findById = launChannelService.findById(key.toString());
					redisMap.put(findById.getName(), value);
				}
			}
		}
		redisTemplate.delete(ConstantUtlis.THEME_REDIS_FLAG);
		opsForHash.putAll(ConstantUtlis.THEME_REDIS_FLAG, redisMap);
	}

	public static void main(String[] args) {
		String a = "2";
		String b = "1";
		System.out.println(a.compareTo(b));
	}
}
