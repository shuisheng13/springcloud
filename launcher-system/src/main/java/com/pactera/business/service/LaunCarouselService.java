package com.pactera.business.service;

import com.pactera.result.ResultData;
import org.springframework.http.ResponseEntity;

/**
 * 轮播图的业务
 * @Author zhaodong
 * @Date 10:07 2019/2/28
 **/
public interface LaunCarouselService {

    /**
     * 添加/编辑 轮播图
     * @Author zhaodong
     * @Date 13:48 2019/2/28
     * @Param carouselJson
     * @return ResponseEntity
     **/
    ResponseEntity<ResultData> addCarousel(String carouselJsonAddOrUp,int type);

    /**
     * 删除轮播图
     * @Author zhaodong
     * @Date 16:48 2019/2/28
     * @Param id
     * @return
     **/
    ResponseEntity<ResultData> deCarousel(String id);

    /**
     * 轮播图列表
     * @Author zhaodong
     * @Date 17:07 2019/2/28
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> carouselList(int formatId, double version, String status, String position, String title, int pageIndex, int pageSize);

    /**
     * 轮播图上架下架
     * @Author zhaodong
     * @Date 10:11 2019/3/1
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> carouselUpOrDown(String id, String status);

    /**
     * 轮播图详情
     * @Author zhaodong
     * @Date 14:24 2019/3/1
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> carouselInfoById(String id);
}
