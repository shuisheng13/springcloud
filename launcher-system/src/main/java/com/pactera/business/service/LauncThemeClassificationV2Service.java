package com.pactera.business.service;

import com.pactera.result.ResultData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  主题分类管理
 * @author:zhaodong
 * @since:2018年12月19日 14:29:39
 */
public interface LauncThemeClassificationV2Service {

    /**
     * 主题分类添加
     * @Author zhaodong
     * @Date 12:07 2018/12/20
     * @Param themeClassName,themeClassName,coverImage
     * @return ResponseEntity<ResultData>
     **/
    ResponseEntity<ResultData> addthemeClass(String themeClassName, MultipartFile coverImage, int formatId);

    /**
     * 编辑主题分类
     * @Author zhaodong
     * @Date 11:42 2018/12/20
     * @Param themeClassName,themeClassId,coverImage
     * @return ResponseEntity<ResultData>
     **/
    ResponseEntity<ResultData> upThemeClass(String themeClassName, String themeClassId,String coverImage);

    /**
     * 删除主题
     * @Author zhaodong
     * @Date 14:26 2018/12/20
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> deThemeClass(String id);

    /**
     * 查看主题分类
     * @Author zhaodong
     * @Date 16:21 2018/12/20
     * @Param themeClassId
     * @return
     **/
    ResponseEntity<ResultData> seThemeClass(String id, int pageNum,int pageSize);

    /**
     * 所有列表接口
     * @Author zhaodong
     * @Date 16:38 2018/12/20
     * @Param apiKey
     * @return
     **/
    ResponseEntity<ResultData> seThemeClassList(String shelfStatus,String classificationName,int pageNum,int pageSizet,Integer formatId);

    /**
     *  上架或下架主题
     * @Author zhaodong
     * @Date 13:42 2018/12/21
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeClassUpOrDown(String shelfStatus,String id);

    /**
     * 主题是否上架或下架
     * @Author zhaodong
     * @Date 15:01 2018/12/21
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeClassIsUpOrDown(String id);

    /**
     * 权重进行排序
     * @Author zhaodong
     * @Date 16:34 2018/12/26
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> orderThemeClass(String sortOrder);

    /**
     * 主题应用下拉的分类列表
     * @Author zhaodong
     * @Date 17:15 2019/1/14
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeClassByTid(int formatId);

     /**
      * 单个权重排序
      * @Author zhaodong
      * @Date 11:04 2019/1/17
      * @Param
      * @return
      **/
    ResponseEntity<ResultData> updateThemTypeOrder(String id,int order);
}
