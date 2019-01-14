package com.pactera.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.navinfo.wecloud.common.filter.SaasHeaderContext;
import com.navinfo.wecloud.saas.api.facade.TenantFacade;
import com.pactera.business.dao.LaunThemeClassificationV2Mapper;
import com.pactera.business.service.LaunFileCrudService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LauncThemeClassificationV2Service;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.domain.LaunThemeClassificationV2;
import com.pactera.result.ResultData;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunThemeInfoVo;
import com.pactera.vo.LaunThemeVo;
import com.pactera.vo.LauncThemeClassVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    LaunThemeService launThemeService;

    /**
     * 主题分类添加(只有普通租户)
     *
     * @return ResponseEntity<ResultData>
     * @Author zhaodong
     * @Date 12:07 2018/12/20
     * @Param themeClassName, themeClassName, coverImage
     **/
    @Override
    public ResponseEntity<ResultData> addthemeClass(String themeClassName, MultipartFile coverImage) {

        //System.out.println(SaasHeaderContextV1.getTenantName()+"-------"+SaasHeaderContextV1.getTenantId()+"======"+SaasHeaderContextV1.getUserType());
        //Integer tenantId2;
        // 判断用户的性质
        /*if (SaasHeaderContextV1.getUserType() == 0) {// 最高管理员的租户id为前台拿到的租户id
            tenantId2 = tenantId;
        } else if (SaasHeaderContextV1.getUserType() == 1) {// 用户为租户自己的时候，租户id通过网关来获取
            tenantId2 = SaasHeaderContextV1.getTenantId();
        } else {
            ResultData resultData = new ResultData(400, "该用户没有添加分类权限");
            return ResponseEntity.ok(resultData);
        }*/
        // 检查分类名是否超过限制
        if (themeClassName.length() > 8) {
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.message());
            return ResponseEntity.ok(resultData);
        }
        //检查添加的分类是否重名
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        int tenantId2 = SaasHeaderContextV1.getTenantId();
        themeClassVo.setTenantId(tenantId2 + "");
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        for (LauncThemeClassVo laun : launcThemeClass) {
            if (themeClassName.equals(laun.getClassificationName())) {
                ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_ADD.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_ADD.message());
                return ResponseEntity.ok(resultData);
            }
        }
        LaunThemeClassificationV2 themeClass = new LaunThemeClassificationV2();
        // 封装属性
        themeClass.setId(ThemeClassId());
        themeClass.setClassificationName(themeClassName);
        themeClass.setCreator(SaasHeaderContextV1.getUserName());
        themeClass.setTenantId(tenantId2 + "");
        themeClass.setDisable(1);
        themeClass.setQuantity(0);
        themeClass.setShelfCount(0);
        themeClass.setShelfStatus("0");
        themeClass.setSort(1);
        themeClass.setCreateDate(new Date());
        themeClass.setUpdateDate(new Date());
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
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id, MultipartFile coverImage) {
        // 校验名字字符是否超过8字符
        if (themeClassName.length() > 8) {
            ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_REEOR.message());
            return ResponseEntity.ok(resultData);
        }
        // 检查租户下编辑是否重名
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
       /* if (SaasHeaderContextV1.getUserType() == 1) {// 管理员去查一下租户id
            LaunThemeClassificationV2 laun = LauncThemeClassMapper.selectByPrimaryKey(id);
            themeClassVo.setTenantId(laun.getTenantId());
        } else {// 普通租户就直接获取id
            int tenantId2 = SaasHeaderContextV1.getTenantId();
            themeClassVo.setTenantId(tenantId2 + "");
        }*/
        int tenantId2 = SaasHeaderContextV1.getTenantId();
        themeClassVo.setTenantId(tenantId2 + "");
        themeClassVo.setDisable(1);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        for (LauncThemeClassVo laun : launcThemeClass) {
            if (themeClassName.equals(laun.getClassificationName())) {
                if (id.equals(laun.getId())) {// 如果为本身原来名字编辑，可以
                    continue;
                } else {
                    ResultData resultData = new ResultData(ErrorStatus.NAME_CLASS_LAUNTHEM_UP.status(), ErrorStatus.NAME_CLASS_LAUNTHEM_UP.message());
                    return ResponseEntity.ok(resultData);
                }
            }
        }
        LauncThemeClassVo themeClass = new LauncThemeClassVo();
        themeClass.setUpdateDate(new Date());
        themeClass.setClassificationName(themeClassName);
       /* if (SaasHeaderContextV1.getUserType() == 1) {// 如果为普通租户，修改加上租户id, 保证租户自己更新的安全, 如果最高的管理员空就没必要了。
            themeClass.setTenantId(SaasHeaderContextV1.getTenantId() + "");
        }*/
        themeClass.setTenantId(SaasHeaderContextV1.getTenantId() + "");
        themeClass.setId(id);
        String coverImageUrl = launFileCrudService.fileUpload(coverImage);
        if (coverImageUrl == null) {
            ResultData resultData = new ResultData(ErrorStatus.WIDGETUPLOAD_ERROR.status(), ErrorStatus.WIDGETUPLOAD_ERROR.message());
            return ResponseEntity.ok(resultData);
        } else {
            themeClass.setCoverImage(coverImageUrl);
        }
        int i = LauncThemeClassMapper.updateByThemClassId(themeClass);
        if (i == 1) {
            ResultData resultData = new ResultData();
            return ResponseEntity.ok(resultData);
        } else {
            ResultData resultData = new ResultData(400, "更新失败");
            return ResponseEntity.ok(resultData);
        }

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
        //LauncThemeClassification ThemeClass = new LauncThemeClassification();
        //ThemeClass.setClassificationId(themeClassId);
        //LauncThemeClassMapper.delete(ThemeClass);//真删除
       /* if (SaasHeaderContextV1.getUserType() == 0) {// 如果为最高管理员,直接删除
            LauncThemeClassMapper.deleteByThemClassId(id, null);
        } else {
            LauncThemeClassMapper.deleteByThemClassId(id, SaasHeaderContextV1.getTenantId() + "");
        }*/
        LauncThemeClassMapper.deleteByThemClassId(id, SaasHeaderContextV1.getTenantId() + "");
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
        for (LauncThemeClassVo launcTheme : launcThemeClass) {
            json.put("classificationName", launcTheme.getClassificationName());
            json.put("coverImage", launcTheme.getCoverImage());
        }
        launThemeService.query(null,id,null,null,pageNum,pageSize);
        PageInfo<LaunThemeVo> query = launThemeService.query(null, id, null, null, pageNum, pageSize);
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
    public ResponseEntity<ResultData> seThemeClassList(String shelfStatus, String classificationName, int pageNum, int pageSize) {
        Integer tenantId = SaasHeaderContextV1.getTenantId();
        PageHelper.startPage(pageNum, pageSize);
        if (HStringUtlis.isNotEmpty(classificationName)) {
            classificationName = "%" + classificationName + "%";
        }
        LauncThemeClassVo themeClass = new LauncThemeClassVo();
       /* if (SaasHeaderContextV1.getUserType() == 1) {// 为1的时候为普通租户
            themeClass.setTenantId(tenantId + "");
        }*/
        themeClass.setTenantId(tenantId + "");
        themeClass.setClassificationName(classificationName);
        themeClass.setDisable(1);
        themeClass.setShelfStatus(shelfStatus);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClass);
        PageInfo<LauncThemeClassVo> PageInfo = new PageInfo<>(launcThemeClass);
        ResultData resultData = new ResultData();
        resultData.setData(PageInfo);
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
        Integer tenantId = SaasHeaderContextV1.getTenantId();
        // 租户id为空,不行
        /*if (SaasHeaderContextV1.getUserType() == 0) {// 最高管理员直接修改
            LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
            themeClassVo.setUpdateDate(new Date());
            themeClassVo.setShelfStatus(shelfStatus);
            LauncThemeClassMapper.updateByThemClassId(themeClassVo);
        } else {*/
            LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
            themeClassVo.setUpdateDate(new Date());
            themeClassVo.setTenantId(tenantId + "");
            themeClassVo.setId(id);
            themeClassVo.setShelfStatus(shelfStatus);
            int i = LauncThemeClassMapper.updateByThemClassId(themeClassVo);
            log.info("租户上下架主题分类>>>>>>>>>>>>>>>>租户添加" + i + ">>>>" + new Date());
           /*if (i==0) {
                 themeClassVo = new LauncThemeClassVo();
                 themeClassVo.setTenantId(tenantId+"");
                 themeClassVo.setDisable(1);
                 themeClassVo.setId(id);
                 List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
                 if (launcThemeClass.isEmpty()){
                     ResultData resultData = new ResultData(400,"该租户无权操作此分类");
                     return ResponseEntity.ok(resultData);
                 }
             }*/
        //}
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
    }

    /**
     * 权重排序
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
     * 更新分类下的主题总数(供删除(批量删除)主题，添加主题等接口调用)
     *
     * @return
     * @Author zhaodong
     * @Date 15:42 2018/12/21
     * @Param
     **/
    @Override
    public String upThemeClassCount(int status, String id) {
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setId(id);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        if (!launcThemeClass.isEmpty()) {
            int quantity = launcThemeClass.get(0).getQuantity();
            LauncThemeClassVo themeClassVo1 = new LauncThemeClassVo();
            themeClassVo1.setId(id);
            themeClassVo1.setUpdateDate(new Date());
            if (status == 1) {// 代表添加
                log.info("添加主题>>>>>>>>>>>>>>>>添加主题使得分类数量增加>>>>" + new Date());
                quantity = quantity + 1;
                themeClassVo1.setQuantity(quantity);
            } else if (status == 0){// 删除
                log.info("删除主题>>>>>>>>>>>>>>>>删除主题使得分类数量减少>>>>" + new Date());
                quantity = quantity - 1;
                themeClassVo1.setQuantity(quantity);
            } else {
                return "error";
            }
            LauncThemeClassMapper.updateByThemClassId(themeClassVo1);
            return "OK";
        } else {
            log.info("该主题分类不存在>>>>>>>>>>>>>>>>该主题分类不存在>>>>>>>>>>>>>>>>添加删除主题影响分类主题数>>>>" + new Date());
            return "error";
        }

    }

    /**
     * 更新该分类下的以上架的主题数(供下架(批量下架)主题，上架(批量上架主题)主题等接口调用)
     *
     * @return
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     **/
    @Override
    public String upThemeClassCountUpOrDown(int status, String id) {
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setId(id);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        if (!launcThemeClass.isEmpty()) {
            int shelfCount = launcThemeClass.get(0).getShelfCount();
            themeClassVo = new LauncThemeClassVo();
            themeClassVo.setId(id);
            themeClassVo.setUpdateDate(new Date());
            if (status == 1) {// 代表添加
                log.info("上架主题>>>>>>>>>>>>>>>>上架主题使得分类中上架主题数增加>>>>" + new Date());
                shelfCount = shelfCount + 1;
                themeClassVo.setShelfCount(shelfCount);
            } else if (status == 0){// 删除
                log.info("下架主题>>>>>>>>>>>>>>>>下架主题使得分类中下架主题数减少>>>>" + new Date());
                shelfCount = shelfCount - 1;
                themeClassVo.setShelfCount(shelfCount);
            } else {
                return "error";
            }
            LauncThemeClassMapper.updateByThemClassId(themeClassVo);
            return "ok";
        } else {
            log.info("该主题分类不存在>>>>>>>>>>>>>>>>该主题分类不存在>>>>>>>>>>>>>>>>上下架影响主题>>>>" + new Date());
            return "error";
        }
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
