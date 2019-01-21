package com.pactera.business.service.impl;

import com.navinfo.wecloud.saas.api.facade.ApiKeyFacade;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.RemoteThemeService;
import com.pactera.business.service.ThemeService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.dto.ThemeDTO;
import com.pactera.result.ResultData;
import com.pactera.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ThemeServiceImpl
 * @Description
 * @Author xukj
 * @Date 2018/12/29 17:20
 * @Version
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private LaunThemeMapper launThemeMapper;

    @Autowired
    private RemoteThemeService remoteThemeService;

    @Autowired
    private ApiKeyFacade apiKeyFacade;

    @Override
    public List<ThemeListVO> search(String value, String apiKey) {
        Integer tenantId = apiKeyFacade.queryTenantByApiKey(apiKey).getData().getId();
        List<LaunThemeAdministration> launThemeAdministrations = launThemeMapper.search(value, tenantId);
        List<ThemeListVO> themeLists = new ArrayList<>();
        BeanCopier beanCopier = BeanCopier.create(LaunThemeAdministration.class ,ThemeListVO.class,true);
        launThemeAdministrations.forEach(a->{
            ThemeListVO themeListVO = new ThemeListVO();
            beanCopier.copy(a, themeListVO, (Object v, Class t, Object c)->v);
            themeLists.add(themeListVO);
        });
        return themeLists;
    }

    @Override
    public int count(String id, int type) {

        if(type != 0 && type != 1) {
            throw new DataStoreException(ErrorStatus.PARAMETER_ERROR);
        }
        return launThemeMapper.count(type, id);
    }

    @Override
    public ThemeDetailVO detail(String id) {
        ResultData<ThemeDTO> themeDTO = remoteThemeService.detail(id);
        LaunThemeVo theme = themeDTO.getData().getTheme();
        List<LaunThemeFileVo> files = themeDTO.getData().getFile();

        ThemeVO themeVo = new ThemeVO();
        BeanCopier.create(LaunThemeVo.class ,ThemeVO.class,false)
                .copy(theme, themeVo, null);

        themeVo.setDownloadCount(themeVo.getDownloadCount() + themeVo.getAddition());
        BeanCopier beanCopier = BeanCopier.create(LaunThemeFileVo.class ,ThemeFileVO.class,false);
        List<ThemeFileVO> nfiles = new ArrayList<>();
        files.forEach(f->{
            ThemeFileVO file = new ThemeFileVO();
            beanCopier.copy(f,file,null);
            nfiles.add(file);
        });

        ThemeDetailVO themeDetailVO = new ThemeDetailVO();
        themeDetailVO.setFile(nfiles).setTheme(themeVo);
        return themeDetailVO;
    }
}
