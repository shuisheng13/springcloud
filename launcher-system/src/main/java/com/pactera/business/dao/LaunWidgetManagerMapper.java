package com.pactera.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetType;
import com.pactera.vo.LaunAttributeVo;
import com.pactera.vo.LaunWidgetTypeVo;
import com.pactera.vo.LaunWidgetVo;

public interface LaunWidgetManagerMapper extends BaseMapper<LaunWidget> {
	
	String widgetPreview(@Param("widgetId")Long widgetId);
	
	/**
	 * 
	 * @description:添加Widget类型
	 * @since:2018年4月26日 上午11:28:24
	 * @param id
	 * @param typeName
	 * @param createDate
	 * @return
	 */
	Integer insertMidgetType(@Param("id") Long id, @Param("typeName") String typeName,
			@Param("createDate") Date createDate);

	/**
	 * @description:管理员查询widget列表
	 * @since:2018年4月26日 下午12:28:24
	 * @param size
	 * @param category
	 * @param version
	 * @param keyWord
	 * @return
	 */
	List<LaunWidgetVo> findWidgetsPullListByAd(@Param("defaultSize") String defaultSize, @Param("category") Long category,
			@Param("version") Integer version, @Param("keyWord") String keyWord,@Param("channels")String channels,@Param("channelnum")Integer channelnum);
	
	/**
	 * @description:渠道查询widget列表
	 * @since:2018年4月26日 下午12:28:24
	 * @param size
	 * @param category
	 * @param version
	 * @param keyWord
	 * @return
	 */
	List<LaunWidgetVo> findWidgetsPullListByCh(@Param("defaultSize") String defaultSize, @Param("category") Long category,
			@Param("version") Integer version, @Param("keyWord") String keyWord,@Param("creator")Long creator,@Param("channelId")Long channelId);
	
	/**
	 * @description:管理员查询widget列表
	 * @since:2018年4月26日 下午12:28:24
	 * @param size
	 * @param category
	 * @param version
	 * @param keyWord
	 * @return
	 */
	List<LaunWidgetVo> findWidgetsListByAd(@Param("defaultSize") String defaultSize, @Param("category") Long category, @Param("version") Integer version, @Param("keyWord") String keyWord);
	
	/**
	 * @description:渠道查询widget列表
	 * @since:2018年4月26日 下午12:28:24
	 * @param size
	 * @param category
	 * @param version
	 * @param keyWord
	 * @return
	 */
	List<LaunWidgetVo> findWidgetsListByCh(@Param("defaultSize") String defaultSize, @Param("category") Long category,
			@Param("version") Integer version, @Param("keyWord") String keyWord,@Param("creator")Long creator,@Param("channelId")Long channelId);


	/**
	 * @description:查询widget类型列表
	 * @since:2018年4月27日 下午7:28:29
	 * @return
	 */
	List<LaunWidgetTypeVo> findWidgetTypeList();

	/**
	 * @description:根据主键删除widget类型
	 * @since:2018年4月27日 下午9:28:24
	 * @param id
	 * @return
	 */
	int deleteWidgetType(@Param("id") Long id);


	/**
	 * @description:根据主键删除widget
	 * @since:2018年4月28日 下午2:26:45
	 * @param id
	 * @return
	 */
	int deleteWidgetById(@Param("id") Long id);

	/**
	 * @description:查询widget尺寸列表
	 * @since:2018年4月28日 下午3:5:29
	 * @return
	 */
	List<String> findWidgetDefaultSize();

	/**
	 * @description:查询widget类型列表
	 * @since:2018年4月28日 下午4:6:35
	 * @return
	 */
	List<LaunWidgetType> findWidgetCategory();

	/**
	 * @description:查询widget版本列表
	 * @since:2018年4月28日 下午5:1:22
	 * @return
	 */
	List<LaunAttributeVo> findWidgetVersion();
	
	
	/**
	 * 删除自定义widget所包含的widget的图片
	 * @param widgetId
	 */
	void deletewidgetfile(@Param("widgetId")Long widgetId);
	
	
	/**
	 * 删除widget封面
	 * @param widgetId
	 */
	void deletewidgetcover(@Param("widgetId")Long widgetId);

	/**
	 * 查询widget在主题内使用情况
	 * @author LL
	 * @date 2018年5月18日 下午4:51:25
	 * @param widgetId主键id
	 * @return List<String>
	 */
	List<String> findWidgetUseNum(Long widgetId);

}
