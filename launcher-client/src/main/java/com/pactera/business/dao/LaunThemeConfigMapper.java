package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeConfig;

public interface LaunThemeConfigMapper extends BaseMapper<LaunThemeConfig> {

	/**
	 * 根据主题id查询配置
	 * 
	 * @author LL
	 * @date 2018年4月29日 下午4:42:33
	 * @param id主题主键
	 * @return List<LaunThemeConfig>
	 */
	List<LaunThemeConfig> findByThemeId(@Param("id") Long id);

}
