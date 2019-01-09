package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.domain.LaunVersions;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LaunVersionServiceImpl
 * @Description
 * @Author xukj
 * @Date 2018/12/19 14:31
 * @Version
 */
@Service
public class LaunVersionServiceImpl implements LaunVersionService {

    @Autowired
    private LaunVersionMapper versionMapper;

    @Override
    public int add(String version) {
        //TODO 租户id
        Long tenantId = 123L;
        LaunVersions launVersions = new LaunVersions();

        launVersions.setTenantId(tenantId).setVersion(version);
        if (versionMapper.selectOne(launVersions) != null) { return 0; }

        launVersions.setId(IdUtlis.Id()).setCreateDate(TimeUtils.nowTimeStamp());
        return versionMapper.insert(launVersions);
    }
}
