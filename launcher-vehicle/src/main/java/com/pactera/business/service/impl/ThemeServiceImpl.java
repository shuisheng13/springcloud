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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Value("${fast.url}")
    private String fastUrl;

    @Override
    public List<ThemeListVO> search(String value, String apiKey, double version) {
        //Integer tenantId = apiKeyFacade.queryTenantByApiKey(apiKey).getData().getId();
        List<LaunThemeAdministration> launThemeAdministrations = launThemeMapper.search(value, version);
        BeanCopier beanCopier = BeanCopier.create(LaunThemeAdministration.class ,ThemeListVO.class,false);
        List<ThemeListVO> themeLists = launThemeAdministrations.stream().map(a->{
            ThemeListVO themeListVO = new ThemeListVO();
            beanCopier.copy(a, themeListVO, null);
            themeListVO.setPreviewPath(fastUrl + themeListVO.getPreviewPath());
            return themeListVO;
        }).collect(Collectors.toList());
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
        ThemeDetailVO themeDetailVO = new ThemeDetailVO();

        if(null != theme) {
            ThemeVO themeVo = new ThemeVO();
            BeanCopier.create(LaunThemeVo.class ,ThemeVO.class,false)
                    .copy(theme, themeVo, null);
            themeVo.setZipUrl(fastUrl + themeVo.getZipUrl());
            themeVo.setDownloadCount(themeVo.getDownloadCount() + themeVo.getAddition());
            themeDetailVO.setTheme(themeVo);
        }

        if(null != files) {
            BeanCopier beanCopier = BeanCopier.create(LaunThemeFileVo.class ,ThemeFileVO.class,false);
            List<ThemeFileVO> nfiles = files.stream().map(f->{
                ThemeFileVO file = new ThemeFileVO();
                beanCopier.copy(f,file,null);
                file.setFilePath(fastUrl + file.getFilePath());
                return file;
            }).collect(Collectors.toList());
            themeDetailVO.setFile(nfiles);
        }
        return themeDetailVO;
    }
}
