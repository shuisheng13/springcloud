package com.pactera.business.dao;
import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunCarouselImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 轮播图文件mapper
 * @Author zhaodong
 * @Date 13:43 2019/2/28
 **/
public interface LaunCarouselImageMapper extends BaseMapper<LaunCarouselImages> {

     /**
      * 批量添加轮播图文件
      * @Author zhaodong
      * @Date 14:52 2019/2/28
      * @Param
      * @return
      **/
    int insertLaunCarouselImage(@Param("list") List<Map> list);
}
