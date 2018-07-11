package com.pactera.business.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunStatisticsService;
import com.pactera.domain.LaunApplicationStatistics;
import com.pactera.domain.LaunCarStatistics;
import com.pactera.domain.LaunWidgetStatistics;
import com.pactera.result.ResultData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "统计管理")
@RequestMapping("statistics")

public class LaunStatisticsController {

	@Autowired
	private LaunStatisticsService launStatisticsService;

	@GetMapping("findWisdgetStatic")
	@ApiOperation("widget使用情况-图表展示")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "channelId", value = "渠道id"),
			@ApiImplicitParam(name = "version", value = "版本下标") })
	public ResponseEntity<ResultData> findWisdgetStatic(Long startTime, Long endTime, String channelId,
			String version) {
		Map<String, Object> findWisdgetStatic = launStatisticsService.findWisdgetStatic(startTime, endTime, channelId,
				version);
		return ResponseEntity.ok(new ResultData(findWisdgetStatic));
	}

	@GetMapping("findOverViewStatic")
	@ApiOperation("widget使用情况-概览列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示的条数"),
			@ApiImplicitParam(name = "startTime", value = "开始时间"), @ApiImplicitParam(name = "endTime", value = "结束时间"),
			@ApiImplicitParam(name = "channelId", value = "渠道id"),
			@ApiImplicitParam(name = "version", value = "版本下标") })
	public ResponseEntity<ResultData> findOverViewStatic(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, Long startTime, Long endTime, String channelId,
			String version) {
		PageInfo<LaunWidgetStatistics> overViewList = launStatisticsService.findOverViewStatic(pageNum, pageSize,
				startTime, endTime, channelId, version);
		return ResponseEntity.ok(new ResultData(overViewList));
	}

	@GetMapping("findApplicationStatic")
	@ApiOperation("应用分布-图表，概览列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "channelId", value = "渠道id"),
			@ApiImplicitParam(name = "version", value = "版本下标") })
	public ResponseEntity<ResultData> findApplicationStatic(Long startTime, Long endTime, String channelId,
			String version) {
		Map<String, Object> overViewMap = launStatisticsService.findApplicationStatic(startTime, endTime, channelId,
				version);
		return ResponseEntity.ok(new ResultData(overViewMap));
	}

	@GetMapping("findAppDetailStatic")
	@ApiOperation("应用分布-详情列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示的条数"),
			@ApiImplicitParam(name = "startTime", value = "开始时间"), @ApiImplicitParam(name = "endTime", value = "结束时间"),
			@ApiImplicitParam(name = "channelId", value = "渠道id"),
			@ApiImplicitParam(name = "version", value = "版本下标") })
	public ResponseEntity<ResultData> findAppDetailStatic(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, Long startTime, Long endTime, String channelId,
			String version) {
		PageInfo<LaunApplicationStatistics> detailList = launStatisticsService.findAppDetailStatic(pageNum, pageSize,
				startTime, endTime, channelId, version);
		return ResponseEntity.ok(new ResultData(detailList));
	}

	// @GetMapping("findWisdgetDetailStatic")
	// @ApiOperation("widget使用情况之详情")
	// @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页"),
	// @ApiImplicitParam(name = "pageSize", value = "每页显示的条数"),
	// @ApiImplicitParam(name = "startTime", value = "开始时间"),
	// @ApiImplicitParam(name = "endTime", value = "结束时间"),
	// @ApiImplicitParam(name = "channelId", value = "渠道id") })
	// public ResponseEntity<ResultData>
	// findWisdgetDetailStatic(@RequestParam(defaultValue = "1") Integer
	// pageNum,
	// @RequestParam(defaultValue = "10") Integer pageSize, Long startTime, Long
	// endTime, String channelId) {
	// PageInfo<LaunWidgetStatistics> pageInfo =
	// launStatisticsService.findWisdgetDetailStatic(pageNum, pageSize,
	// startTime, endTime, channelId);
	// return ResponseEntity.ok(new ResultData(pageInfo));
	// }

	@GetMapping("getVersion")
	@ApiOperation("查询所有的版本")
	public ResponseEntity<ResultData> getVersion() {
		List<String> versions = launStatisticsService.getVersion();
		return ResponseEntity.ok(new ResultData(versions));
	}

	@GetMapping("carStatistics")
	@ApiOperation("根据条件去查询车辆统计")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "version", value = "版本Id"),
			@ApiImplicitParam(name = "channel", value = "渠道ID"),
			@ApiImplicitParam(name = "type", value = "类型(1:新增车辆2:活跃车辆3:启动次数4:平均单次使用时长)"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> selectCarStatistics(Long startTime, Long endTime, Long type, String version,
			Long channel) {
		Map<String, Object> map = launStatisticsService.selectCarStatistics(startTime, endTime, channel, version, type);
		return ResponseEntity.ok(new ResultData(map));
	}

	@GetMapping("carListStatistics")
	@ApiOperation("列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "version", value = "版本Id"),
			@ApiImplicitParam(name = "channel", value = "渠道ID"),
			@ApiImplicitParam(name = "type", value = "类型(0:新增车辆1:活跃车辆2:启动次数3:平均单次使用时长)"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> selectListCarStatistics(Long startTime, Long endTime, Long type, Long version,
			Long channel, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) {
		PageInfo<LaunCarStatistics> pageInfo = launStatisticsService.selectCarListStatistics(startTime, endTime,
				channel, version, type, pageNum, pageSize);
		return ResponseEntity.ok(new ResultData(pageInfo));

	}

	@GetMapping("themeStatistics")
	@ApiOperation("主题相关的统计")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "version", value = "版本Id"),
			@ApiImplicitParam(name = "channel", value = "渠道ID"),
			@ApiImplicitParam(name = "type", value = "类型(0:使用次数1:平均单次使用时长2:累计车辆)"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })

	public ResponseEntity<ResultData> selectThemeStatistics(Long startTime, Long endTime, Long type, Long version,
			Long channel, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) {

		Map<String, Object> map = launStatisticsService.selectThemeStatistics(startTime, endTime, channel, version,
				type);

		return ResponseEntity.ok(new ResultData(map));
	}

	/**
	 * 版本相关的统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月4日 下午3:03:46
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("versionStatistics")
	@ApiOperation("版本相关的统计")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"),
			@ApiImplicitParam(name = "type", value = "类型(1:新增车辆2:活跃车辆3:启动次数4:升级车辆5:累计车辆)"),
			@ApiImplicitParam(name = "channel", value = "渠道ID"), @ApiImplicitParam(name = "versions", value = "版本"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> versionStatistics(Long startTime, Long endTime, Long type, String versions,
			Long channel) {
		Map<String, Object> map = launStatisticsService.versionStatistics(startTime, endTime, type, channel, versions);
		return ResponseEntity.ok(new ResultData(map));
	}

	/**
	 * 版本详情统计
	 * 
	 * @description
	 * @author dw
	 * @since 2018年7月9日 下午3:48:05
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/xiangqing")
	@ApiOperation("明细数据版本")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> versionXiang(Long startTime, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) {
		PageInfo<LaunCarStatistics> pageInfo = launStatisticsService.versionXiang(startTime, pageNum, pageSize);
		return ResponseEntity.ok(new ResultData(pageInfo));
	}

	@PostMapping("/versionTrend")
	@ApiOperation("版本趋势概况")
	@ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间"),
			@ApiImplicitParam(name = "endTime", value = "结束时间"), @ApiImplicitParam(name = "channel", value = "渠道ID"),
			@ApiImplicitParam(name = "versions", value = "版本") })
	public ResponseEntity<ResultData> versionTrend(Long startTime, Long endTime, String versions, Long channel) {
		List<LaunCarStatistics> list = launStatisticsService.versionTrend(startTime, endTime, versions, channel);
		return ResponseEntity.ok(new ResultData(list));
	}

}
