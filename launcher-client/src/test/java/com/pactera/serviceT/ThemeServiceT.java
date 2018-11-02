package com.pactera.serviceT;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunClientThemeService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.vo.LaunThemeShopVo;

/**
 * @description:应用相关测试
 * @author:Scott
 * @since:2018年4月26日 下午4:10:37
 */
public class ThemeServiceT extends Tester {

	@Autowired
	private LaunClientThemeService launClientThemeService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void test() {

		String channle = "13754Z";
		String version = "1";
		Long screenHeight = 720L;
		Long screenWidth = 1080L;
		Integer type = 2;
		String userId = null;
		String city = null;
		List<LaunThemeShopVo> themeList = launClientThemeService.getThemeList(channle, version, screenHeight,
				screenWidth, userId, city, type);

		System.out.println(themeList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		String channle = "13754Z";
		// 从redis中根据渠道查询
		List<LaunThemeAdministration> list = (List<LaunThemeAdministration>) redisTemplate.opsForHash()
				.get(ConstantUtlis.THEME_REDIS_FLAG, channle);

		System.out.println(list.size());
	}
}
