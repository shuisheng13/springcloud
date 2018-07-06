package com.pactera.business.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunApplicationPostersService;
import com.pactera.business.service.LaunApplicationService;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.domain.LaunApplication;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.result.ResultData;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunApplicationPosterVo;
import com.pactera.vo.LaunApplicationVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description:应用相关
 * @author:Scott
 * @since:2018年4月26日 上午9:56:23
 */
@RestController
@Api(description = "应用管理")
@RequestMapping("application")
public class LaunApplicationController {

	@Autowired
	private LaunApplicationService launApplicationService;

	@Autowired
	private LaunApplicationPostersService launApplicationPostersService;

	@PostMapping("save")
	@ApiOperation("添加应用")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channelIds", value = "渠道id集合") })
	public ResponseEntity<ResultData> add(LaunApplication app, String channelIds) {
		Integer ret = launApplicationService.add(app, channelIds);
		return ResponseEntity.ok(new ResultData(ret, SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("upload")
	@ApiOperation("上传应用")
	public ResponseEntity<ResultData> upload(@RequestParam("file") MultipartFile file) {
		LaunApplicationVo app = launApplicationService.upload(file);
		return ResponseEntity.ok(new ResultData(app, SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@GetMapping("list")
	@ApiOperation("获取应用列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "keyWords", value = "应用名称或者ID") })
	public ResponseEntity<ResultData> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String channelId, String keyWords) {
		PageInfo<LaunApplicationVo> ret = launApplicationService.findByCondition(pageNum, pageSize, channelId, keyWords);
		return ResponseEntity.ok(new ResultData(ret,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@GetMapping("findPosterByAppId")
	@ApiOperation("根据应用id获取海报详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "应用ID") })
	public ResponseEntity<ResultData> findPosterByAppId(Long id) {
		Map<String, Object> ret = launApplicationService.findPosterByAppId(id);
		return ResponseEntity.ok(new ResultData(ret,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@GetMapping("findById")
	@ApiOperation("根据应用id获取应用详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "应用ID") })
	public ResponseEntity<ResultData> findById(Long id) {
		LaunApplicationVo app = launApplicationService.findById(id);
		return ResponseEntity.ok(new ResultData(app,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("update")
	@ApiOperation("更新应用信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channelIds", value = "渠道id集合") })
	public ResponseEntity<ResultData> update(LaunApplication application, String channelIds) {
		Integer ret = launApplicationService.update(application, channelIds);
		return ResponseEntity.ok(new ResultData(ret,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@GetMapping("select")
	@ApiOperation("查询海报")
	@ApiImplicitParams({ @ApiImplicitParam(name = "applicationId", value = "应用id") })
	public ResponseEntity<ResultData> select(Long applicationId) {
		List<LaunApplicationPosterVo> select = launApplicationPostersService.select(applicationId);
		return ResponseEntity.ok(new ResultData(select,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("uploadPoster")
	@ApiOperation("上传海报图片")
	public ResponseEntity<ResultData> uploadPoster(@RequestParam("file") MultipartFile file) {
		String posterPath = launApplicationService.InsertPoster(file);
		return ResponseEntity.ok(new ResultData(posterPath,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("savePoster")
	@ApiOperation("保存海报详情")
	public ResponseEntity<ResultData> savePoster(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime, Long applicationId, String posterListStr) {
		List<LaunApplicationPoster> posterList = JsonUtils.jsonToList(posterListStr, LaunApplicationPoster.class);
		Integer ret = launApplicationService.savePoster(startTime, endTime, applicationId, posterList);
		return ResponseEntity.ok(new ResultData(ret,SuccessStatus.OPERATION_SUCCESS.status(),
				SuccessStatus.OPERATION_SUCCESS.message()));
	}
}
