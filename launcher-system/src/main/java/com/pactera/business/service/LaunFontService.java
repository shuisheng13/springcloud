package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunFont;

/**
 * @description: 字体相关接口
 * @author:LL
 * @since:2018年7月10日 上午10:02:19
 */
public interface LaunFontService {

	/**
	 * 查询字体列表
	 * 
	 * @author LL
	 * @date 2018年7月10日 上午10:08:16
	 * @param
	 * @return List<LaunFont>
	 */
	List<LaunFont> getList();

	/**
	 * 添加字体
	 * 
	 * @author LL
	 * @date 2018年7月10日 上午10:08:36
	 * @param fontName字体名称
	 * @param filePath字体路径
	 * @return void
	 */
	void addFont(String fontName, String filePath);

	/**
	 * 查询默认字体
	 * 
	 * @author LL
	 * @date 2018年7月12日 上午11:32:33
	 * @param
	 * @return void
	 */
	LaunFont selectFont();
}
