package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.domain.LaunVersions;
import com.pactera.utlis.IdUtlis;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<LaunVersions> versions() {
        return versionMapper.selectAll();
    }

    @Override
    public int describe(Long id, String description) {

        LaunVersions launVersions = new LaunVersions();
        launVersions.setId(id).setUpdateDate(new Date());
        launVersions.setTenantId(null);
        launVersions.setDescription(description);
        return versionMapper.updateByPrimaryKeySelective(launVersions);
    }

    @Override
    public int add(String version) {
        //TODO 租户id
        Long tenantId = 123L;
        LaunVersions launVersions = new LaunVersions();

        launVersions.setTenantId(tenantId).setVersion(version);
        if (versionMapper.selectOne(launVersions) != null) { return 0; }

        launVersions.setId(IdUtlis.Id()).setCreateDate(new Date());
        return versionMapper.insert(launVersions);
    }
}
