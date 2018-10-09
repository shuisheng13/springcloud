package com.pactera.business.dao;

import java.util.List;
import java.util.Map;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunPermissions;

public interface LaunPermissionsMapper extends BaseMapper<LaunPermissions> {
	
	List<LaunPermissions> findPermissionsByUserId(Map<String,Object> map);

}
