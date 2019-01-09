package com.pactera.business.service;

import com.pactera.result.ResultData;
import org.springframework.http.ResponseEntity;

/**
 * @Author zhaodong
 * @Date 10:21 2019/1/7
 **/
public interface LaunListService {

    /**
     * 主题分类列表
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themeclasslist2(String apiKey);

    /**
     * 1-全部主题，2-主题排行，3-推荐主题
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themTopAndAll(String apiKey,int status, int pageNum, int pageSize);

    /**
     * 分类下的主题列表
     * @Author zhaodong
     * @Date 10:10 2019/1/7
     * @Param
     * @return
     **/
    ResponseEntity<ResultData> themTopAndByClassId(String apiKey, String id, int pageNum, int pageSize);

}
