package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeAdministration;

/**
 * @description:主题的mapper
 * @author:woqu
 * @since:2018年4月26日 上午11:29:48
 */
public interface LaunClientThemeMapper extends BaseMapper<LaunThemeAdministration> {

	/**
	 * @description 根据条件去查询主题
	 * @author liudawei
	 * @param screenWidth屏宽
	 * @param screenHeight屏高
	 * @param version版本
	 * @param channleName渠道名称
	 * @since 2018年4月26日 上午11:31:01
	 * @return List<LaunThemeAdministration>
	 */
	List<LaunThemeAdministration> getThemeList(@Param("channleId") Long channleId, @Param("version") String version,
			@Param("height") Long screenHeight, @Param("width") Long screenWidth);

	/**
	 * @description 查询强推主题
	 * @author liudawei
	 * @param screenWidth屏宽
	 * @param screenHeight屏高
	 * @param version版本
	 * @param channleId渠道主键
	 * @since 2018年4月26日 上午11:31:01
	 * @return List<LaunThemeAdministration>
	 */
	LaunThemeAdministration pushTheme(@Param("channleId") Long channleId, @Param("version") String version,
			@Param("height") Long screenHeight, @Param("width") Long screenWidth);

}
