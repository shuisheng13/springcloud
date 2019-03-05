package com.pactera.business.service;

import com.pactera.vo.LaunKeywordsVo;

import java.util.List;

public interface LaunKeywordsService {

    List<LaunKeywordsVo> query(Integer layoutId, Integer status, String keyword);
}
