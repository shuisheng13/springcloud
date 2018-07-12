package com.pactera.business.dao;

import java.util.List;
import java.util.Map;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunWidgetFile;

/**
 * @description widget文件mapper
 * @author lzp
 * @date 2018年5月25日17:58:03
 *
 */
public interface LaunWidgetFileMapper extends BaseMapper<LaunWidgetFile> {

	List<Map<String,String>> selectfilepath(LaunWidgetFile launwidthfile);
	
	/**
	 * 根据widgetId查询图片和lib文件
	 * @param widgetId
	 * @return
	 */
	List<LaunWidgetFile> findwidgetfile(Long widgetId);
	
}
