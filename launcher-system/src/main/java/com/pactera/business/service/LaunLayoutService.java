package com.pactera.business.service;

import java.util.List;

public interface LaunLayoutService {

    List<com.pactera.domain.LaunLayout> query();
    com.pactera.domain.LaunLayout findById(Long id);
}
