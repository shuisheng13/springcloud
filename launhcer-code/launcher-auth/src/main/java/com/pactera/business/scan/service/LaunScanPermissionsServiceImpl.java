package com.pactera.business.scan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunScanPermissionsMapper;
import com.pactera.domain.LaunPermissions;

import tk.mybatis.mapper.entity.Example;

/**
 * @description: 扫描
 * @author:woqu
 * @since:2018年5月24日 下午7:20:54
 */
@Service
public class LaunScanPermissionsServiceImpl implements LaunScanPermissionsService {

	@Autowired
	private LaunScanPermissionsMapper launScanPermissionsMapper;

	@Override
	public void save(LaunPermissions launPermissions) {
		Long id = launPermissions.getId();
		Example example = new Example(LaunPermissions.class);
		example.createCriteria().andEqualTo("id", id);
		int selectCountByExample = launScanPermissionsMapper.selectCountByExample(example);
		if (selectCountByExample == 0) {
			launScanPermissionsMapper.insertSelective(launPermissions);
		}
	}

	@Override
	public void delete() {
		Example example = new Example(LaunPermissions.class);
		example.createCriteria().andIsNull("parentId");
		launScanPermissionsMapper.deleteByExample(example);
	}

}
