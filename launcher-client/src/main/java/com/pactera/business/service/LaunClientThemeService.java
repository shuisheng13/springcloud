package com.pactera.business.service;

import java.util.List;
import java.util.Map;

import com.pactera.vo.LaunThemeShopVo;

/**
 * launcher客户端请求接口
 * 
 * @description:
 * @author:LL
 * @since:2018年6月1日 上午11:35:43
 */
public interface LaunClientThemeService {

	/**
	 * 获取主题列表
	 * 
	 * @author LL
	 * @param city
	 *            用户所在地区
	 * @param userId
	 *            用户 ID
	 * @param screenWidth
	 *            屏宽
	 * @param screenHeight
	 *            屏高
	 * @param version
	 *            版本
	 * @param channle
	 *            渠道名称
	 * @param type
	 *            类型1全部;2上架;3未上架
	 * @date 2018年6月1日 下午7:34:35
	 * @param
	 * @return List<Map<String,Object>>
	 */
	List<LaunThemeShopVo> getThemeList(String channle, String version, Long screenHeight, Long screenWidth,
			String userId, String city, Integer type);

	/**
	 * 获取强推主题
	 * 
	 * @author LL
	 * @param posterIds
	 *            当前主题包所包含的海报广告位 ID，如果有多个以逗号分隔“，”
	 * @param city
	 *            用户所在地区
	 * @param userId
	 *            用户 ID
	 * @param screenWidth
	 *            屏宽
	 * @param screenHeight
	 *            屏高
	 * @param version
	 *            版本
	 * @param channle
	 *            渠道名称
	 * @date 2018年6月1日 下午7:34:35
	 * @param
	 * @return List<Map<String,Object>>
	 */
	Map<String, Object> getPosters(String channle, String version, Long screenHeight, Long screenWidth, String userId,
			String city, String posterIds);

}
