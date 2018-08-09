package com.pactera.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunChannelService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunUser;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunAuthChannelVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description:渠道管理
 * @author:woqu
 * @since:2018年5月24日 下午7:04:31
 */
@RestController
@RequestMapping("channel")
@Api(description = "渠道管理")
public class LaunChannelController {

	@Autowired
	private LaunChannelService launChannelService;

	@PostMapping("saveChannel")
	@ApiOperation("添加渠道或更新渠道")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channel", value = "渠道实体"),
			@ApiImplicitParam(name = "userId", value = "用户主键"),
			@ApiImplicitParam(name = "permissionids", value = "权限主键集合") })
	public ResponseEntity<ResultData> saveChannel(LaunChannel channel, Long userId, String[] permissionids) {
		Long id = channel.getId();
		Integer flag = Integer.parseInt(ConstantUtlis.FAILURE_SATE);
		if (id == null) {
			flag = launChannelService.saveChannel(channel, userId, permissionids);
		} else {
			flag = launChannelService.updateChannel(channel, userId, permissionids);
		}
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("删除渠道")
	@PostMapping("deleteChannel")
	@ApiImplicitParam(name = "id", value = "渠道id")
	public ResponseEntity<ResultData> deleteChannel(Long id) {
		Integer flag = launChannelService.deleteChannel(id);
		return ResponseEntity.ok(new ResultData(flag));
	}
	
	@ApiOperation("删除渠道提示")
	@PostMapping("deleteChannelCue")
	@ApiImplicitParam(name = "id", value = "渠道id")
	public ResponseEntity<ResultData> deleteChannelCue(Long id) {
		Integer flag = launChannelService.deleteChannelCue(id);
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("查询渠道列表")
	@PostMapping("findChannelList")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页默认为1"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示的数据量，默认10条") })
	public ResponseEntity<ResultData> findChannelList(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<LaunAuthChannelVo> list = launChannelService.findChannelList(pageNum, pageSize);
		PageInfo<LaunAuthChannelVo> pageInfo = new PageInfo<>(list);
		return ResponseEntity.ok(new ResultData(pageInfo));
	}

	@ApiOperation("根据主键查询渠道")
	@PostMapping("findChannelById")
	@ApiImplicitParam(name = "id", value = "渠道主键")
	public ResponseEntity<ResultData> findChannelById(Long id) {
		LaunChannel flag = launChannelService.findChannelById(id);
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("更新渠道")
	@PostMapping("updateChannel")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channel", value = "渠道实体"),
			@ApiImplicitParam(name = "userId", value = "用户主键"),
			@ApiImplicitParam(name = "permissionids", value = "权限主键集合") })
	public ResponseEntity<ResultData> updateChannel(LaunChannel channel, Long userId, String[] permissionids) {
		Integer flag = launChannelService.updateChannel(channel, userId, permissionids);
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("批量更新渠道权限")
	@PostMapping("updateChannelList")
	@ApiImplicitParam(name = "channelList", value = "渠道权限json集合")
	public ResponseEntity<ResultData> updateChannelList(String channelList) {
		Integer flag = launChannelService.updateChannelList(channelList);
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("查询重复渠道")
	@PostMapping("checkRepeatChannel")
	@ApiImplicitParams({ @ApiImplicitParam(name = "channelName", value = "渠道名称"),
			@ApiImplicitParam(name = "channelId", value = "渠道id") })
	public ResponseEntity<ResultData> checkRepeatChannel(String channelName, String channelId) {
		Integer flag = launChannelService.checkRepeatChannel(channelName, channelId);
		return ResponseEntity.ok(new ResultData(flag));
	}

	@ApiOperation("获取渠道id")
	@PostMapping("findChannelId")
	public ResponseEntity<ResultData> findChannelId() {
		String channelId = launChannelService.getChannelId();
		return ResponseEntity.ok(new ResultData(channelId));
	}

	@ApiOperation("查询所有的渠道")
	@PostMapping("findChannelAll")
	public ResponseEntity<ResultData> findChannelAll() {
		List<LaunChannel> list = launChannelService.findChannelAll();
		return ResponseEntity.ok(new ResultData(list));
	}

	@ApiOperation("根据渠道id查询用户")
	@PostMapping("findUserByChannelId")
	public ResponseEntity<ResultData> findUserByChannelId(Long channelId) {
		List<LaunUser> list = launChannelService.findUserByChannelId(channelId);
		return ResponseEntity.ok(new ResultData(list));
	}

}
