package com.pactera.business.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.vo.LaunThemeVo;

/**
 * @description: 主题的响应的接口
 * @author:woqu
 * @since:2018年4月26日 上午11:26:22
 */
public interface LaunThemeService {

	/**
	 * @description 根据条件去查询主题
	 * @author liudawei
	 * @since 2018年4月26日 上午11:26:44
	 * @param type
	 *            类型 title 名称 status 上架状态
	 * @return PageInfo<LaunThemeAdministration>
	 */
	PageInfo<LaunThemeVo> selectByCoundy(Long type, String version, Long channel, String title, Integer status,
			int pageNum, int pageSize);

	/**
	 * @description 根据id去查看主题详情
	 * @author liudawei
	 * @since 2018年4月26日 下午2:27:01
	 * @param id
	 *            主键
	 * @return LaunThemeAdministration
	 */
	Map<String, Object> selectById(Long id);

	/**
	 * @description 根据id去删除主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:33:31
	 * @param id主键
	 * @return void
	 */
	int delectById(Long id);

	/**
	 * @description 修改上下架状态
	 * @author liudawei
	 * @since 2018年4月26日 下午2:47:54
	 * @param ids
	 *            主键已逗号分隔
	 * @param status
	 *            状态
	 * @return void
	 */
	void modifyStatus(String ids, Integer status);

	/**
	 * @description 保存主题
	 * @author peill
	 * @param baseJson底屏json
	 * @param widgetJson组件json
	 * @param themeJson主题基础信息json
	 * @param saveType
	 *            0保存widget 1主题保存
	 * @since 2018年4月29日 下午2:46:33
	 * @param
	 * @return int
	 */
	Long saveTheme(String baseJson, String widgetJson, String themeJson, Integer saveType);

	/**
	 * 打包主题zip包
	 * 
	 * @author LL
	 * @date 2018年5月25日 上午10:31:14
	 * @param theme主题
	 * @param themeConfig需要下载的文件集合
	 * @return Map<String, Object> length:文件大小 length:文件大小
	 */
	Map<String, Object> saveAssembleConfigJson(LaunThemeAdministration theme, Map<String, String> themeConfig);

	/**
	 * @description 修改主题
	 * @author peill
	 * @param baseJson底屏json
	 * @param widgetJson组件json
	 * @param themeJson主题基础信息json
	 * @param saveType
	 *            0保存widget 1主题保存
	 * @since 2018年5月29日 下午2:46:33
	 * @param
	 * @return int
	 */
	Long updateTheme(String baseJson, String widgetJson, String themeJson, Integer saveType);
}
