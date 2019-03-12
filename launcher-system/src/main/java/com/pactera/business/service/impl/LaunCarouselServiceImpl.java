package com.pactera.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.business.dao.LaunCarouselImageMapper;
import com.pactera.business.dao.LaunCarouselMapper;
import com.pactera.business.service.LaunCarouselService;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.config.header.TenantFacadeV1;
import com.pactera.domain.LaunCarousel;
import com.pactera.domain.LaunCarouselImages;
import com.pactera.po.LaunCarouselSaOrUpPo;
import com.pactera.result.ResultData;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunCarouselInfoVo;
import com.pactera.vo.LaunCarouselListVo;
import com.pactera.vo.LaunPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 轮播图逻辑
 * @Author zhaodong
 * @Date 11:11 2019/2/28
 **/
@Service
@Slf4j
public class LaunCarouselServiceImpl implements LaunCarouselService {

    @Resource
    private LaunCarouselMapper launCarouselMapper;

    @Resource
    private LaunCarouselImageMapper launCarouselImageMapper;

    @Resource
    private TenantFacadeV1 tenantFacadeV1;

    /**
     * 添加/更新 轮播图（type=0添加，type=1更新）
     * @Author zhaodong
     * @Date 13:50 2019/2/28
     * @Param
     * @return
     **/
     @Override
     public ResponseEntity<ResultData> addCarousel(String carouselJsonaAdd,int type) {
         LaunCarouselSaOrUpPo launCarousel = JsonUtils.jsonToClass(carouselJsonaAdd, LaunCarouselSaOrUpPo.class);
         //汉字的字数校验
         if (launCarousel.getTitle().length()>8){return ResponseEntity.ok(new ResultData(ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_NUM.status(),ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_NUM.message())); }
         //轮播图数量校验
         if(launCarousel.getCarouselList().size()>10||launCarousel.getCarouselList().size()<3){
             return ResponseEntity.ok(new ResultData(ErrorStatus.SERVICE_ERROR_CAROUSEL_IMAGES_NUM.status(),ErrorStatus.SERVICE_ERROR_CAROUSEL_IMAGES_NUM.message()));
         }
         //重名校验
         LaunCarousel launCarouselName = new LaunCarousel().setTitle(launCarousel.getTitle());
         List<LaunCarousel> select = launCarouselMapper.select(launCarouselName);
         if (!select.isEmpty()){
             if (type==0){//添加有重名就不成
                return ResponseEntity.ok(new ResultData(ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_SAME.status(),ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_SAME.message()));
             }else {// 更新的时候看看是不是自己的名字
                 if (!launCarouselMapper.selectByPrimaryKey(launCarousel.getId()).getTitle().equals(launCarousel.getTitle())){
                     return ResponseEntity.ok(new ResultData(ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_SAME.status(),ErrorStatus.SERVICE_ERROR_CAROUSEL_NAME_SAME.message()));
                 }
             }
         }
         String id="";
         if (type==0) {
             id = ThemeClassId();
         }else{
             id = launCarousel.getId();
             int i = launCarouselImageMapper.delete(new LaunCarouselImages().setThemeId(id));
             log.info("执行更新轮播图进行删除的轮播文件的数量:{}",i);
         }
         //添加轮播图
         LaunCarousel launCarouselAdd = new LaunCarousel()
                 .setId(id)
                 .setTitle(launCarousel.getTitle())
                 .setStartTime(new Date(launCarousel.getStartTime()))
                 .setEndTime(new Date(launCarousel.getEndTime()))
                 .setInterval(launCarousel.getInterval())
                 .setVersion(launCarousel.getVersion())
                 .setFormatId(launCarousel.getFormatId())
                 .setPosition(launCarousel.getPosition())
                 .setCreateDate(new Date())
                 .setUpdateDate(new Date())
                 .setStatus(type==0?"0":null);
         if (type==0){
             launCarouselMapper.insert(launCarouselAdd);
         }else{
             launCarouselMapper.updateByPrimaryKeySelective(launCarouselAdd);
         }
         //批量添加轮播图文件
         int i = launCarouselImageMapper.insertLaunCarouselImage(launCarousel.getCarouselList());
         log.info("添加了轮播文件为:{}",i);
         return ResponseEntity.ok(new ResultData());
     }

     /**
      * 轮播图列表
      * @Author zhaodong
      * @Date 17:11 2019/2/28
      * @Param
      * @return
      **/
      @Override
      public ResponseEntity<ResultData> carouselList(int formatId, double version, String status, String position, String title, int pageIndex, int pageSize) {
          PageHelper.startPage(pageIndex, pageSize);
          LaunCarousel carousel = new LaunCarousel()
                  .setFormatId(formatId)
                  .setVersion(version)
                  .setStatus(status)
                  .setPosition(position)
                  .setTitle(title);
          List<LaunCarouselListVo> launCarouselListVos = launCarouselMapper.carouselList(carousel);
          PageInfo<LaunCarouselListVo> PageInfo = new PageInfo<>(launCarouselListVos);
          ResultData resultData = new ResultData(new LaunPage(PageInfo, launCarouselListVos));
          return ResponseEntity.ok(resultData);
      }

    /**
     * 轮播图的上架下架
     * @Author zhaodong
     * @Date 10:16 2019/3/1
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> carouselUpOrDown(String id, String status) {
        LaunCarousel launCarousel = new LaunCarousel().setId(id).setStatus(status);
        int i = launCarouselMapper.updateByPrimaryKeySelective(launCarousel);
        return ResponseEntity.ok(new ResultData(i));
    }

    /**
     * 轮播图详情
     * @Author zhaodong
     * @Date 14:26 2019/3/1
     * @Param
     * @return
     **/
    @Override
    public ResponseEntity<ResultData> carouselInfoById(String id) {
        LaunCarousel launCarousel = launCarouselMapper.selectByPrimaryKey(id);
        List<LaunCarouselImages> select = launCarouselImageMapper.select(new LaunCarouselImages().setLaunCarouselId(launCarousel.getId()));
        JSONObject json = new JSONObject();
        json.put("title",launCarousel.getTitle());
        json.put("version",launCarousel.getVersion());
        json.put("status",launCarousel.getStatus());
        json.put("position",launCarousel.getPosition());
        json.put("startEndTime",launCarousel.getStartTime()+"至"+launCarousel.getEndTime());
        json.put("formatId",launCarousel.getFormatId());
        json.put("interval",launCarousel.getInterval());
        //查询主题
        List<LaunCarouselInfoVo> vos = launCarouselMapper.seCarouseInfo(launCarousel.getId());
        vos.forEach(a->{a.setTenantName(tenantFacadeV1.tenantInfoName(Integer.parseInt(a.getTenantName())));});
        json.put("list",vos);
        return ResponseEntity.ok(new ResultData(json));
    }

    /**
     * 删除轮播图
     * @Author zhaodong
     * @Date 16:49 2019/2/28
     * @Param
     * @return
     **/
      @Override
      public ResponseEntity<ResultData> deCarousel(String id) {
          LaunCarousel launCarousel = launCarouselMapper.selectByPrimaryKey(id);
          //删除轮播图主表
          launCarouselMapper.deleteByPrimaryKey(id);
          //删除轮播从表文件
          int i = launCarouselImageMapper.delete(new LaunCarouselImages().setThemeId(id));
          log.info("删除轮播图————————>删除轮播图从表文件个数为:{}",i);
          return ResponseEntity.ok(new ResultData());
      }

      //判断是否有id重复的
      private String ThemeClassId(){
        // 获取id
        String id = IdUtlis.Id("ZLBT", SaasHeaderContextV1.getTenantName());
        LaunCarousel launCarousel = launCarouselMapper.selectByPrimaryKey(id);
        if (launCarousel == null){
            return id;
        }else{
            return this.ThemeClassId();
        }
      }
}
