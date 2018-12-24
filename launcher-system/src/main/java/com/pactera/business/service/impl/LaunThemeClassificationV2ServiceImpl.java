package com.pactera.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunThemeClassificationV2Mapper;
import com.pactera.business.service.LaunFileCrudService;
import com.pactera.business.service.LauncThemeClassificationV2Service;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.domain.LaunThemeClassificationV2;
import com.pactera.result.ResultData;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.vo.LauncThemeClassVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 *  主题分类管理
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

    /**
     * 主题分类添加
     * @Author zhaodong
     * @Date 12:07 2018/12/20
     * @Param themeClassName,themeClassName,coverImage
     * @return ResponseEntity<ResultData>
     **/
    @Override
    public ResponseEntity<ResultData> addthemeClass(String themeClassName, String apiKey, MultipartFile coverImage){
        //TODO 是否为合法用户
        if(false){
            ResultData resultData = new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message());
            return ResponseEntity.ok(resultData);
        }
        LaunThemeClassificationV2 themeClass = new LaunThemeClassificationV2();
        // 封装属性
        themeClass.setId(IdUtlis.Id("ZTFL","xijinping"));
        if (themeClassName.length()>16){
            ResultData resultData = new ResultData(500,"分类名不得超过8个汉字");
            return ResponseEntity.ok(resultData);
        }
        themeClass.setClassificationName(themeClassName);
        // TODO 对接租户
        themeClass.setCreator("周恩来");
        themeClass.setTenantId("112345678");
        themeClass.setDisable(1);
        themeClass.setQuantity(0);
        themeClass.setShelfCount(0);
        themeClass.setShelfStatus("0");
        themeClass.setSort(1);
        themeClass.setCreateDate(new Date());
        themeClass.setUpdateDate(new Date());
        String coverImageUrl = launFileCrudService.fileUpload(coverImage);
        if (coverImageUrl == null){
            ResultData resultData = new ResultData(ErrorStatus.WIDGETUPLOAD_ERROR.status(),ErrorStatus.WIDGETUPLOAD_ERROR.message());
            return ResponseEntity.ok(resultData);
        }else {
            themeClass.setCoverImage(coverImageUrl);
        }
        //存表
        int insert = LauncThemeClassMapper.insert(themeClass);
        if (insert==1){
            ResultData resultData = new ResultData();
            return ResponseEntity.ok(resultData);
        }else{
            ResultData resultData = new ResultData(500,"保存失败");
            return ResponseEntity.ok(resultData);
        }


    }

    /**
     * 编辑主题分类
     * @Author zhaodong
     * @Date 11:42 2018/12/20
     * @Param themeClassName,themeClassId,coverImage
     * @return ResponseEntity<ResultData>
     **/
    @Override
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id,MultipartFile coverImage){
        // TODO 判断用户是否合法，用户是否修改的权限
        LauncThemeClassVo themeClass = new LauncThemeClassVo();
        themeClass.setUpdateDate(new Date());
        themeClass.setClassificationName(themeClassName);
        themeClass.setId(id);
        String coverImageUrl = launFileCrudService.fileUpload(coverImage);
        if (coverImageUrl == null){
            ResultData resultData = new ResultData(ErrorStatus.WIDGETUPLOAD_ERROR.status(),ErrorStatus.WIDGETUPLOAD_ERROR.message());
            return ResponseEntity.ok(resultData);
        }else {
            themeClass.setCoverImage(coverImageUrl);
        }
        int i = LauncThemeClassMapper.updateByThemClassId(themeClass);
        if (i==1){
            ResultData resultData = new ResultData();
            return ResponseEntity.ok(resultData);
        }else{
            ResultData resultData = new ResultData(500,"更新失败");
            return ResponseEntity.ok(resultData);
        }

    }

     /**
      * 删除主题分类
      * @Author zhaodong
      * @Date 14:27 2018/12/20
      * @Param themeClassId
      * @return
      **/
     @Override
     public ResponseEntity<ResultData> deThemeClass(String id,String apiKey){
         //TODO 是否为合法用户
         if(false){
             ResultData resultData = new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message());
             return ResponseEntity.ok(resultData);
         }
        //LauncThemeClassification ThemeClass = new LauncThemeClassification();
        //ThemeClass.setClassificationId(themeClassId);
        //LauncThemeClassMapper.delete(ThemeClass);//真删除
        //TODO 是否判断删除是否是自己的主题
        LauncThemeClassMapper.deleteByThemClassId(id);
        ResultData resultData = new ResultData();
        return ResponseEntity.ok(resultData);
     }

     /**
      * 查看主题分类
      * @Author zhaodong
      * @Date 16:26 2018/12/20
      * @Param themeClassId
      * @return
      **/
     @Override
     public ResponseEntity<ResultData> seThemeClass(String id){
         LauncThemeClassVo  themeClassVo = new LauncThemeClassVo();
         themeClassVo.setDisable(1);
         themeClassVo.setId(id);
         List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
         JSONObject json = new JSONObject();
         for (LauncThemeClassVo launcTheme:launcThemeClass){
             json.put("classificationName",launcTheme.getClassificationName());
             json.put("coverImage",launcTheme.getCoverImage());
         }
         ResultData resultData = new ResultData();
         resultData.setData(json);
         return ResponseEntity.ok(resultData);
     }

     /**
      * 分类列表
      * @Author zhaodong
      * @Date 16:43 2018/12/20
      * @Param
      * @return 
      **/
     @Override
     public ResponseEntity<ResultData> seThemeClassList(String apiKey,String shelfStatus,String classificationName,int pageNum,int pageSize){
        //TODO 是否为合法用户
         if(false){
             ResultData resultData = new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message());
             return ResponseEntity.ok(resultData);
         }
         PageHelper.startPage(pageNum, pageSize);
         if (HStringUtlis.isNotEmpty(classificationName)) {
             classificationName = "%" + classificationName + "%";
         }
         LauncThemeClassVo themeClass = new LauncThemeClassVo();
         //TODO apiKey判断是否为最高管理员，还是普通租户
         if(false){
             themeClass.setTenantId("xijinping");
         }
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
      * @Author zhaodong
      * @Date 13:47 2018/12/21
      * @Param
      * @return
      **/
     @Override
     public ResponseEntity<ResultData> themeClassUpOrDown(String apiKey,String shelfStatus,String id){
         //TODO 是否为合法租户，对接租户的接口
         if(false){
             ResultData resultData = new ResultData(ErrorStatus.NOT_APIKEY.status(),ErrorStatus.NOT_APIKEY.message());
             return ResponseEntity.ok(resultData);
         }
         if (apiKey.equals("zhaodong")){// 最高管理员直接修改
             LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
             themeClassVo.setUpdateDate(new Date());
             themeClassVo.setShelfStatus(shelfStatus);
             LauncThemeClassMapper.updateByThemClassId(themeClassVo);
         }else{
             LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
             themeClassVo.setUpdateDate(new Date());
             themeClassVo.setId(id);
             themeClassVo.setShelfStatus(shelfStatus);
             int i = LauncThemeClassMapper.updateByThemClassId(themeClassVo);
             if (i==0) {
                 themeClassVo = new LauncThemeClassVo();
                 themeClassVo.setTenantId("xijinping");
                 themeClassVo.setDisable(1);
                 themeClassVo.setId(id);
                 List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
                 if (launcThemeClass.isEmpty()){
                     ResultData resultData = new ResultData(500,"该租户无权操作此分类");
                     return ResponseEntity.ok(resultData);
                 }
             }
         }
         ResultData resultData = new ResultData();
         return ResponseEntity.ok(resultData);
     }

     /**
      * 主题类别下的主题数量判断
      * @Author zhaodong
      * @Date 15:01 2018/12/21
      * @Param
      * @return
      **/
     @Override
     public ResponseEntity<ResultData> themeClassIsUpOrDown(String id){

         LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
         themeClassVo.setDisable(1);
         themeClassVo.setId(id);
         List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
         ResultData resultData = new ResultData();
         if(!launcThemeClass.isEmpty()){
             JSONObject json = new JSONObject();
             if (launcThemeClass.get(0).getShelfCount()==0)
                 json.put("shelfCount",false);
             else
                 json.put("shelfCount",true);
             resultData.setData(json);
         }else {
             resultData = new ResultData(500,"不存在该主题类别");
         }
         return ResponseEntity.ok(resultData);
     }

     /**
      * 更新分类下的主题总数(供删除(批量删除)主题，添加主题等接口调用)
      * @Author zhaodong
      * @Date 15:42 2018/12/21
      * @Param
      * @return
      **/
     @Override
     public String upThemeClassCount(int status,String id,int num){
         //TODO 添加的上架主题数量小于当前的主题总数

         LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
         themeClassVo.setDisable(1);
         themeClassVo.setId(id);
         List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
         if (!launcThemeClass.isEmpty()){
             int quantity = launcThemeClass.get(0).getQuantity();
             LauncThemeClassVo themeClassVo1 = new LauncThemeClassVo();
             themeClassVo1.setId(id);
             themeClassVo1.setUpdateDate(new Date());
             if (status==1){// 代表添加
                 quantity= quantity+num;
                 themeClassVo1.setQuantity(quantity);
             }else{// 删除
                 quantity= quantity-num;
                 themeClassVo1.setQuantity(quantity);
             }
             LauncThemeClassMapper.updateByThemClassId(themeClassVo1);
             return "OK";
         }else {
             return "error";
         }

    }

    /**
     * 更新该分类下的以上架的主题数(供下架(批量下架)主题，上架(批量上架主题)主题等接口调用)
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     * @return
     **/
    @Override
    public String upThemeClassCountUpOrDown(int status,String id,int num){
        LauncThemeClassVo themeClassVo = new LauncThemeClassVo();
        themeClassVo.setDisable(1);
        themeClassVo.setId(id);
        List<LauncThemeClassVo> launcThemeClass = LauncThemeClassMapper.selectLauncThemeClassVo(themeClassVo);
        if (!launcThemeClass.isEmpty()){
            int shelfCount = launcThemeClass.get(0).getShelfCount();
            themeClassVo = new LauncThemeClassVo();
            themeClassVo.setId(id);
            themeClassVo.setUpdateDate(new Date());
            if (status==1){// 代表添加
                shelfCount= shelfCount+num;
                themeClassVo.setShelfCount(shelfCount);
            }else{// 删除
                shelfCount= shelfCount-num;
                themeClassVo.setShelfCount(shelfCount);
            }
            LauncThemeClassMapper.updateByThemClassId(themeClassVo);
            return "OK";
        }else {
            return "error";
        }
    }



}
