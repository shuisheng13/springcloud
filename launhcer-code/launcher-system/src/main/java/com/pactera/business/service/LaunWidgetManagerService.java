package com.pactera.business.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunAttribute;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetChannel;
import com.pactera.domain.LaunWidgetFile;
import com.pactera.domain.LaunWidgetType;
import com.pactera.vo.LaunAttributeVo;
import com.pactera.vo.LaunWidgetTypeVo;
import com.pactera.vo.LaunWidgetVo;

/**
 * 
 * @description:Widget管理
 * @author:wangyaqun
 * @since:2018年4月26日 上午11:28:24
 */
public interface LaunWidgetManagerService {
	
	
	
	/**
	 * 新增自定义widget信息
	 * @param widgetjon 新增的widget的json
	 * @param groupjson  包含的widget的json
	 * @return
	 */
	String saveGroupWidget(String widgetjon,String groupjson);
	
	/**
	 * 自定义widget页面拖动的基础widget的所有信息
	 * @param widgetId 主键Id
	 * @return
	 */
	String getGoupWidgetById(Long widgetId);
	
	/**
	 * 创建变体widget
	 * @param widgetjson 属性json
	 * @param filepath widget图片路径
	 * @param channels 渠道数组
	 * @return
	 */
	int saveSingleWidget(String widgetjson,String filepath,String channels);	
	
	
	/**
	 * 修改变体widget
	 * @param widgetjson 属性json
	 * @param filepath widget图片路径
	 * @param channels 渠道数组
	 * @return
	 */
	int updateSingleWidget(String widgetjson,String filepath,String channels);	
	
	/**
	 * widget关联渠道添加
	 * @param widgetchannel
	 */
	void insertWidgetChannel(LaunWidgetChannel widgetchannel);
	
	/**
	 * 修改变体widget
	 * @param widget widget实体
	 * @return
	 */
	void insertWidget(LaunWidget widget);
	
	/**
	 * 根据widget主键查询详细信息
	 * @param widgetId
	 * @return widget实体
	 */
	LaunWidget findWidgetById(Long widgetId);
	
	
	/**
	 * 根据widget主键查询预览图
	 * @param widgetId
	 * @return
	 */
	String widgetPreview(Long widgetId);
	
	/**
	 * 根据widgetId获取该widget相关文件资源
	 * @param widgetId
	 * @return
	 */
	LaunWidgetFile findWidgetFileById(Long widgetId);
	
	/**
	 * 添加widget分类
	 * @author:wangyaqun
	 * @param typeName
	 * @return
	 */
	String insertMidgetType(String typeName);

	/**
	 * 上传基础widget包
	 * @author:wangyaqun
	 * @param file
	 * @return
	 */
	String fileUpload(MultipartFile file);
	
	/**
	 * 替换基础widget包
	 * @author:lizhipeng
	 * @param file
	 * @param widgetId 替换的widgetId
	 * @return
	 */
	String fileReplace(MultipartFile file,Long widgetId);

	/**
	 * 查询widget列表
	 * @author:wangyaqun
	 * @param pageNum 页数
	 * @param pageSize 
	 * @param defaultSize 大小
	 * @param category 分类
	 * @param version 版本
	 * @param keyWord 关键字
	 * @param type 管理员类型
	 * @return type
	 */
	PageInfo<LaunWidgetVo> findWidgetsList(Integer pageNum, Integer pageSize, String defaultSize, Long category,
			Integer version, String keyWord, Integer type);
	
	/**
	 * 查询widget列表
	 * @author:wangyaqun
	 * @param pageNum 页数
	 * @param pageSize 
	 * @param defaultSize 大小
	 * @param category 分类
	 * @param version 版本
	 * @param keyWord 关键字
	 * @param type 管理员类型
	 * @return type
	 */
	PageInfo<LaunWidgetVo> findWidgetsPullList(Integer pageNum, Integer pageSize, String defaultSize, Long category,
			Integer version, String keyWord, Integer type,String channels,Integer channelnum);

	/**
	 * 查询widget类型列表
	 * @author:wangyaqun
	 * @return
	 */
	PageInfo<LaunWidgetTypeVo> findWidgetTypeList();

	/**
	 * 根据主键删除widget类型
	 * @author:wangyaqun
	 * @param id
	 * @return
	 */
	String deleteWidgetType(Long id);

	/**
	 * 根据主键删除widget
	 * @author:wangyaqun
	 * @param id
	 * @return
	 */
	String deleteWidgetById(Long id);

	/**
	 * 查询所有widget尺寸列表
	 * @author:wangyaqun
	 * @return
	 */
	PageInfo<String> findWidgetDefaultSize();

	/**
	 * 查询所有widget类型列表
	 * @author:wangyaqun
	 * @return
	 */
	PageInfo<LaunWidgetType> findWidgetCategory();

	/**
	 * 查询所有widget版本列表
	 * @author:wangyaqun
	 * @return
	 */
	List<LaunAttributeVo> findWidgetVersion();

	/**
	 * 解析widget压缩包
	 * @param json文件
	 * @param imgpath 图片路径
	 * @param otherpath 其他目录
	 * @author:wangyaqun
	 * @return
	 */
	LaunWidget unJsonParsing(String json, String imgpath,String otherpath);
	
	/**
	 * 根据widget主键查询详细信息
	 * @param widgetId
	 * @return 基础和变体返回key为实体value为空的map，自定义返回key为实体，value为list的子widget
	 * @throws Exception 
	 */
	String getWidgetDetail(Long widgetId) throws Exception;
	
	
	/**
	 * 根据widgetId查询widget的文件信息
	 * @param widgetId
	 * @return
	 */
	List<LaunWidgetFile> findWidgetfileById(Long widgetId);
	
	
	/**
	 * 查询widget在主题内使用情况
	 * @author LL
	 * @date 2018年5月18日 下午4:51:25
	 * @param widgetId主键id
	 * @return String
	 */
	String findWidgetUseNum(Long widgetId);
	
	/**
	 * 根据widgetId获取渠道信息
	 * @author lizhipeng
	 * @date 2018年5月18日 下午4:51:25
	 * @param widgetId主键id
	 * @return 渠道集合
	 */
	List<LaunWidgetChannel> findWidgetChannelById(Long widgetId);

}
