package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.ThemeService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.vo.LaunThemeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ThemeServiceImpl
 * @Description
 * @Author xukj
 * @Date 2018/12/29 17:20
 * @Version
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private LaunThemeMapper launThemeMapper;

    @Override
    public List<LaunThemeVo> search(String value) {
        return launThemeMapper.search(value);
    }

    @Override
    public int count(String id, int type) {

        if(type != 0 && type != 1) {
            throw new DataStoreException(ErrorStatus.PARAMETER_ERROR);
        }
        return launThemeMapper.count(type, id);
    }
}
