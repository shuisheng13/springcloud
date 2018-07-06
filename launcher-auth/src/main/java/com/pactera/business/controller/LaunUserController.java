package com.pactera.business.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.business.service.LaunUserService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunUser;
import com.pactera.result.ResultData;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunAuthFindUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 账号授权的controller
 * @author:woqu
 * @since:2018年5月24日 上午9:30:49
 */
@RestController
@RequestMapping("accountNumber")
@Api(description = "账号授权")
public class LaunUserController {

	@Autowired
	private LaunPermissionsService launPermissionsService;

	@Autowired
	private LaunUserService launUserService;

	@GetMapping("findUserByLike")
	@ApiOperation("模糊查询用户")
	@ApiImplicitParam(name = "userName", value = "用户名")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户名"),
			@ApiImplicitParam(name = "type", value = "1-查询没有渠道的，2，查询当前渠道和没有渠道的"),
			@ApiImplicitParam(name = "channelId", value = "渠道主键") })
	public ResponseEntity<ResultData> findUserByLike(String userName, String type, String channelId) {
		List<LaunUser> listUser = launUserService.findUserByLike(userName, type, channelId);
		List<LaunAuthFindUserVo> list = new ArrayList<>();
		for (LaunUser launUser : listUser) {
			LaunAuthFindUserVo authFindUser = new LaunAuthFindUserVo();
			authFindUser.setId(launUser.getId());
			authFindUser.setUserName(launUser.getUserName());
			list.add(authFindUser);
		}
		return ResponseEntity.ok(new ResultData(list));
	}

	@GetMapping("accountList")
	@ApiOperation("列表展示")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "第几页"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> accountList(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		PageInfo<LaunAuthFindUserVo> userList = launUserService.accountList(pageNum, pageSize);
		return ResponseEntity.ok(new ResultData(userList));
	}

	@PostMapping("saveAccount")
	@ApiOperation("账号添加/编辑账号")
	public ResponseEntity<ResultData> saveAccount(String str, String type) {
		if (HStringUtlis.isNoneBlank(str) && str.length() > 0) {
			Map<?, ?> jsonToMap = JsonUtils.JsonToMap(str);
			LaunUser user = JsonUtils.jsonToClass(JsonUtils.ObjectToJson(jsonToMap.get("user")), LaunUser.class);
			String[] permissions = JsonUtils.jsonToClass(JsonUtils.ObjectToJson(jsonToMap.get("permissions")),
					String[].class);
			if (user != null) {
				if (user.getChannelId() == ConstantUtlis.RETURN_NUMBER) {
					user.setChannelId(null);
					user.setUserType(ConstantUtlis.DOWN_SHELF);
				}
				if (HStringUtlis.isNotBlank(type) && type.length() > 0
						&& type.equalsIgnoreCase(ConstantUtlis.SUCCESS_SATE)) {
					user.setCreateDate(new Date());
				}
				user.setUpdateDate(new Date());
				launUserService.updateByUserSelect(user);
				Integer integer = launPermissionsService.updatePermissionsByUserId(user.getId(), permissions);
				return ResponseEntity.ok(new ResultData(integer));
			} else {
				return ResponseEntity.ok(new ResultData(ConstantUtlis.FAILURE_SATE));
			}
		} else {
			return ResponseEntity.ok(new ResultData(ConstantUtlis.FAILURE_SATE));
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("updateAccount")
	@ApiOperation("账号批量更新")
	public ResponseEntity<ResultData> updateAccount(String str) {
		if (HStringUtlis.isNoneBlank(str) && str.length() > 0) {
			List<Map> jsonToList = JsonUtils.jsonToList(str, Map.class);
			if (jsonToList != null && jsonToList.size() > 0) {
				Integer integer = null;
				for (Map map : jsonToList) {
					LaunUser user = JsonUtils.jsonToClass(JsonUtils.ObjectToJson(map.get("user")), LaunUser.class);
					String[] permissions = JsonUtils.jsonToClass(JsonUtils.ObjectToJson(map.get("permissions")),
							String[].class);
					if (user != null) {
						integer = launPermissionsService.updatePermissionsByUserId(user.getId(), permissions);
					}
				}
				return ResponseEntity.ok(new ResultData(integer));
			} else {
				return ResponseEntity.ok(new ResultData(ConstantUtlis.FAILURE_SATE));
			}
		} else {
			return ResponseEntity.ok(new ResultData(ConstantUtlis.FAILURE_SATE));
		}
	}

	@PostMapping("selectAccountList")
	@ApiOperation("账号授权的渠道列表")
	public ResponseEntity<ResultData> selectAccountList(Long id) {
		Map<String, Object> map = launUserService.selectAccountList(id);
		return ResponseEntity.ok(new ResultData(map));
	}

	@PostMapping("deleteById")
	@ApiOperation("根据id去删除权限")
	public ResponseEntity<ResultData> deleteById(Long id) {
		LaunUser user = launUserService.selectById(id);
		Integer userType = user.getUserType();
		if (userType != null && userType == Integer.parseInt(ConstantUtlis.USER_TYPE_ONE)) {
			return ResponseEntity.ok(new ResultData(ConstantUtlis.NOT_DELETE_CHANNEL));
		}
		user.setChannelId(null);
		user.setUserType(null);
		launUserService.updateByUser(user);
		Integer integer = launPermissionsService.deletePermissionsByUserId(id);
		return ResponseEntity.ok(new ResultData(integer));
	}

	@PostMapping("selectById")
	@ApiOperation("根据id去查看修改人的权限")
	@ApiImplicitParam(name = "id", value = "账号的id")
	public ResponseEntity<ResultData> selectById(Long id) {
		LaunAuthFindUserVo user = launUserService.selectByIdVo(id);
		return ResponseEntity.ok(new ResultData(user));
	}

	// @PostMapping("selectChannelByLogin")
	// @ApiOperation("获取当前登录人的渠道")
	// public ResponseEntity<ResultData> selectChannelByLogin() {
	// LaunUser launUser = UserUtlis.launUser();
	// LaunChannel channel =
	// launUserService.selectChannelByLogin(launUser.getChannelId());
	// return ResponseEntity.ok(new ResultData(channel));
	// }

}
