package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunAccountNumberMapper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunRoleMapper;
import com.pactera.business.dao.LaunRolePermissionsMapper;
import com.pactera.business.dao.LaunUserMapper;
import com.pactera.business.dao.LaunUserRoleMapper;
import com.pactera.business.service.LaunPermissionsService;
import com.pactera.business.service.LaunUserService;
import com.pactera.config.security.UserUtlis;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunPermissions;
import com.pactera.domain.LaunRole;
import com.pactera.domain.LaunRolePermissions;
import com.pactera.domain.LaunUser;
import com.pactera.domain.LaunUserRole;
import com.pactera.utlis.HStringUtlis;
import com.pactera.vo.LaunAuthChannelVo;
import com.pactera.vo.LaunAuthFindUserVo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @description: 账号授权的实现类
 * @author:woqu
 * @since:2018年5月24日 上午10:01:11
 */
@Service
public class LaunUserServiceImpl implements LaunUserService {

	@Autowired
	private LaunAccountNumberMapper accountNumberMapper;

	@Autowired
	private LaunChannelMapper channelMapper;

	@Autowired
	private LaunRoleMapper launRoleMapper;

	@Autowired
	private LaunUserRoleMapper launUserRoleMapper;

	@Autowired
	private LaunRolePermissionsMapper launRolePermissionsMapper;

	@Autowired
	private LaunPermissionsService launPermissionsService;

	@Autowired
	private LaunUserMapper launUserMapper;

	@Override
	public List<LaunUser> findUserByLike(String userName, String type, String channelId) {
		Example example = new Example(LaunUser.class);
		List<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(0);
		arrayList.add(2);
		// 查询没有渠道的id
		if (HStringUtlis.isNotBlank(type) && type.equalsIgnoreCase(ConstantUtlis.USER_TYPE_ONE)) {
			Criteria criteria1 = example.createCriteria();
			criteria1.andIsNull("channelId").andLike("userName", "%" + userName + "%").andNotIn("userType", arrayList);
			Criteria criteria2 = example.createCriteria();
			criteria2.andIsNull("channelId").andLike("userName", "%" + userName + "%").andIsNull("userType");
			example.or(criteria1);
			example.or(criteria2);
		}
		// 查询没有渠道和本渠道的id
		if (HStringUtlis.isNotBlank(type) && type.equalsIgnoreCase(ConstantUtlis.USER_TYPE_TWO)) {
			Criteria criteria1 = example.createCriteria();
			criteria1.andIsNull("channelId").andLike("userName", "%" + userName + "%").andNotIn("userType", arrayList);
			Criteria criteria2 = example.createCriteria();
			criteria2.andIsNull("channelId").andLike("userName", "%" + userName + "%").andIsNull("userType");

			if (HStringUtlis.isNotBlank(channelId) && channelId.length() > 0) {
				Long channelid = new Long(channelId);
				Criteria criteria3 = example.createCriteria();
				criteria3.andEqualTo("channelId", channelid).andEqualTo("userType", "1").andLike("userName",
						"%" + userName + "%");
				example.or(criteria3);
			}
			example.or(criteria1);
			example.or(criteria2);
		}
		List<LaunUser> selectByExample = launUserMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public Integer save(LaunUser launUser, List<Long> permissionIds) {
		// 更新用户，
		int i = accountNumberMapper.updateByPrimaryKeySelective(launUser);
		// 保存角色表
		LaunRole launRole = new LaunRole();
		launRole.setId(launUser.getId());
		launRole.setName(launRole.getName());
		launRoleMapper.insertSelective(launRole);

		// 保存角色和用户
		LaunUserRole launUserRole = new LaunUserRole();
		launUserRole.setUserId(launUser.getId());
		launUserRole.setRoleId(launRole.getId());
		launUserRoleMapper.insertSelective(launUserRole);
		// 保存角色和权限
		LaunRolePermissions launRolePermissions = new LaunRolePermissions();
		for (Long id : permissionIds) {
			launRolePermissions.setPermissionsId(id);
			launRolePermissions.setRoleId(launRole.getId());
			launRolePermissionsMapper.insertSelective(launRolePermissions);
		}

		return i;
	}

	/**
	 * @description 判断用户名是否重复
	 * @author liudawei
	 * @since 2018年5月11日 下午3:15:35
	 * @param
	 */
	@Override
	public LaunUser selectByUserName(String userName) {
		Example example = new Example(LaunUser.class);
		example.or().andEqualTo("userName", userName);
		List<LaunUser> list = accountNumberMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @description 查询渠道的
	 * @author liudawei
	 * @since 2018年5月11日 下午3:34:21
	 * @param
	 */
	@Override
	public Map<String, Object> selectAccountList(Long id) {
		Map<String, Object> map = new HashMap<>(16);
		// 用户类型：0-思维管理员，1-渠道管理员，2-普通用户") Integer userType;
		LaunUser user = new LaunUser();
		if (id != null) {
			user = launUserMapper.selectByPrimaryKey(id);
		} else {
			user = UserUtlis.launUser();
		}
		List<LaunAuthChannelVo> list = new ArrayList<>();

		Example example = new Example(LaunChannel.class);
		Integer userType = user.getUserType();
		Criteria createCriteria = example.createCriteria();
		if (userType != null && (userType == 0 || userType == 2)) {
			createCriteria.andNotEqualTo("channelStatus", 1);
		} else {
			Long channelId = user.getChannelId();
			createCriteria.andNotEqualTo("channelStatus", 1).andEqualTo("id", channelId);
		}
		List<LaunChannel> listChannel = channelMapper.selectByExample(example);

		if (userType != null && (userType == 0 || userType == 2)) {
			LaunAuthChannelVo channel = new LaunAuthChannelVo();
			channel.setId(0L);
			channel.setName("四维超级管理");
			list.add(channel);
			map.put("select", "四维超级管理");
		} else {
			for (LaunChannel launChannel : listChannel) {
				if (launChannel.getId().equals(user.getChannelId())) {
					map.put("select", launChannel.getName());
				}
			}
		}
		if (listChannel.size() > 0 && listChannel != null) {
			for (LaunChannel launChannel : listChannel) {
				LaunAuthChannelVo channelVo = new LaunAuthChannelVo();
				channelVo.setId(launChannel.getId());
				channelVo.setName(launChannel.getName());
				list.add(channelVo);
			}
		}
		map.put("list", list);
		return map;
	}

	@Override
	public Integer deleteById(Long id) {
		Example example = new Example(LaunRolePermissions.class);
		example.or().andEqualTo("roleId", id);
		int i = launRolePermissionsMapper.deleteByExample(example);
		return i;
	}

	/**
	 * @description 更新
	 * @author liudawei
	 * @since 2018年5月11日 下午5:07:48
	 * @param
	 */
	@Override
	public Integer update(LaunUser launUser, List<Long> permissionIds) {
		// 更新用户，
		int i = accountNumberMapper.updateByPrimaryKeySelective(launUser);
		Example example = new Example(LaunRolePermissions.class);
		example.or().andEqualTo("roleId", launUser.getId());
		launRolePermissionsMapper.deleteByExample(example);
		// 保存角色和权限
		LaunRolePermissions launRolePermissions = new LaunRolePermissions();
		for (Long id : permissionIds) {
			launRolePermissions.setPermissionsId(id);
			launRolePermissions.setRoleId(launUser.getId());
			launRolePermissionsMapper.insertSelective(launRolePermissions);
		}
		return i;
	}

	/**
	 * @description 列表
	 * @author liudawei
	 * @since 2018年5月11日 下午6:47:30
	 * @param
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<LaunAuthFindUserVo> accountList(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		LaunUser launUser = UserUtlis.launUser();
		Example example = new Example(LaunUser.class);
		Criteria createCriteria1 = example.createCriteria();
		if (launUser.getChannelId() != null) {
			// 渠道不为空，查询渠道下的
			createCriteria1.andEqualTo("channelId", launUser.getChannelId()).andNotEqualTo("id", launUser.getId());
		} else {
			// 渠道为空，并且usertype =0 或者 2查询全部
			createCriteria1.andNotEqualTo("id", launUser.getId()).andIsNotNull("channelId");
			example.or(createCriteria1);
			Criteria createCriteria2 = example.createCriteria();
			createCriteria2.andEqualTo("userType", 2).andNotEqualTo("id", launUser.getId());
			example.or(createCriteria2);
		}
		example.setOrderByClause("create_date desc");
		List<LaunUser> list = launUserMapper.selectByExample(example);
		@SuppressWarnings("rawtypes")
		PageInfo pageInfo = new PageInfo<>(list);
		List<LaunAuthFindUserVo> listVo = new ArrayList<>();
		for (LaunUser launUser2 : list) {
			LaunAuthFindUserVo launAuthFindUserVo = new LaunAuthFindUserVo();
			List<LaunPermissions> list2 = launPermissionsService.findPermissionsByUserId(launUser2.getId());
			launUser2.setListPermissions(list2);
			launAuthFindUserVo.setListPermissions(list2);
			launAuthFindUserVo.setId(launUser2.getId());
			launAuthFindUserVo.setUserName(launUser2.getUserName());
			listVo.add(launAuthFindUserVo);
		}
		pageInfo.setList(listVo);
		return pageInfo;
	}

	/**
	 * @description 根据id去查询
	 * @author liudawei
	 * @since 2018年5月11日 下午6:54:55
	 * @param
	 */
	@Override
	public LaunUser selectById(Long id) {
		LaunUser user = accountNumberMapper.selectByPrimaryKey(id);
		if (user.getChannelId() != null) {
			LaunChannel channel = channelMapper.selectByPrimaryKey(user.getChannelId());
			user.setChannelName(channel.getName());
		} else if (user.getChannelId() == null && user.getUserType() == ConstantUtlis.DOWN_SHELF) {
			user.setChannelId(0L);
			user.setChannelName("四维超级管理");
		} else if (user.getUserType() == ConstantUtlis.DOWN_SHELF) {
			user.setChannelId(0L);
			user.setChannelName("四维超级管理");
		}
		List<LaunPermissions> list = launPermissionsService.findPermissionsByUserId(id);
		user.setListPermissions(list);
		return user;
	}

	@Override
	public void updateByUser(LaunUser user) {
		Integer userType = user.getUserType();
		if (userType != null && userType == Integer.parseInt(ConstantUtlis.USER_TYPE_ONE)) {
			Example example = new Example(LaunChannel.class);
			example.createCriteria().andEqualTo("userId", user.getId());
			List<LaunChannel> selectByExample = channelMapper.selectByExample(example);
			if (selectByExample != null && selectByExample.size() > 0) {
				for (LaunChannel launChannel : selectByExample) {
					launChannel.setUserId(null);
					channelMapper.updateByPrimaryKey(launChannel);
				}
			}
		}
		launUserMapper.updateByPrimaryKey(user);
	}

	@Override
	public List<LaunUserRole> selectRoleById(Long id) {
		Example example = new Example(LaunUserRole.class);
		example.createCriteria().andEqualTo("userId", id);
		List<LaunUserRole> selectByExample = launUserRoleMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public LaunChannel selectChannelByLogin(Long channelId) {
		LaunChannel channel = channelMapper.selectByPrimaryKey(channelId);
		return channel;
	}

	@Override
	public void updateByUserSelect(LaunUser user) {
		launUserMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * @description 根据id去查询
	 * @author liudawei
	 * @since 2018年5月11日 下午6:54:55
	 * @param
	 */
	@Override
	public LaunAuthFindUserVo selectByIdVo(Long id) {
		LaunUser user = accountNumberMapper.selectByPrimaryKey(id);
		LaunAuthFindUserVo authFindUserVo = new LaunAuthFindUserVo();
		authFindUserVo.setId(user.getId());
		authFindUserVo.setUserName(user.getUserName());
		authFindUserVo.setRemarks(user.getRemarks());
		authFindUserVo.setUserType(user.getUserType());
		if (user.getChannelId() != null) {
			LaunChannel channel = channelMapper.selectByPrimaryKey(user.getChannelId());
			user.setChannelName(channel.getName());
			authFindUserVo.setChannelId(channel.getId());
			authFindUserVo.setChannelName(channel.getName());
		} else if (user.getChannelId() == null && user.getUserType() == ConstantUtlis.DOWN_SHELF) {
			user.setChannelId(0L);
			user.setChannelName("四维超级管理");
			authFindUserVo.setChannelId(0L);
			authFindUserVo.setChannelName("四维超级管理");
		} else if (user.getUserType() == ConstantUtlis.DOWN_SHELF) {
			user.setChannelId(0L);
			user.setChannelName("四维超级管理");
			authFindUserVo.setChannelId(0L);
			authFindUserVo.setChannelName("四维超级管理");
		}
		List<LaunPermissions> list = launPermissionsService.findPermissionsByUserId(id);
		user.setListPermissions(list);
		authFindUserVo.setListPermissions(list);
		return authFindUserVo;
	}

}
