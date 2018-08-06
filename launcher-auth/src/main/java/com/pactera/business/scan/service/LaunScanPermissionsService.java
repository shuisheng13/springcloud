package com.pactera.business.scan.service;

import com.pactera.domain.LaunPermissions;

/**
 * @description:扫描权限接口
 * @author:woqu
 * @since:2018年5月24日 下午7:20:07
 */
public interface LaunScanPermissionsService {

	/**
	 * 保存
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:20:20
	 * @param launPermissions
	 * @return void
	 */
	void save(LaunPermissions launPermissions);

	/**
	 * 删除
	 * 
	 * @description
	 * @author dw
	 * @since 2018年5月24日 下午7:20:26
	 * @param
	 * @return void
	 */
	void delete();

}
