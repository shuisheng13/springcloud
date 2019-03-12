package com.pactera.business.service.impl;

import com.navinfo.wecloud.saas.api.facade.ApiKeyFacade;
import com.pactera.business.dao.LaunLayoutMapper;
import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.domain.LaunLayout;
import com.pactera.domain.LaunVersions;
import com.pactera.dto.LaunVersionsDto;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private LaunVersionMapper versionMapper;
    @Resource
    private LaunLayoutMapper launLayoutMapper;

    @Autowired
    private ApiKeyFacade apiKeyFacade;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(double version,String versionName, String apiKey, String layoutName) {

        List<LaunVersionsDto> dtos = versionMapper.findByVersionAndLayout(version, layoutName);
        if(!dtos.isEmpty()) {return 0;}

        LaunLayout launLayout = new LaunLayout().setName(layoutName);
        if(0 == launLayoutMapper.selectCount(launLayout)) {
            launLayoutMapper.insertSelective(launLayout.setId(IdUtlis.Id()));
        }

        Long launLayoutId = launLayoutMapper.selectOne(launLayout).getId();

        LaunVersions launVersions = new LaunVersions();
        launVersions.setVersionName(versionName).setLayoutId(launLayoutId).setVersion(version);
        launVersions.setId(IdUtlis.Id()).setCreateDate(TimeUtils.nowTimeStamp());

        return versionMapper.insert(launVersions);
    }
}
