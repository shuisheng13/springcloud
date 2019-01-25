package com.pactera.business.service.impl;

import com.navinfo.wecloud.saas.api.facade.ApiKeyFacade;
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

    @Autowired
    private ApiKeyFacade apiKeyFacade;

    @Override
    public int add(String version,String versionName, String apiKey) {
        Integer tenantId = apiKeyFacade.queryTenantByApiKey(apiKey).getData().getId();
        if(null == tenantId){return 0;}
        LaunVersions launVersions = new LaunVersions();

        launVersions.setTenantId(tenantId).setVersion(version);
        if (versionMapper.select(launVersions) != null) { return 0; }

        launVersions.setId(IdUtlis.Id()).setCreateDate(TimeUtils.nowTimeStamp());
        return versionMapper.insert(launVersions);
    }
}
