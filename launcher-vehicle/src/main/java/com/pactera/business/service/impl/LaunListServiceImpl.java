package com.pactera.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.navinfo.wecloud.saas.api.facade.ApiKeyFacade;
import com.navinfo.wecloud.saas.api.response.TenantInfo;
import com.navinfo.wecloud.solar.common.rest.response.CommonResult;
import com.pactera.business.dao.LaunVehicleListMapper;
import com.pactera.business.service.LaunListService;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.dto.ThemClassDTO;
import com.pactera.dto.ThemListDTO;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhaodong
 * @Date 10:22 2019/1/7
 */
@Service
public class LaunListServiceImpl implements LaunListService {

    @Resource
    private ApiKeyFacade apiKeyFacade;

    @Resource
    private LaunVehicleListMapper launVehicleListMapper;

    @Value("${fast.url}")
    private String fastUrl;

    /**
     * 主题分类列表
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> themeclasslist2(String apiKey, String layoutName){
       /* CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();
        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        int tenanId = data.getId();*/
        //int tenanId = 55;
        List<ThemClassDTO> themListDTOS = launVehicleListMapper.themeclasslist2(layoutName);
        themListDTOS.stream().filter(t->!t.getCoverImage().contains(fastUrl)).forEach(t->t.setCoverImage(fastUrl+t.getCoverImage()));
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
    public ResponseEntity<ResultData> themTopAndAll(String apiKey,int status, int pageNum, int pageSize,double version,String layoutName){
       /* CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();
        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        int tenanId = data.getId();*/
        //int tenanId = 55;
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> map =new HashMap();
       // LaunThemeAdministration LaunThemeVo = new LaunThemeAdministration();
        map.put("version",version);
        if (status==1) map.put("sort",-1);// 全部标记
        else if (status==2) map.put("downloadCount",-1);// 排行标记
        else if (status==3) map.put("recommendSort",-1);// 推荐标记
        else return ResponseEntity.ok().body(new ResultData(ErrorStatus.SYS_ERROR.status(),ErrorStatus.SYS_ERROR.message()));// 暂时提供系统错误
        List<ThemListDTO> themListDTOS = launVehicleListMapper.themTopAndByClassId(map);
        themListDTOS.stream().filter(t->!t.getPreviewPath().contains(fastUrl)).forEach(t->t.setPreviewPath(fastUrl+t.getPreviewPath()));
        PageInfo<ThemListDTO> PageInfo = new PageInfo<>(themListDTOS);
        JSONObject json = new JSONObject();
        json.put("list",new LaunPage(PageInfo, themListDTOS));
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
    public ResponseEntity<ResultData> themTopAndByClassId(String apiKey, String id, int pageNum, int pageSize,double version){
        PageHelper.startPage(pageNum, pageSize);
        /*CommonResult<TenantInfo> tenantInfoCommonResult = apiKeyFacade.queryTenantByApiKey(apiKey);
        TenantInfo data = tenantInfoCommonResult.getData();
        if (data==null){
            return ResponseEntity.ok().body(new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message()));
        }
        if (id==null){
            return ResponseEntity.ok().body(new ResultData("id不为空"));
        }
        int tenanId = data.getId();*/
        //int tenanId = 55;
        Map<String,Object> map =new HashMap();
        map.put("typeId",id);// id
        map.put("downloadCount",-1);//分类的主题列表按照下载量排序
        map.put("version",version);
        List<ThemListDTO> themListDTOS = launVehicleListMapper.themTopAndByClassId(map);
        themListDTOS.stream().filter(t->!t.getPreviewPath().contains(fastUrl)).forEach(t->t.setPreviewPath(fastUrl+t.getPreviewPath()));
        PageInfo<ThemListDTO> PageInfo = new PageInfo<>(themListDTOS);
        JSONObject json = new JSONObject();
        json.put("list",new LaunPage(PageInfo, themListDTOS));
        ResultData resultData = new ResultData(json);
        return ResponseEntity.ok().body(resultData);
    }
}
