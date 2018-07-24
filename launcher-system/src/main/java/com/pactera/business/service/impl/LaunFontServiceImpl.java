package com.pactera.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunFontMapper;
import com.pactera.business.service.LaunFontService;
import com.pactera.domain.LaunFont;
import com.pactera.utlis.IdUtlis;

/**
 * @description:字体相关接口实现
 * @author:LL
 * @since:2018年7月10日 上午10:01:50
 */
@Service
public class LaunFontServiceImpl implements LaunFontService {

	@Autowired
	private LaunFontMapper launFontMapper;

	@Override
	public List<LaunFont> getList() {

		return launFontMapper.selectAll();
	}

	@Override
	public void addFont(String fontName, String filePath) {

		LaunFont record = new LaunFont();
		record.setId(IdUtlis.Id());
		record.setFilePath(filePath);
		record.setFontName(fontName);
		launFontMapper.insertSelective(record);
	}

	@Override
	public LaunFont selectFont() {
		List<LaunFont> selectAll = launFontMapper.selectAll();
		return selectAll.size() > 0 ? selectAll.get(0) : null;
	}

}
