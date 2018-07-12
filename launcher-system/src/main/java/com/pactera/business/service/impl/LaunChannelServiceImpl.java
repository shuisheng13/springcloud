package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.service.LaunChannelService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunChannel;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * @description:渠道接口实现类
 * @author:LL
 * @since:2018年4月27日 上午10:22:48
 */
@Service
public class LaunChannelServiceImpl implements LaunChannelService {

	@Autowired
	private LaunChannelMapper launChannelMapper;

	@Override
	public List<LaunChannel> findAll(String name) {
		Example example = new Example(LaunChannel.class);
		example.createCriteria().andIsNotNull("userId").andNotEqualTo("channelStatus", ConstantUtlis.UP_SHELF);
		example.setOrderByClause("create_date desc");
		Criteria createCriteria = example.createCriteria();
		if (name != null) {
			createCriteria.andLike("name", "%" + name + "%");
		}
		return launChannelMapper.selectByExample(example);
	}

	@Override
	public LaunChannel findById(String id) {
		return launChannelMapper.selectById(new Long(id));
	}

	@Override
	public LaunChannel findByName(String channleName) {
		Example example = new Example(LaunChannel.class);
		example.createCriteria().andNotEqualTo("name", channleName);
		List<LaunChannel> list = launChannelMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
}
