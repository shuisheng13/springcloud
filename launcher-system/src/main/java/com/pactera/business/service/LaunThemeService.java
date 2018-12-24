package com.pactera.business.service;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.vo.LaunThemeVo;

import java.util.List;
import java.util.Map;

/**
 * @description: 主题的响应的接口
 * @author:woqu
 * @since:2018年4月26日 上午11:26:22
 */
public interface LaunThemeService {

    /**
     * v2
     * @description 根据条件去插叙年主题的实现类
     * @author liudawei
     * @since 2018年4月26日 上午11:31:16
     * @param tenantId 主题分类
     * @param type 主题分类
     * @param title 主题名称
     * @param status 主题状态
     * @param pageNum 第几页
     * @param pageSize 每页条数
     */
	PageInfo<LaunThemeVo> query(Long tenantId, Long type, String title, Integer status, int pageNum, int pageSize);

    /**
     * v2 版本 删除，上下架 禁用启用使用
     * @param id
     * @param status
     */
    int changeStatus(String id, Integer status);

	/**
	 * @description 根据id去查看主题详情
	 * @author liudawei
	 * @since 2018年4月26日 下午2:27:01
	 * @param id
	 *            主键
	 * @return LaunThemeAdministration
	 */
	Map<String, Object> selectById(String id);

	/**
	 * @description 根据id去删除主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:33:31
	 * @param id 主键
	 * @return void
	 */
	int delectById(String id);

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
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题基础信息json
	 * @param saveType
	 *            0保存widget 1主题保存
	 * @since 2018年4月29日 下午2:46:33
	 * @param
	 * @return int
	 */
	String saveTheme(String baseJson, String widgetJson, String themeJson, Integer saveType);

	/**
	 * 打包主题zip包
	 * 
	 * @author LL
	 * @date 2018年5月25日 上午10:31:14
	 * @param theme 主题
	 * @param themeConfig 需要下载的文件集合
	 * @return Map<String, Object> length:文件大小 length:文件大小
	 */
	Map<String, Object> saveAssembleConfigJson(LaunThemeAdministration theme, Map<String, String> themeConfig);

	/**
	 * @description 修改主题
	 * @author peill
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题基础信息json
	 * @param saveType
	 *            0保存widget 1主题保存
	 * @since 2018年5月29日 下午2:46:33
	 * @param
	 * @return int
	 */
	String updateTheme(String baseJson, String widgetJson, String themeJson, Integer saveType);

	/**
	 * 根据渠道分组查询有效主题
	 * 
	 * @author LL
	 * @date 2018年8月3日 下午3:56:17
	 * @param
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, String>> getEffeCount();
}
