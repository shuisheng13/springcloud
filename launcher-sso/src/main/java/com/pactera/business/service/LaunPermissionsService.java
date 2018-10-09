package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunUser;

public interface LaunPermissionsService {
	//查询用户所有的权限（不包括子权限）
	List<LaunPermissions> listPermissionsByUser(LaunUser user);
	//查询用户所有的权限
	String listPermissionsByUserAll(LaunUser user);
	//查询所有的权限
	List<LaunPermissions> findPermissionsAll();
	//更具用户id查询权限
	List<LaunPermissions> findPermissionsByUserId(Long id);
}
