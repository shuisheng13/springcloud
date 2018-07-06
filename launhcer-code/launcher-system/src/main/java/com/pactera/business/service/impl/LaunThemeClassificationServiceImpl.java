package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeClassificationMapper;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.LaunThemeClassificationService;
import com.pactera.domain.LaunThemeClassification;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;

import tk.mybatis.mapper.entity.Example;

/**
 * @description: 主题分类有关的实现类
 * @author:woqu
 * @since:2018年4月26日 下午2:15:15
 */
@Service
public class LaunThemeClassificationServiceImpl implements LaunThemeClassificationService {

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private LaunThemeClassificationMapper launThemeClassificationMapper;

	/**
	 * @description 保存分类
	 * @author liudawei
	 * @since 2018年4月26日 下午2:18:28
	 * @param classification
	 */
	@Override
	public void addType(String classification, String ids) {

		// 添加分类
		if (HStringUtlis.isNotBlank(classification)) {
			String[] classifies = classification.split(",");
			for (String string : classifies) {
				LaunThemeClassification themeClassification = new LaunThemeClassification();
				themeClassification.setId(IdUtlis.Id());
				themeClassification.setClassificationName(string);
				launThemeClassificationMapper.insert(themeClassification);
			}

		}

		// 删除分类
		delectById(ids);
	}

	/**
	 * @description 查询分类
	 * @author liudawei
	 * @since 2018年4月26日 下午10:12:50
	 * @param
	 */
	@Override
	public List<LaunThemeClassification> selecByType() {
		List<LaunThemeClassification> list = launThemeMapper.selectTypeList();
		return list;
	}

	@Override
	public void delectById(String ids) {

		List<Long> list = new ArrayList<Long>();
		if (HStringUtlis.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for (String string : split) {
				list.add(Long.parseLong(string));
			}
			Example example = new Example(LaunThemeClassification.class);
			example.createCriteria().andIn("id", list);
			launThemeClassificationMapper.deleteByExample(example);
		}
		// launThemeClassificationMapper.deleteByPrimaryKey(id);
	}
}
