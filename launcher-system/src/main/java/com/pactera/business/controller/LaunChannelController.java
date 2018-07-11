package com.pactera.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.service.LaunChannelService;
import com.pactera.domain.LaunChannel;
import com.pactera.result.ResultData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description:渠道管理相关
 * @author:LL
 * @since:2018年4月26日 下午11:39:36
 */
@RestController
@Api(description = "渠道管理")
@RequestMapping("channel")
public class LaunChannelController {

	@Autowired
	private LaunChannelService launChannelService;

	/**
	 * 根据name模糊查询渠道/无分页
	 * 
	 * @author LL
	 * @date 2018年4月27日 上午10:33:35
	 * @param name渠道名称
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("findAll")
	@ApiOperation("根据name模糊查询渠道/无分页")
	@ApiImplicitParam(name = "name", value = "渠道名称字段")
	public ResponseEntity<ResultData> findAll(String name) {
		List<LaunChannel> findAll = launChannelService.findAll(name);
		return ResponseEntity.ok(new ResultData(findAll));
	}

	/**
	 * 根据渠道id获取渠道信息
	 * @param id 主键
	 * @return
	 */
	@GetMapping("findOneById")
	@ApiOperation("根据id获取渠道详情")
	@ApiImplicitParam(name = "id", value = "渠道主键")
	public ResponseEntity<ResultData> findOneById(String id) {
		LaunChannel channel = launChannelService.findById(id);
		return ResponseEntity.ok(new ResultData(channel));
	}
}
