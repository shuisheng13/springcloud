package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunWidgetWidthToHight;

/**
 * @description: 关于主题中widget中的接口
 * @author:woqu
 * @since:2018年4月29日 下午3:21:47
 */
public interface LaunThemeWidthToHightService {

	/**
	 * @description 根据id去保存
	 * @author liudawei
	 * @since 2018年4月29日 下午3:14:33
	 * @param
	 * @return void
	 */
	void save(List<LaunWidgetWidthToHight> hights);

	/**
	 * 根据配置id查询配置详情list
	 * 
	 * @author LL
	 * @date 2018年4月29日 下午4:45:11
	 * @param configId配置主键id
	 * @return List<LaunWidgetWidthToHight>
	 */
	List<LaunWidgetWidthToHight> findByConfigId(Long configId);
}
