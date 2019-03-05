package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunLayoutMapper;
import com.pactera.business.service.LaunLayoutService;
import com.pactera.domain.LaunLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LaunLayoutServiceImpl
 * @Description
 * @Author xukj
 * @Date 2019/3/5 17:07
 * @Version
 */
@Service
public class LaunLayoutServiceImpl implements LaunLayoutService {

    @Autowired
    private LaunLayoutMapper launLayoutMapper;

    @Override
    public List<LaunLayout> query() {
        return launLayoutMapper.selectAll();
    }
}
