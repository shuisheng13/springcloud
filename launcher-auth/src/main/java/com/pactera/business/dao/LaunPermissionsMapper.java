package com.pactera.business.dao;

import java.util.List;
import java.util.Map;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunPermissions;

/**
 * @description: 权限mapper
 * @author:woqu
 * @since:2018年5月24日 下午7:18:25
 */
public interface LaunPermissionsMapper extends BaseMapper<LaunPermissions> {

	/**
	 * 权限
	 * 
	 * @description
	 * @author liudawei
	 * @since 2018年5月24日 下午7:18:32
	 * @param map
	 * @return List<LaunPermissions>
	 */
	List<LaunPermissions> findPermissionsByUserId(Map<String, Object> map);
}
