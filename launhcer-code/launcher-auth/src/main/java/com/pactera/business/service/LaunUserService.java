package com.pactera.business.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunUser;
import com.pactera.domain.LaunUserRole;
import com.pactera.vo.LaunAuthFindUserVo;

/**
 * @description: 用户授权的接口
 * @author:woqu
 * @since:2018年5月11日 下午3:15:57
 */
public interface LaunUserService {

	/**
	 * 保存用户
	 * 
	 * @description 保存用户
	 * @author liudawei
	 * @param permissionId
	 *            权限id
	 * @since 2018年5月11日 下午3:16:11
	 * @param launUser
	 *            用户
	 * @return Integer
	 */
	Integer save(LaunUser launUser, List<Long> permissionId);

	/**
	 * 查询用户名是否重复
	 * 
	 * @description 查询用户名是否重复
	 * @author liudawei
	 * @since 2018年5月11日 下午3:16:17
	 * @param userName
	 *            用户名称
	 * @return LaunUser
	 */
	LaunUser selectByUserName(String userName);

	/**
	 * 查询列表
	 * 
	 * @description 查询列表
	 * @author liudawei
	 * @since 2018年5月11日 下午3:33:15
	 * @param id
	 *            用户id
	 * @return Map<String,Object>
	 */
	Map<String, Object> selectAccountList(Long id);

	/**
	 * 更新
	 * 
	 * @description 更新
	 * @author liudawei
	 * @param permissionId
	 * @since 2018年5月11日 下午4:22:27
	 * @param launUser
	 *            活用
	 * @return Integer
	 */
	Integer update(LaunUser launUser, List<Long> permissionId);

	/**
	 * 根据id去删除用户
	 * 
	 * @description 根据id去删除用户
	 * @author liudawei
	 * @since 2018年5月11日 下午4:28:27
	 * @param id
	 *            用户id
	 * @return Integer
	 */
	Integer deleteById(Long id);

	/**
	 * 列表
	 * 
	 * @description 列表
	 * @author liudawei
	 * @since 2018年5月11日 下午5:53:21
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            页大小
	 * @return List<LaunUser>
	 */
	PageInfo<LaunAuthFindUserVo> accountList(Integer pageNum, Integer pageSize);

	/**
	 * 根据id去查询
	 * 
	 * @description 根据id去查询
	 * @author liudawei
	 * @since 2018年5月11日 下午6:52:49
	 * @param id
	 *            用户id
	 * @return LaunUser
	 */
	LaunUser selectById(Long id);

	/**
	 * 模糊查询
	 * 
	 * @description 模糊查询
	 * @author liudawei
	 * @since 2018年5月24日 上午9:38:03
	 * @param userName
	 *            用户名
	 * @param type
	 *            用户类型
	 * @param channelId
	 *            渠道id
	 * @return List<LaunUser>
	 */
	List<LaunUser> findUserByLike(String userName, String type, String channelId);

	/**
	 * 根据用户名去更新用户
	 * 
	 * @description 根据用户名去更新用户
	 * @author liudawei
	 * @since 2018年5月16日 下午5:44:03
	 * @param user
	 *            用户
	 * @return Long
	 */
	void updateByUser(LaunUser user);

	/**
	 * 查看用户是否存在角色
	 * 
	 * @description 查看用户是否存在角色
	 * @author liudawei
	 * @since 2018年5月17日 下午10:02:46
	 * @param id
	 *            用户的id
	 * @return List<LaunUserRole>
	 */
	List<LaunUserRole> selectRoleById(Long id);

	/**
	 * 获取当前登录人的渠道
	 * 
	 * @description 获取当前登录人的渠道
	 * @author liudawei
	 * @since 2018年5月17日 下午10:02:38
	 * @param channelId
	 *            渠道id
	 * @return LaunChannel
	 */
	LaunChannel selectChannelByLogin(Long channelId);

	/**
	 * 更新用户
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月28日 下午3:42:29
	 * @param user
	 * @return void
	 */
	void updateByUserSelect(LaunUser user);

	/**
	 * 根据id去查询
	 * 
	 * @description 根据id去查询
	 * @author liudawei
	 * @since 2018年5月11日 下午6:52:49
	 * @param id
	 *            用户id
	 * @return LaunUser
	 */
	LaunAuthFindUserVo selectByIdVo(Long id);

}
