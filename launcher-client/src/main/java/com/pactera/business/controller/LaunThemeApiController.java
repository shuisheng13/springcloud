package com.pactera.business.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.service.LaunClientThemeService;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunThemeShopVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 车机请求API
 * 
 * @description:
 * @author:LL
 * @since:2018年6月1日 下午7:44:50
 */
@RestController
@Api(description = "主题管理")
@RequestMapping("themeApi")
public class LaunThemeApiController {

	@Autowired
	private LaunClientThemeService launClientThemeService;

	@GetMapping("pushTheme")
	@ApiOperation("获取强推主题")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channle", value = "渠道名称") })
	public ResponseEntity<ResultData> pushTheme(String channel, String version, Long screenHeight, Long screenWidth,
			String userId, String city, String posterIds) {
		Map<String, Object> posters = launClientThemeService.getPosters(channel, version, screenHeight, screenWidth,
				userId, city, posterIds);
		if (posters == null) {
			return ResponseEntity.ok(new ResultData(posters, SuccessStatus.THEMEAPI_NOTHEME));
		}

		return ResponseEntity.ok(new ResultData(posters));
	}

	@GetMapping("getThemeList")
	@ApiOperation("获取主题列表")
	public ResponseEntity<ResultData> getThemeList(String channel, String version, Long screenHeight, Long screenWidth,
			String userId, String city, Integer type) {
		List<LaunThemeShopVo> themeList = launClientThemeService.getThemeList(channel, version, screenHeight,
				screenWidth, userId, city, type);
		return ResponseEntity.ok(new ResultData(themeList));
	}

	@GetMapping("test")
	@ApiOperation("获取主题列表")
	public ResponseEntity<ResultData> test() {
		return ResponseEntity.ok(new ResultData("压力测试"));
	}

}
