package com.pactera.business.dao;
import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunCarousel;
import com.pactera.vo.LaunCarouselInfoVo;
import com.pactera.vo.LaunCarouselListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图mapper
 * @Author zhaodong
 * @Date 11:29 2019/2/28
 **/
public interface LaunCarouselMapper extends BaseMapper<LaunCarousel> {

    /**
     * @Author zhaodong
     * @Date 9:35 2019/3/1
     * @Param
     * @return
     **/
    List<LaunCarouselListVo> carouselList(LaunCarousel launCarousel);

    /**
     * 查询的轮播的文件列表
     * @Author zhaodong
     * @Date 15:39 2019/3/1
     * @Param
     * @return
     **/
    List<LaunCarouselInfoVo> seCarouseInfo(@Param("id") String launCarouselId);
}
