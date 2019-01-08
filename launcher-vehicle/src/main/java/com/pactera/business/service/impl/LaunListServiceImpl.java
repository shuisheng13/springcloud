package com.pactera.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.navinfo.wecloud.common.rest.response.CommonResult;
import com.navinfo.wecloud.saas.api.facade.ApiKeyFacade;
import com.navinfo.wecloud.saas.api.response.TenantInfo;
import com.pactera.business.dao.LaunVehicleListMapper;
import com.pactera.business.service.LaunListService;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.dto.ThemClassDTO;
import com.pactera.dto.ThemListDTO;
import com.pactera.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhaodong
 * @Date 10:22 2019/1/7
 */
@Service
public class LaunListServiceImpl implements LaunListService {

    @Autowired
    private ApiKeyFacade apiKeyFacade;

    @Autowired
    private LaunVehicleListMapper launVehicleListMapper;

    /**
     * 主题分类列表
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> themeclasslist2(String apiKey){
        CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();
        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        int tenanId = data.getId();
        List<ThemClassDTO> themListDTOS = launVehicleListMapper.themeclasslist2(tenanId + "");
        JSONObject json = new JSONObject();
        json.put("list",themListDTOS);
        ResultData resultData = new ResultData(json);
        return ResponseEntity.ok().body(resultData);
    }

    /**
     * 1-全部主题，2-主题排行，3-推荐主题
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> themTopAndAll(String apiKey,int status, int pageNum, int pageSize){
        CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();

        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        int tenanId = data.getId();
        PageHelper.startPage(pageNum, pageSize);
        List<ThemListDTO> themListDTOS=new ArrayList<>();
        LaunThemeAdministration LaunThemeVo = new LaunThemeAdministration();
        LaunThemeVo.setTenantId(tenanId+"");
        if (status==1) LaunThemeVo.setSort(-1);// 全部标记
        else if (status==2) LaunThemeVo.setDownloadCount(-1);// 排行标记
        else if (status==3) LaunThemeVo.setRecommendSort(-1);// 推荐标记
        else return ResponseEntity.ok().body(new ResultData(ErrorStatus.SYS_ERROR.status(),ErrorStatus.SYS_ERROR.message()));// 暂时提供系统错误
        themListDTOS = launVehicleListMapper.themTopAndByClassId(LaunThemeVo);
        PageInfo<ThemListDTO> PageInfo = new PageInfo<>(themListDTOS);
        JSONObject json = new JSONObject();
        json.put("list",PageInfo);
        ResultData resultData = new ResultData(json);
        return ResponseEntity.ok().body(resultData);
    }

    /**
     * 分类下的主题列表
     * @Author zhaodong
     * @Date 10:10 2019/1/7
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> themTopAndByClassId(String apiKey, String id, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();

        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        int tenanId = data.getId();
        LaunThemeAdministration LaunThemeVo = new LaunThemeAdministration();
        LaunThemeVo.setTenantId(tenanId+"");
        LaunThemeVo.setTypeId(id); // id
        List<ThemListDTO> themListDTOS = launVehicleListMapper.themTopAndByClassId(LaunThemeVo);
        PageInfo<ThemListDTO> PageInfo = new PageInfo<>(themListDTOS);
        JSONObject json = new JSONObject();
        json.put("list",PageInfo);
        ResultData resultData = new ResultData(json);
        return ResponseEntity.ok().body(resultData);
    }
}
