package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunThemeConfig;

/**
 * @description: 关于主题中的配置接口
 * @author:woqu
 * @since:2018年4月29日 下午3:22:48
 */
public interface LaunThemeConfigService {

	/**
	 * @description
	 * @author liudawei
	 * @since 2018年4月29日 下午3:22:43
	 * @param
	 * @return void
	 */
	Long save(LaunThemeConfig launThemeConfig);

	/**
	 * 根据主题id查询全部widget配置
	 * 
	 * @author LL
	 * @date 2018年4月29日 下午4:10:49
	 * @param id主题主键id
	 * @return List<LaunThemeConfig>
	 */
	List<LaunThemeConfig> findByThemeId(Long id);

	/**
	 * 根据主题id查询全部widget配置
	 * 
	 * @author LL
	 * @date 2018年4月29日 下午4:10:49
	 * @param id主题主键id
	 * @return List<LaunThemeConfig>
	 */
	void deleteByThemeId(Long id);

}
