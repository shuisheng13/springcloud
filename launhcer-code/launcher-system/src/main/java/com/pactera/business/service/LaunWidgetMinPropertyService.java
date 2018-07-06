package com.pactera.business.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * widget属性相关接口
 * 
 * @description:
 * @author:LL
 * @since:2018年5月1日 下午4:51:55
 */
public interface LaunWidgetMinPropertyService {

	/**
	 * 根据widgetId主键id查询属性
	 * 
	 * @author LL
	 * @date 2018年5月1日 下午4:50:10
	 * @param widgetId主键id
	 * @return List<LaunWidgetMinProperty>
	 */
	List<Map<String, Object>> selectByWidgetId(@Param("widgetId") Long widgetId);
}
