package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunThemeClassification;

/**
 * @description:主题分类有关的接口
 * @author:woqu
 * @since:2018年4月26日 下午2:14:47
 */
public interface LaunThemeClassificationService {

	/**
	 * @description 添加分类
	 * @author liudawei
	 * @since 2018年4月26日 下午3:02:58
	 * @param classification
	 *            添加分类的名称：已逗号分隔
	 * @param channleId
	 *            渠道Id
	 * @param ids 删除分类的ids
	 * @return void
	 */
	void addType(String classification, String ids);

	/**
	 * @description 查询所有的分类
	 * @author liudawei
	 * @since 2018年4月26日 下午10:12:07
	 * @param
	 * @return List<LaunThemeClassification>
	 */
	List<LaunThemeClassification> selecByType();

	/**
	 * 根据分类主键删除分类
	 * 
	 * @author LL
	 * @date 2018年5月15日 下午9:47:21
	 * @param id主键
	 * @return void
	 */
	void delectById(String ids);

}
