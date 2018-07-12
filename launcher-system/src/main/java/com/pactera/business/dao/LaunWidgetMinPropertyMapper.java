package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunWidgetMinProperty;

public interface LaunWidgetMinPropertyMapper extends BaseMapper<LaunWidgetMinProperty> {

	/**
	 * 根据widgetId主键id查询属性
	 * 
	 * @author LL
	 * @date 2018年5月1日 下午4:50:10
	 * @param widgetId主键id
	 * @return List<LaunWidgetMinProperty>
	 */
	List<LaunWidgetMinProperty> selectByWidgetId(@Param("widgetId") Long widgetId);
}
