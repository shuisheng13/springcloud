package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.domain.LaunVersions;
import com.pactera.utlis.IdUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public int add(String version, Long tenantId) {
        LaunVersions launVersions = new LaunVersions();
        launVersions.setId(IdUtlis.Id()).setCreateDate(new Date());
        launVersions.setVersion(version).setTenantId(tenantId);
        return versionMapper.insert(launVersions);
    }
}
