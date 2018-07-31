package com.pactera.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.result.ResultData;

import io.swagger.annotations.Api;

/**
 * @date 2018年7月27日10:46:39
 * @author lzp
 * @description widget统计
 *
 */
@RestController
@RequestMapping("widget")
@Api(description = "widget统计")
public class LaunWidgetCountController {
	
	@Autowired
	private LaunWidgetStatisticsMapper launWidgetStatisticsMapper;
	
	
	/**
	 * 
	 * @param channelId 渠道Id
	 * @param version 版本
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	@GetMapping("queryWidgetGroup")
	public ResponseEntity<ResultData> queryWidgetGroup(String channelId,String version,String beginDate,String endDate){
		List<LaunWidgetStatistics> list = launWidgetStatisticsMapper.queryWidgetGroup(channelId, version, beginDate, endDate);
		return ResponseEntity.ok(new ResultData(list,SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message()));
	}
	
	/**
	 * 
	 * @param channelId 渠道Id
	 * @param version 版本
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	@GetMapping("queryWidgetstartupnum")
	public ResponseEntity<ResultData> queryWidgetstartupnum(String channelId,String version,String beginDate,String endDate){
		List<LaunWidgetStatistics> list = launWidgetStatisticsMapper.queryWidgetstartupnum(channelId, version, beginDate, endDate);
		return ResponseEntity.ok(new ResultData(list,SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message()));
	}
	
	public ResponseEntity<ResultData> queryBywidget(String channelId,String version,String beginDate,String endDate,String codeId){
		List<LaunWidgetStatistics> list = launWidgetStatisticsMapper.queryBywidget(channelId, version, beginDate, endDate,codeId);
		return ResponseEntity.ok(new ResultData(list,SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message())); 
	}
	

}
