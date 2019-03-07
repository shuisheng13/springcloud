package com.pactera.business.service;

import com.pactera.domain.LaunThemeAdministration;
import com.pactera.po.ThemesParam;
import com.pactera.vo.LaunPage;
import com.pactera.vo.LaunResolution;
import com.pactera.vo.LaunThemeUploadFileVo;
import com.pactera.vo.LaunThemeVo;
import org.springframework.web.multipart.MultipartFile;

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
     */
	LaunPage<LaunThemeVo> query(ThemesParam themes);

    /**
     * v2 版本 删除，上下架 禁用启用使用
	 * 状态类型有：上架/下架（2，3）  删除（-1） 禁用/启用【未上架】（0，1）
     * @param ids
     * @param status
     */
    int changeStatus(List<String> ids, Integer status);

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
	 * 修改车机端主题排序权重
	 * @param id 主题id
	 * @param sort 排序权重
	 * @return
	 */
	int sort(String id, Long sort);

	/**
	 * 修改车机端主题推荐排序权重
	 * @param id 主题id
	 * @param sort 排序权重
	 * @return
	 */
	int recommendSort(String id, Integer sort);

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
	String saveTheme(String baseJson, String widgetJson, String themeJson);

	/**
	 *
	 * @param file 上传的文件包
	 */
	LaunThemeUploadFileVo upload (MultipartFile file);

	/**
	 * 推荐主题
	 * @param id 主题的id
	 * @param value 0:不推荐  1：推荐
	 */
	int recommend (String id, boolean value);

	/**
	 * 定时上下架
	 */
	void themeAutoUpDown(String timeStamp);

	/**
	 * 删除主题分类时，更新主题
	 */
	int cleanThemeClassification(String id);

	List<LaunResolution> resolution(Integer layoutId);
}
