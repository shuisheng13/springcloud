package com.pactera.business.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.config.security.UserUtlis;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunUser;
import com.pactera.result.ResultData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 权限管理
 * @author:woqu
 * @since:2018年5月24日 下午4:35:08
 */
@RestController
@Api(description = "权限管理")
@RequestMapping("permissions")
public class LaunPermissionsController {

	@ApiOperation("查询当前用户的权限（不包含子权限）")
	@GetMapping("findPermissionsByUser")
	public ResponseEntity<ResultData> findPermissionsByUser() {
		LaunUser jsonToClass = UserUtlis.launUser();
		List<LaunPermissions> listPermissions = jsonToClass.getListPermissions();
		return ResponseEntity.ok(new ResultData(listPermissions));
	}

}
