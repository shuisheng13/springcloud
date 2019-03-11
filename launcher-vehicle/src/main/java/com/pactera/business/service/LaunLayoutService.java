package com.pactera.business.service;

import com.pactera.domain.LaunLayout;

/**
 * @ClassName LaunLayoutService
 * @Description
 * @Author xukj
 * @Date 2019/3/11 14:25
 * @Version
 */
public interface LaunLayoutService {

    LaunLayout findByName(String name);
    Long findIdByName(String name);
}
