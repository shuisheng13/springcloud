package com.pactera.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunApplication;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.vo.LaunApplicationVo;

/**
 * @description:应用
 * @author:Scott
 * @since:2018年4月26日 下午2:16:00
 */
public interface LaunApplicationService {

	/**
	 * 新增应用
	 * 
	 * @param app
	 * @param channelIds
	 * @return
	 */
	Integer add(LaunApplication app, String channelIds);

	/**
	 * 上传应用
	 * 
	 * @param file
	 * @return
	 */
	LaunApplicationVo upload(MultipartFile file);

	/**
	 * 获取应用列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param channelId
	 * @param keyword
	 * @return
	 */
	PageInfo<LaunApplicationVo> findByCondition(Integer pageNum, Integer pageSize, String channelId, String keyword);

	/**
	 * 根据应用id获取海报详情
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> findPosterByAppId(Long id);

	/**
	 * 上传海报
	 * 
	 * @param file
	 * @return
	 */
	String InsertPoster(MultipartFile file);

	/**
	 * 保存海报
	 * 
	 * @param startTime
	 * @param endTime
	 * @param applicationId
	 * @param posterList
	 * @return
	 */
	Integer savePoster(Date startTime, Date endTime, Long applicationId, List<LaunApplicationPoster> posterList);

	/**
	 * 根据应用id获取应用详情
	 * @param id
	 * @return
	 */
	LaunApplicationVo findById(Long id);

	/**
	 * 更新应用信息
	 * @param application
	 * @param channelIds
	 * @return
	 */
	Integer update(LaunApplication application, String channelIds);
}
