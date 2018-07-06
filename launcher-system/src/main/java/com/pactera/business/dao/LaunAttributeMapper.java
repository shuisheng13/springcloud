package com.pactera.business.dao;



import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunAttribute;

/**
 * 
 * @description:
 * @author:lzp
 * @since:2018年5月9日20:02:19
 */
public interface LaunAttributeMapper extends BaseMapper<LaunAttribute>{
	
	/**
	 * 获取当前类型的最大值
	 * @return 
	 */
	public Integer getMaxValueByType(LaunAttribute attr);


}
