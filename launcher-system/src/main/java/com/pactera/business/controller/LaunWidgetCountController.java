package com.pactera.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.dao.LaunWidgetStatisticsMapper;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.result.ResultData;
import com.pactera.utlis.ExportExcel;
import com.pactera.utlis.TimeUtils;

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
	 * @throws Exception 
	 */
	@GetMapping("export")
	public void export(String channelId,String version,String beginDate,String endDate,HttpServletResponse response) throws Exception{
		List<LaunWidgetStatistics> list = launWidgetStatisticsMapper.queryWidgetstartupnum(channelId, version, beginDate, endDate);
		String time = "";
		if(beginDate.equals(endDate)) {
			time = beginDate;
		}else {
			time = beginDate+"-"+endDate;
		}
		String title = "widget使用概览统计数据"+time;
		String rowName[] = {"序号","应用名称","点击次数","累计车辆"};
		List<Object[]>  dataList = new ArrayList<Object[]>(); 
		int i = 0;
		for (LaunWidgetStatistics poi : list) {
			Object[] obj = new Object[rowName.length];
			obj[0] = i;
			obj[1] = poi.getApplicationName();
			obj[2] = poi.getStartUpNum();
			obj[3] = poi.getCarNum();
			dataList.add(obj);
			i++;
		}
		ExportExcel excel = new ExportExcel(title, rowName, dataList, response);
		excel.export();
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
	
	/**
	 * 
	 * @param channelId渠道Id
	 * @param version版本
	 * @param beginDate开始时间
	 * @param endDate结束时间
	 * @param codeId
	 * @return
	 */
	@RequestMapping("queryBywidget")
	public ResponseEntity<ResultData> queryBywidget(String channelId,String version,String beginDate,String endDate,String codeId){
		List<LaunWidgetStatistics> list = launWidgetStatisticsMapper.queryBywidget(channelId, version, beginDate, endDate,codeId);
		return ResponseEntity.ok(new ResultData(list,SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message())); 
	}
	

}
