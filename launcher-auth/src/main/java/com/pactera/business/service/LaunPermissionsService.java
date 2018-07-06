package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunUser;

/**
 * @description: 权限的接口
 * @author:woqu
 * @since:2018年5月24日 下午4:36:28
 */
public interface LaunPermissionsService {
	/**
	 * 根据用户id删除权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:36:38
	 * @param id
	 * @return Integer
	 */
	Integer deletePermissionsByUserId(Long id);

	/**
	 * 根据渠道id删除权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:38:09
	 * @param id
	 * @return Integer
	 */
	Integer deletePermissionsByChannelId(Long id);

	/**
	 * 根据用户id更新权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:38:21
	 * @param id
	 * @param permissions
	 * @return Integer
	 */
	Integer updatePermissionsByUserId(Long id, String[] permissions);

	/**
	 * 根据渠道id更新权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:38:38
	 * @param id
	 * @param permissions
	 * @return Integer
	 */
	Integer updatePermissionsByChannelId(Long id, String[] permissions);

	/**
	 * 用户添加权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:38:54
	 * @param id
	 * @param permissions
	 * @return Integer
	 */
	Integer savePermissionsByUserId(Long id, String[] permissions);

	//
	/**
	 * 渠道添加权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:39:12
	 * @param userId
	 * @param channelId
	 * @param permissions
	 * @return Integer
	 */
	Integer savePermissionsByChannelId(Long userId, Long channelId, String[] permissions);

	//
	/**
	 * 根据用户id查询权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:39:31
	 * @param id
	 * @return List<LaunPermissions>
	 */
	List<LaunPermissions> findPermissionsByUserId(Long id);

	//
	/**
	 * 根据渠道id查询权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:39:41
	 * @param id
	 * @return List<LaunPermissions>
	 */
	List<LaunPermissions> findPermissionsByChannelId(Long id);

	/**
	 * 更新权限
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午4:39:51
	 * @param user
	 * @param permissions
	 * @return Integer
	 */
	Integer updatUserById(LaunUser user, String[] permissions);

}
