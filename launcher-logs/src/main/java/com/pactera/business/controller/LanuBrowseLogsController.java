package com.pactera.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.business.service.LanuBrowseLogsService;
import com.pactera.domain.LaunBrowseLogs;
import com.pactera.utlis.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "日志")
public class LanuBrowseLogsController {
	
	@Autowired
	private LanuBrowseLogsService storeBrowseLogsService;

	@PostMapping("addStoreBrowseLogs")
	@ApiOperation(value = "添加操作日志", notes = "添加操作日志")
	public ResponseEntity<Void> addStoreBrowseLogs(LaunBrowseLogs storeBrowseLogs,String date) {
		storeBrowseLogs.setCreateDate(TimeUtils.string2Date(date));
		storeBrowseLogsService.addStoreBrowseLogs(storeBrowseLogs);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("findStoreBrowseLogs")
	@ApiOperation(value = "查询操作日志", notes = "查询操作日志")
    @ApiImplicitParams({ 
	    @ApiImplicitParam(name = "userName", value = "用户名"), 
	    @ApiImplicitParam(name = "page", value = "当前页"),
	    @ApiImplicitParam(name = "pageSize", value = "每页显示的数据条数") })
	public ResponseEntity<Page<LaunBrowseLogs>> findStoreBrowseLogs(
			@RequestParam(value="userName", defaultValue="") String userName,
			@RequestParam(value="channelId", defaultValue="") String channelId,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		Page<LaunBrowseLogs> list=storeBrowseLogsService.findStoreBrowseLogs(userName,channelId,page,pageSize);
		return ResponseEntity.ok(list);
	}
}
