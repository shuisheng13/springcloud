package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunThemeFile;

/**
 * @description:主体文件接口
 * @author:LL
 * @since:2018年4月27日 下午5:22:08
 */
public interface LaunThemeFileService {

	/**
	 * 根据主体id查询文件
	 * @author LL
	 * @date 2018年4月27日 下午5:27:49
	 * @param themeId主题主键id
	 * @return List<LaunThemeFile>
	 */
	List<LaunThemeFile> selectByThemeId(Long themeId);
	
	/**
	 * 根据themeId插入文件
	 * @author LL
	 * @date 2018年4月29日 下午5:26:58
	 * @param json文件
	 * @param themeId主题主键
	 * @return void
	 */
	void insert(String json,Long themeId);

	/**
	 * 插入主题预览图
	 * @author LL
	 * @date 2018年5月23日 下午2:09:25
	 * @param themeFile主图文件实体
	 * @return void
	 */
	void insert(LaunThemeFile themeFile);
	
	/**
	 * 根据主键删除图片
	 * @author LL
	 * @date 2018年5月31日 下午8:18:27
	 * @param id主键id
	 * @return void
	 */
	void deleteById(Long id);
}
