package com.pactera.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunVersionMapper;
import com.pactera.business.service.LaunVersionService;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.config.header.TenantFacadeV1;
import com.pactera.domain.LaunVersions;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunPage;
import com.pactera.vo.LaunVersionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

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
    private TenantFacadeV1 tenantFacade;

    @Override
    public LaunPage<LaunVersionsVo> query(int pageNum, int pageSize) {
        PageInfo<LaunVersionsVo> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> list());
        return new LaunPage(pageInfo, pageInfo.getList());
    }

    @Override
    public int describe(Long id, String description) {

        LaunVersions launVersions = new LaunVersions();
        launVersions.setId(id).setUpdateDate(TimeUtils.nowTimeStamp());
        launVersions.setDescription(description);
        return versionMapper.updateByPrimaryKeySelective(launVersions);
    }

    @Override
    public List<LaunVersionsVo> list() {
        Example example = new Example(LaunVersions.class);
        //example.createCriteria().andEqualTo("tenantId", SaasHeaderContextV1.getTenantIdInt());
        example.setOrderByClause("version DESC");
        List<LaunVersions> list = versionMapper.selectByExample(example);
        return this.po2vo(list);
    }

    private List<LaunVersionsVo> po2vo(List<LaunVersions> source) {
        BeanCopier beanCopier = BeanCopier.create(LaunVersions.class, LaunVersionsVo.class, false);
        return source.stream().map(ver -> {
            LaunVersionsVo launVersionsVo = new LaunVersionsVo();
            beanCopier.copy(ver, launVersionsVo, null);
            launVersionsVo.setTenantName(tenantFacade.tenantInfoName(ver.getTenantId()));
            return launVersionsVo;
        }).collect(Collectors.toList());
    }
}
