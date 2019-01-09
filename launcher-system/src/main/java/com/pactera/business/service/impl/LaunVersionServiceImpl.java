package com.pactera.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.domain.LaunVersions;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunThemeVo;
import com.pactera.vo.LaunVersionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PageInfo<LaunVersionsVo> versions(int pageNum, int pageSize) {
        List<LaunVersionsVo> list = new ArrayList<>();

        PageHelper.startPage(pageNum, pageSize);
        List<LaunVersions> versions = versionMapper.selectAll();
        BeanCopier beanCopier = BeanCopier.create(LaunVersions.class, LaunVersionsVo.class,false);
        versions.forEach(ver->{
            LaunVersionsVo launVersionsVo = new LaunVersionsVo();
            beanCopier.copy(ver, launVersionsVo, null);
            launVersionsVo.setTenantName("xukj");
            list.add(launVersionsVo);
        });

        return new PageInfo<>(list);
    }

    @Override
    public int describe(Long id, String description) {

        LaunVersions launVersions = new LaunVersions();
        launVersions.setId(id).setUpdateDate(TimeUtils.nowTimeStamp());
        launVersions.setDescription(description);
        return versionMapper.updateByPrimaryKeySelective(launVersions);
    }
}
