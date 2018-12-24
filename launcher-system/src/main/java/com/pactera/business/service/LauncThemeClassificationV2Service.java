package com.pactera.business.service;

import com.pactera.result.ResultData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    ResponseEntity<ResultData> addthemeClass(String themeClassName, String apiKey, MultipartFile coverImage);

    /**
     * 编辑主题分类
     * @Author zhaodong
     * @Date 11:42 2018/12/20
     * @Param themeClassName,themeClassId,coverImage
     * @return ResponseEntity<ResultData>
     **/
    ResponseEntity<ResultData> upThemeClass(String themeClassName, String themeClassId,MultipartFile coverImage);

    /**
     * 删除主题
     * @Author zhaodong
     * @Date 14:26 2018/12/20
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> deThemeClass(String id,String apiKey);

    /**
     * 查看主题分类
     * @Author zhaodong
     * @Date 16:21 2018/12/20
     * @Param themeClassId
     * @return
     **/
    ResponseEntity<ResultData> seThemeClass(String id);

    /**
     * 所有列表接口
     * @Author zhaodong
     * @Date 16:38 2018/12/20
     * @Param apiKey
     * @return
     **/
    ResponseEntity<ResultData> seThemeClassList(String apiKey,String shelfStatus,String classificationName,int pageNum,int pageSize);

    /**
     *  上架或下架主题
     * @Author zhaodong
     * @Date 13:42 2018/12/21
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeClassUpOrDown(String apiKey,String shelfStatus,String id);

    /**
     * 主题是否上架或下架
     * @Author zhaodong
     * @Date 15:01 2018/12/21
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeClassIsUpOrDown(String id);

    /**
     * 更新分类下的主题总数(供删除(批量删除)主题，添加主题等接口调用(1-添加,0-删除))
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     * @return
     **/
    String upThemeClassCount(int status,String id,int num);

    /**
     * 更新该分类下的以上架的主题数(供下架(批量下架)主题，上架(批量上架主题)主题等接口调用,(1-上架,0-下架))
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     * @return
     **/
    String upThemeClassCountUpOrDown(int status,String id,int num);
}
