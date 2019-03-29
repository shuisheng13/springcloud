package com.pactera.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.navinfo.wecloud.saas.api.facade.TenantFacade;
import com.pactera.business.dao.LaunThemeClassificationV2Mapper;
import com.pactera.business.service.LaunFileCrudService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LauncThemeClassificationV2Service;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.domain.LaunThemeClassificationV2;
import com.pactera.po.ThemesParam;
import com.pactera.result.ResultData;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunPage;
import com.pactera.vo.LaunThemeVo;
import com.pactera.vo.LauncThemeClassVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主题分类管理
 *
 * @author:zhaodong
 * @since:2018年12月19日 14:29:39
 */
@Service
@Slf4j
public class LaunThemeClassificationV2ServiceImpl implements LauncThemeClassificationV2Service {

    @Resource
    private LaunThemeClassificationV2Mapper LauncThemeClassMapper;

    @Resource
    private LaunFileCrudService launFileCrudService;

    @Resource
    private TenantFacade tenantFacade;

    @Resource
    private LaunThemeService launThemeService;

    /**
     * 主题分类添加(只有普通租户)
     *
     * @return ResponseEntity<ResultData>
     * @Author zhaodong
     * @Date 12:07 2018/12/20
     * @Param themeClassName, themeClassName, coverImage
     **/
    @Override
    public ResponseEntity<ResultData> addthemeClass(String themeClassName, MultipartFile coverImage, long formatId) {

        // 检查分类名是否超过限制
        if (themeClassName.length() > 8) {
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.message());
            return ResponseEntity.ok(resultData);
        }
        //检查添加的分类是否重名
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setFormatId(formatId);
        String tenantId2 = SaasHeaderContextV1.getTenantId();
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        long count = launcThemeClass.stream().filter(t -> themeClassName.equals(t.getClassificationName())).count();
        if (count>0){
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_ADD.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_ADD.message());
            return ResponseEntity.ok(resultData);
        }
        LaunThemeClassificationV2 themeClass = new LaunThemeClassificationV2()
                .setId(ThemeClassId())
                .setClassificationName(themeClassName)
                .setCreator(SaasHeaderContextV1.getUserName())
                .setTenantId(tenantId2 + "")// 存租户id，如今并没有什么卵用，就是处理成租户和管理员是一个，也就是说是租户
                .setDisable(1)
                .setQuantity(0)
                .setShelfCount(0)
                .setShelfStatus("0")
                .setSort(1)
                .setFormatId(formatId)
                .setCreateDate(new Date())
                .setUpdateDate(new Date());
        String coverImageUrl = launFileCrudService.fileUpload(coverImage);
        if (coverImageUrl == null) {
            ResultData resultData = new ResultData(ErrorStatus.WIDGETUPLOAD_ERROR.status(), ErrorStatus.WIDGETUPLOAD_ERROR.message());
            return ResponseEntity.ok(resultData);
        } else {
            themeClass.setCoverImage(coverImageUrl);
        }
        //存表
        int insert = LauncThemeClassMapper.insert(themeClass);
        if (insert == 1) {
            ResultData resultData = new ResultData();
            return ResponseEntity.ok(resultData);
        } else {
            ResultData resultData = new ResultData(500, "保存失败");
            return ResponseEntity.ok(resultData);
        }

    }

    /**
     * 编辑主题分类
     *
     * @return ResponseEntity<ResultData>
     * @Author zhaodong
     * @Date 11:42 2018/12/20
     * @Param themeClassName, themeClassId, coverImage
     **/
    @Override
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id, String coverImage) {
        // 校验名字字符是否超过8字符
        if (themeClassName.length() > 8) {
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.message());
            return ResponseEntity.ok(resultData);
        }
        // 检查租户下编辑是否重名
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        LaunThemeClassificationV2 V2 = LauncThemeClassMapper.selectByPrimaryKey(id);
        themeClassVo.setDisable(1);
        themeClassVo.setFormatId(V2.getFormatId());
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        if(launcThemeClass.stream().filter(t->{
            if (themeClassName.equals(t.getClassificationName())){
                return !id.equals(t.getId()); // 如果为本身原来名字编辑，可以
            }else{
                return false;
            }
        }).count()>0){
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_UP.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_UP.message());
            return ResponseEntity.ok(resultData);
        }
        LauncThemeClassVo themeClass = new LauncThemeClassVo();
        themeClass.setUpdateDate(new Date());
        themeClass.setClassificationName(themeClassName);
        themeClass.setTenantId(SaasHeaderContextV1.getTenantId() + "");
        themeClass.setId(id);
        themeClass.setCoverImage(coverImage);
        LauncThemeClassMapper.updateByThemClassId(themeClass);
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }

    /**
     * 删除主题分类
     *
     * @return
     * @Author zhaodong
     * @Date 14:27 2018/12/20
     * @Param themeClassId
     **/
    @Override
    public ResponseEntity<ResultData> deThemeClass(String id) {
        LauncThemeClassMapper.deleteByThemClassId(id);
        launThemeService.cleanThemeClassification(id);
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }

    /**
     * 查看主题分类
     *
     * @return
     * @Author zhaodong
     * @Date 16:26 2018/12/20
     * @Param themeClassId
     **/
    @Override
    public ResponseEntity<ResultData> seThemeClass(String id, int pageNum, int pageSize) {
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setId(id);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        JSONObject json = new JSONObject();
        launcThemeClass.forEach(laun->{
            json.put("classificationName", laun.getClassificationName());
            json.put("coverImage", laun.getCoverImage());
        });
        LaunPage<LaunThemeVo> query = launThemeService.query(new ThemesParam(id,null, null, null,null, pageNum, pageSize));
        json.put("themlist", query);
        ResultData resultData = new ResultData();
        resultData.setData(json);
        return ResponseEntity.ok(resultData);
    }

    /**
     * 分类列表
     *
     * @return
     * @Author zhaodong
     * @Date 16:43 2018/12/20
     * @Param
     **/
    @Override
    public ResponseEntity<ResultData> seThemeClassList(String shelfStatus, String classificationName, int pageNum, int pageSize, Long formatId) {
        PageHelper.startPage(pageNum, pageSize);
        LauncThemeClassVo themeClass = new LauncThemeClassVo()
                .setClassificationName(classificationName)
                .setDisable(1)
                .setFormatId(formatId==null?0:formatId)
                .setShelfStatus(shelfStatus);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClass);
        if(!launcThemeClass.isEmpty()) {
            //主题分类id给我组合
            List<String> listId = new ArrayList<>();
            launcThemeClass.forEach(la -> { listId.add(la.getId()); });
            //查询一下分类下面有多少的主题，有多少上架的，多少下架的。
            List<Map> maps = LauncThemeClassMapper.seQuantityTheme(listId);
            // 查询分类下面有多少上架的主题
            List<Map> maps1 = LauncThemeClassMapper.seQuantityThemeUp(listId);
            launcThemeClass.forEach(L->{
                String id = L.getId();
                maps.stream().filter(t->t.containsValue(id)).forEach(t->L.setQuantity(Integer.parseInt(String.valueOf((long) t.get("quantity")))));
                maps1.stream().filter(t->t.containsValue(id)).forEach(t->L.setShelfCount(Integer.parseInt(String.valueOf((long) t.get("shelfCount")))));
            });
        }
        PageInfo<LauncThemeClassVo> PageInfo = new PageInfo<>(launcThemeClass);
        ResultData resultData = new ResultData();
        resultData.setData(new LaunPage(PageInfo, launcThemeClass));
        return ResponseEntity.ok(resultData);
    }

    /**
     * 主题分类的上架下架
     *
     * @return
     * @Author zhaodong
     * @Date 13:47 2018/12/21
     * @Param
     **/
    @Override
    public ResponseEntity<ResultData> themeClassUpOrDown(String shelfStatus, String id) {
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo()
                .setUpdateDate(new Date())
                .setShelfCount(-2)
                .setQuantity(-2)
                .setId(id)
                .setShelfStatus(shelfStatus);
        int i = LauncThemeClassMapper.updateByThemClassId(themeClassVo);
        log.info("租户上下架主题分类>>>>>>>>>>>>>>>>租户添加" + i + ">>>>" + new Date());
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }

     /**
      * 权重排序(单个)
      * @Author zhaodong
      * @Date 11:03 2019/1/17
      * @Param
      * @return
      **/
    @Override
    public ResponseEntity<ResultData> updateThemTypeOrder(String id,int order) {
        LauncThemeClassMapper.updateThemTypeOrder(order,id);
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }
    /**
     * 权重排序(批量排序)
     *
     * @return
     * @Author zhaodong
     * @Date 16:36 2018/12/26
     * @Param
     **/
    @Override
    public ResponseEntity<ResultData> orderThemeClass(String sortOrder) {
        List<Map> list = JsonUtils.jsonToList(sortOrder, Map.class);
        LauncThemeClassMapper.orderThemeClass(list);
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }

    /**
     * 主题类别下的主题数量判断
     *
     * @Author zhaodong
     * @Date 15:01 2018/12/21
     * @Param
     * @return2624830033
     **/
    @Override
    public ResponseEntity<ResultData> themeClassIsUpOrDown(String id) {
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setId(id);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        ResultData resultData = new ResultData();
        if (!launcThemeClass.isEmpty()) {
            JSONObject json = new JSONObject();
            if (launcThemeClass.get(0).getShelfCount() == 0)
                json.put("shelfCount", false);
            else
                json.put("shelfCount", true);
            resultData.setData(json);
        } else {
            resultData = new ResultData(500, "不存在该主题类别");
        }
        return ResponseEntity.ok(resultData);
    }

    /**
     * @Author zhaodong
     * @Date 17:16 2019/1/14
     * @Param
     * @return
     **/
    public ResponseEntity<ResultData> themeClassByTid(Long formatId){
        LauncThemeClassVo vo = new LauncThemeClassVo();
        vo.setDisable(1);
        vo.setFormatId(formatId);
        List<LauncThemeClassVo> launcThemeClassVos = LauncThemeClassMapper.selectLauncThemeClassVo(vo);
        List<JSONObject> list = launcThemeClassVos.stream().map(t -> {
            JSONObject json1 = new JSONObject();
            json1.put("themeClassName", t.getClassificationName());
            json1.put("classNameId", t.getId());
            return json1;
        }).collect(Collectors.toList());
        JSONObject json = new JSONObject();
        json.put("list",list);
        ResultData resultData = new ResultData(json);
        return ResponseEntity.ok(resultData);
    }

    //判断是否有id重复的
    private String ThemeClassId(){
        // 获取id
        String id = IdUtlis.Id("ZTFL", SaasHeaderContextV1.getTenantName());
        LaunThemeClassificationV2 launTheme = LauncThemeClassMapper.selectByPrimaryKey(id);
        if (launTheme == null){
            return id;
        }else{
            return this.ThemeClassId();
        }
    }

}
