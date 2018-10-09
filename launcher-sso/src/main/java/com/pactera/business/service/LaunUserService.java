package com.pactera.business.service;

import com.pactera.domain.LaunUser;

public interface LaunUserService {
	
	/**
	 * 方法描述：根据用户名查询用户信息  
	 * 创建人：赵兴炎 
	 * 创建时间：2017年10月31日 下午7:42:58
	 * @param userName 用户名
	 */
	LaunUser findUserByUserName(String userName);
	
	/**
	 * 方法描述:添加登陆日志
	 * 创建人：赵兴炎 
	 * 创建时间：2017年10月31日 下午7:42:58
	 */
	void saveUserLogs(LaunUser user,String ip);

}
