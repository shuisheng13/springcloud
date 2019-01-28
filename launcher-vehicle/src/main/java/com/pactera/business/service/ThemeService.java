package com.pactera.business.service;

import com.pactera.vo.ThemeDetailVO;
import com.pactera.vo.ThemeListVO;

import java.util.List;

public interface ThemeService {

    /**
     * 搜索主题
     * @param value 关键字
     * @return
     */
    List<ThemeListVO> search(String value, String apiKey, double version);

    /**
     * 统计主题下载（应用）次数
     * @return
     */
    int count(String id, int type);


    ThemeDetailVO detail(String id);

}
