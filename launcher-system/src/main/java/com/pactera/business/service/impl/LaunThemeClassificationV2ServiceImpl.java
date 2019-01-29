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
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeClassificationV2;
import com.pactera.result.ResultData;
import com.pactera.utlis.HStringUtlis;
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
import java.util.*;

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
        String tenantId2 = SaasHeaderContextV1.getTenantId();
        //themeClassVo.setTenantId(tenantId2); //现在处理成一个租户，重名问题为查询所有租户
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
        themeClass.setTenantId(tenantId2 + "");// 存租户id，如今并没有什么卵用，就是处理成租户和管理员是一个，也就是说是租户
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
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id, String coverImage) {
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
        String tenantId2 = SaasHeaderContextV1.getTenantId();
        //themeClassVo.setTenantId(tenantId2); // 你懂得，为啥不带了
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
        //LauncThemeClassification ThemeClass = new LauncThemeClassification();
        //ThemeClass.setClassificationId(themeClassId);
        //LauncThemeClassMapper.delete(ThemeClass);//真删除
       /* if (SaasHeaderContextV1.getUserType() == 0) {// 如果为最高管理员,直接删除
            LauncThemeClassMapper.deleteByThemClassId(id, null);
        } else {
            LauncThemeClassMapper.deleteByThemClassId(id, SaasHeaderContextV1.getTenantId() + "");
        }*/
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
        for (LauncThemeClassVo launcTheme : launcThemeClass) {
            json.put("classificationName", launcTheme.getClassificationName());
            json.put("coverImage", launcTheme.getCoverImage());
        }
        LaunPage<LaunThemeVo> query = launThemeService.query(id,null, null, pageNum, pageSize);
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
        String tenantId = SaasHeaderContextV1.getTenantId();
        PageHelper.startPage(pageNum, pageSize);
        LauncThemeClassVo themeClass = new LauncThemeClassVo();
       /* if (SaasHeaderContextV1.getUserType() == 1) {// 为1的时候为普通租户
            themeClass.setTenantId(tenantId + "");
        }*/
        //themeClass.setTenantId(tenantId); //这版为所有租户都能看到
        themeClass.setClassificationName(classificationName);
        themeClass.setDisable(1);
        themeClass.setShelfStatus(shelfStatus);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClass);
        if(!launcThemeClass.isEmpty()) {
            //主题分类id给我组合
            List<String> listId = new ArrayList<>();
            launcThemeClass.forEach(la -> { listId.add(la.getId()); });
            //查询一下分类下面有多少的主题，有多少上架的，多少下架的。
            List<Map> maps = LauncThemeClassMapper.seQuantityTheme(listId);
            // 查询分类下面有多少上架的主题
            List<Map> maps1 = LauncThemeClassMapper.seQuantityThemeUp(listId);
            for (LauncThemeClassVo vo : launcThemeClass) {
                String id = vo.getId();
                for (Map quantity : maps) {
                    if (quantity.containsValue(id)) {
                        vo.setQuantity(Integer.parseInt(String.valueOf((long) quantity.get("quantity"))));
                    }
                }
                for (Map shelfCount : maps1) {
                    if (shelfCount.containsValue(id)) {
                        vo.setShelfCount(Integer.parseInt(String.valueOf((long) shelfCount.get("shelfCount"))));
                    }
                }
            }
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
        String tenantId = SaasHeaderContextV1.getTenantId();
        // 租户id为空,不行
        /*if (SaasHeaderContextV1.getUserType() == 0) {// 最高管理员直接修改
            LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
            themeClassVo.setUpdateDate(new Date());
            themeClassVo.setShelfStatus(shelfStatus);
            LauncThemeClassMapper.updateByThemClassId(themeClassVo);
        } else {*/
            LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
            themeClassVo.setUpdateDate(new Date());
            themeClassVo.setShelfCount(-2);
            themeClassVo.setQuantity(-2);
           //themeClassVo.setTenantId(tenantId); //限制条件去掉，就因为没有租户，只有管理员
            themeClassVo.setId(id);
            themeClassVo.setShelfStatus(shelfStatus);
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
    public ResponseEntity<ResultData> themeClassByTid(){
        LauncThemeClassVo vo = new LauncThemeClassVo();
        vo.setDisable(1);
        //vo.setTenantId(SaasHeaderContextV1.getTenantId()+""); //都能查询到，没有租户的概念
        List<LauncThemeClassVo> launcThemeClassVos = LauncThemeClassMapper.selectLauncThemeClassVo(vo);
        List<JSONObject> list = new ArrayList<>();
        for (LauncThemeClassVo laun:launcThemeClassVos){
            JSONObject json1 = new JSONObject();
            json1.put("themeClassName",laun.getClassificationName());
            json1.put("classNameId",laun.getId());
            list.add(json1);
        }
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
