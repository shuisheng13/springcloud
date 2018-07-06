package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.dao.LaunApplicationPosterMapper;
import com.pactera.business.service.LaunApplicationPostersService;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.utlis.HStringUtlis;
import com.pactera.vo.LaunApplicationPosterVo;

/**
 * @description:应用海报实现类
 * @author:LL
 * @since:2018年5月10日 上午10:55:19
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class LaunApplicationPostersServiceImpl implements LaunApplicationPostersService {

	@Autowired
	LaunApplicationPosterMapper launApplicationPostersMapper;

	@Override
	public List<LaunApplicationPoster> selectByIds(String ids) {
		List<LaunApplicationPoster> selectByIds = new ArrayList<>();
		List<Long> list = new ArrayList<>();
		if (HStringUtlis.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for (String spl : split) {
				list.add(Long.parseLong(spl));
			}
		}
		if (list.size() > 0) {
			selectByIds = launApplicationPostersMapper.selectByIds(list);
		}
		return selectByIds;
	}

	@Override
	public List<LaunApplicationPosterVo> select(Long applicationId) {

		return launApplicationPostersMapper.selectList(applicationId);
	}

}
