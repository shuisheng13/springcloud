package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunThemeFileMapper;
import com.pactera.business.service.LaunThemeFileService;
import com.pactera.domain.LaunThemeFile;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.JsonUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * @description: 主题相关的实现类
 * @author:woqu
 * @since:2018年4月26日 上午11:27:03
 */
@Service
public class LaunThemeFileServiceImpl implements LaunThemeFileService {

	@Autowired
	private LaunThemeFileMapper launThemeFileMapper;

	@Override
	public List<LaunThemeFile> selectByThemeId(Long themeId) {
		Example example = new Example(LaunThemeFile.class);

		example.createCriteria().andEqualTo("themeId", themeId);
		example.setOrderByClause("file_index");
		return launThemeFileMapper.selectByExample(example);
	}

	@Override
	public void insert(String json, Long themeId) {
		if (HStringUtlis.isNotBlank(json) && themeId != null) {
			List<LaunThemeFile> jsonToList = JsonUtils.jsonToList(json, LaunThemeFile.class);
			for (LaunThemeFile launThemeFile : jsonToList) {
				launThemeFile.setThemeId(themeId);
				launThemeFileMapper.insertSelective(launThemeFile);
			}
		}
	}

	@Override
	public void insert(LaunThemeFile themeFile) {
		launThemeFileMapper.insertSelective(themeFile);
	}

	@Override
	public void deleteById(Long id) {
		if (id == null) {
			return;
		}
		Example example = new Example(LaunThemeFile.class);
		example.createCriteria().andEqualTo("themeId", id);
		launThemeFileMapper.deleteByExample(example);
	}

}
