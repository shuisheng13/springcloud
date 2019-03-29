package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunLayoutMapper;
import com.pactera.business.service.LaunLayoutService;
import com.pactera.domain.LaunLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName LaunLayoutServiceImpl
 * @Description
 * @Author xukj
 * @Date 2019/3/11 14:25
 * @Version
 */
@Service
public class LaunLayoutServiceImpl implements LaunLayoutService {

    @Autowired
    private LaunLayoutMapper launLayoutMapper;


    @Override
    public LaunLayout findByName(String name) {
        return launLayoutMapper.selectOne(new LaunLayout().setName(name));
    }

    @Override
    public Long findIdByName(String name) {
        LaunLayout launLayout = this.findByName(name);
        Optional<LaunLayout> launLayoutOptional = Optional.ofNullable(launLayout);
        return launLayoutOptional.map(l->l.getId()).orElse(null);
    }
}
