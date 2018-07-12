package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeWidthToHightMapper;
import com.pactera.business.service.LaunThemeWidthToHightService;
import com.pactera.domain.LaunWidgetWidthToHight;

import tk.mybatis.mapper.entity.Example;

/**
 * @description: 关于主题中的widget的实现类
 * @author:woqu
 * @since:2018年4月29日 下午3:21:21
 */
@Service
public class LaunThemeWidthToHightServiceImpl implements LaunThemeWidthToHightService {
	@Autowired
	private LaunThemeWidthToHightMapper hightMapper;

	/**
	 * @description 保存主题中的widget
	 * @author liudawei
	 * @since 2018年4月29日 下午3:20:50
	 * @param
	 */
	@Override
	public void save(List<LaunWidgetWidthToHight> hights) {
		if (hights.size() > 0) {
			for (LaunWidgetWidthToHight launWidgetWidthToHight : hights) {
				hightMapper.insertSelective(launWidgetWidthToHight);
			}
		}

	}

	@Override
	public List<LaunWidgetWidthToHight> findByConfigId(Long configId) {
		Example example = new Example(LaunWidgetWidthToHight.class);
		example.createCriteria().andEqualTo("widgetConfigId", "configId");
		return hightMapper.selectByExample(example);
	}

}
