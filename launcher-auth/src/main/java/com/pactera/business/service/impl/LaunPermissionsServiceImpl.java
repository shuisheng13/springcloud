package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.dao.LaunPermissionsMapper;
import com.pactera.business.dao.LaunRoleMapper;
import com.pactera.business.dao.LaunRolePermissionsMapper;
import com.pactera.business.dao.LaunUserMapper;
import com.pactera.business.dao.LaunUserRoleMapper;
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.security.UserUtlis;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunRole;
import com.pactera.domain.LaunRolePermissions;
import com.pactera.domain.LaunUser;
import com.pactera.domain.LaunUserRole;
import com.pactera.utlis.IdUtlis;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @description:权限
 * @author:woqu
 * @since:2018年5月24日 下午4:40:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LaunPermissionsServiceImpl implements LaunPermissionsService {

	@Autowired
	private LaunUserMapper launUserMapper;
	@Autowired
	private LaunRoleMapper launRoleMapper;
	@Autowired
	private LaunUserRoleMapper launUserRoleMapper;
	@Autowired
	private LaunPermissionsMapper launPermissionsMapper;
	@Autowired
	private LaunRolePermissionsMapper launRolePermissionsMapper;

	@Override
	public Integer deletePermissionsByUserId(Long id) {
		try {
			Example example = new Example(LaunUserRole.class);
			example.createCriteria().andEqualTo("userId", id);
			List<LaunUserRole> selectByExample = launUserRoleMapper.selectByExample(example);
			if (selectByExample != null && selectByExample.size() > 0) {
				for (LaunUserRole launUserRole : selectByExample) {
					// 删除角色
					launRoleMapper.deleteByPrimaryKey(launUserRole.getRoleId());
					// 删除用户和角色的关联
					Example example2 = new Example(LaunUserRole.class);
					example2.createCriteria().andEqualTo("roleId", launUserRole.getRoleId());
					launUserRoleMapper.deleteByExample(example2);
					// 删除角色和权限的关联
					Example example3 = new Example(LaunRolePermissions.class);
					example3.createCriteria().andEqualTo("roleId", launUserRole.getRoleId());
					launRolePermissionsMapper.deleteByExample(example3);
				}
			}
		} catch (Exception e) {
			throw new DataStoreException(ErrorStatus.ROLE_DELETE);
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer deletePermissionsByChannelId(Long id) {
		// 查询渠道下面所有的用户
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", id);
		List<LaunUser> listUser = launUserMapper.selectByExample(example);
		if (listUser != null && listUser.size() > 0) {
			for (LaunUser launUser : listUser) {
				deletePermissionsByUserId(launUser.getId());
			}
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer updatePermissionsByUserId(Long id, String[] permissions) {
		Example example = new Example(LaunUserRole.class);
		example.createCriteria().andEqualTo("userId", id);
		List<LaunUserRole> listRoles = launUserRoleMapper.selectByExample(example);
		if (listRoles != null && listRoles.size() > 0) {
			for (LaunUserRole launUserRole : listRoles) {
				// 删除角色和权限的关联
				Long roleId = launUserRole.getRoleId();
				Example example3 = new Example(LaunRolePermissions.class);
				example3.createCriteria().andEqualTo("roleId", roleId);
				launRolePermissionsMapper.deleteByExample(example3);
				if (permissions != null && permissions.length > 0) {
					for (String permission : permissions) {
						LaunRolePermissions launRolePermissions = new LaunRolePermissions();
						launRolePermissions.setId(IdUtlis.Id());
						launRolePermissions.setRoleId(roleId);
						launRolePermissions.setPermissionsId(new Long(permission));
						launRolePermissionsMapper.insertSelective(launRolePermissions);
					}
				}

			}
		} else {
			savePermissionsByUserId(id, permissions);
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer updatePermissionsByChannelId(Long id, String[] permissions) {
		// 查询渠道的管理员
		Example example = new Example(LaunUser.class);
		example.createCriteria().andEqualTo("channelId", id).andEqualTo("userType", 1);
		List<LaunUser> listUser = launUserMapper.selectByExample(example);
		if (listUser != null && listUser.size() > 0) {
			for (LaunUser launUser : listUser) {
				updatePermissionsByUserId(launUser.getId(), permissions);
			}
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer savePermissionsByUserId(Long id, String[] permissions) {
		deletePermissionsByUserId(id);
		Example example = new Example(LaunUserRole.class);
		example.createCriteria().andEqualTo("userId", id);
		List<LaunUserRole> listRoles = launUserRoleMapper.selectByExample(example);
		if (listRoles != null && listRoles.size() > 0) {
			deletePermissionsByUserId(id);
		}
		// 创建角色
		LaunRole launRole = new LaunRole();
		Long roleId = IdUtlis.Id();
		launRole.setId(roleId);
		launRole.setName(String.valueOf(id));
		launRoleMapper.insertSelective(launRole);
		// 创建用户和角色的关联表
		LaunUserRole launUserRole = new LaunUserRole();
		launUserRole.setId(IdUtlis.Id());
		launUserRole.setUserId(id);
		launUserRole.setRoleId(roleId);
		launUserRoleMapper.insertSelective(launUserRole);
		// 创建角色和权限的关联表
		if (permissions != null && permissions.length > 0) {
			for (String permission : permissions) {
				LaunRolePermissions launRolePermissions = new LaunRolePermissions();
				launRolePermissions.setId(IdUtlis.Id());
				launRolePermissions.setRoleId(roleId);
				launRolePermissions.setPermissionsId(new Long(permission));
				launRolePermissionsMapper.insertSelective(launRolePermissions);
			}
		}
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public Integer savePermissionsByChannelId(Long userId, Long channelId, String[] permissions) {
		savePermissionsByUserId(userId, permissions);
		LaunUser launUser = new LaunUser();
		launUser.setId(userId);
		launUser.setChannelId(channelId);
		launUser.setUserType(1);
		launUserMapper.updateByPrimaryKeySelective(launUser);
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public List<LaunPermissions> findPermissionsByUserId(Long id) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>(16);
		hashMap.put("id", id);
		// 获取用户的权限
		List<LaunPermissions> findPermissionsByUserId = launPermissionsMapper.findPermissionsByUserId(hashMap);
		// 获取当前登录所有权限
		LaunUser user = UserUtlis.launUser();
		List<LaunPermissions> findPermissionsAll = user.getListPermissions();
		if (findPermissionsByUserId != null && findPermissionsByUserId.size() > 0) {
			// 一级
			for (LaunPermissions launPermissions : findPermissionsAll) {
				// 二级
				List<LaunPermissions> listPermissions = launPermissions.getListPermissions();
				for (LaunPermissions launPermissions2 : listPermissions) {
					for (LaunPermissions launPermissions3 : findPermissionsByUserId) {
						Long resources = launPermissions2.getId();
						Long resources2 = launPermissions3.getId();
						if (String.valueOf(resources).equalsIgnoreCase(String.valueOf(resources2))) {
							launPermissions2.setChooseType("1");
						}
					}
				}
			}
		}
		return findPermissionsAll;
	}

	@Override
	public List<LaunPermissions> findPermissionsByChannelId(Long id) {
		List<LaunPermissions> listermissions = new ArrayList<LaunPermissions>();
		Example example = new Example(LaunUser.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("userType", 1);
		createCriteria.andEqualTo("channelId", id);
		List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			LaunUser launUser = selectByExample.get(0);
			listermissions = findPermissionsByUserId(launUser.getId());
		}
		return listermissions;
	}

	@Override
	public Integer updatUserById(LaunUser user, String[] permissions) {
		LaunUser exampleUser = new LaunUser();
		exampleUser.setChannelId(user.getChannelId());
		exampleUser.setId(user.getId());
		exampleUser.setRemarks(user.getRemarks());
		launUserMapper.updateByExampleSelective(user, exampleUser);

		Example example = new Example(LaunUserRole.class);
		example.createCriteria().andEqualTo("userId", user.getId());
		List<LaunUserRole> listRoles = launUserRoleMapper.selectByExample(example);
		if (listRoles != null && listRoles.size() > 0) {
			for (LaunUserRole launUserRole : listRoles) {
				// 删除角色和权限的关联
				Long roleId = launUserRole.getRoleId();
				Example example3 = new Example(LaunRolePermissions.class);
				example3.createCriteria().andEqualTo("roleId", roleId);
				launRolePermissionsMapper.deleteByExample(example3);
				if (permissions != null && permissions.length > 0) {
					for (String permission : permissions) {
						LaunRolePermissions launRolePermissions = new LaunRolePermissions();
						launRolePermissions.setId(IdUtlis.Id());
						launRolePermissions.setRoleId(roleId);
						launRolePermissions.setPermissionsId(new Long(permission));
						launRolePermissionsMapper.insertSelective(launRolePermissions);
					}
				}

			}
		}
		return ConstantUtlis.SUCCESS;

	}
}
