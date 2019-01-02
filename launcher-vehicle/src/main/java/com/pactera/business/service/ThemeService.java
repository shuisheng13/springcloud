package com.pactera.business.service;

import com.pactera.vo.LaunThemeVo;

import java.util.List;

public interface ThemeService {

    /**
     * 搜索主题
     * @param value 关键字
     * @return
     */
    List<LaunThemeVo> search(String value);

    /**
     * 统计主题下载（应用）次数
     * @return
     */
    int count(String id, int type);

}
