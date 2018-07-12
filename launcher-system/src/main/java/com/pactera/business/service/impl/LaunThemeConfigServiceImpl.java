package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeConfigMapper;
import com.pactera.business.service.LaunThemeConfigService;
import com.pactera.business.service.LaunThemeWidthToHightService;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.domain.LaunWidgetWidthToHight;
import com.pactera.utlis.IdUtlis;

import tk.mybatis.mapper.entity.Example;

/**
 * @description: 关于主题中配置的实现类
 * @author:woqu
 * @since:2018年4月29日 下午3:23:08
 */
@Service
public class LaunThemeConfigServiceImpl implements LaunThemeConfigService {

	@Autowired
	private LaunThemeWidthToHightService launThemeWidthToHightService;

	@Autowired
	private LaunThemeConfigMapper configMapper;

	/**
	 * @description 保存主题的配置信息
	 * @author liudawei
	 * @since 2018年4月29日 下午3:23:19
	 * @param
	 */
	@Override
	public Long save(LaunThemeConfig launThemeConfig) {
		Long id = IdUtlis.Id();
		launThemeConfig.setId(id);
		configMapper.insertSelective(launThemeConfig);
		return id;
	}

	@Override
	public List<LaunThemeConfig> findByThemeId(Long id) {

		List<LaunThemeConfig> list = configMapper.findByThemeId(id);

		// 查询每个配置的详情配置信息
		for (LaunThemeConfig launConfig : list) {
			List<LaunWidgetWidthToHight> wh = launThemeWidthToHightService.findByConfigId(launConfig.getId());
			launConfig.setLaunWidgetWidthToHightList(wh);
		}
		return list;
	}

	@Override
	public void deleteByThemeId(Long id) {

		if (id == null) {
			return;
		}
		Example example = new Example(LaunThemeConfig.class);
		example.or().andEqualTo("launThemeId", id);
		configMapper.deleteByExample(example);
	}

}
