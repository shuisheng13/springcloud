package com.pactera.vo;

import lombok.Data;

/**
 * 轮播图列表信息
 * @Author zhaodong
 * @Date 9:17 2019/3/1
 **/
@Data
public class LaunCarouselListVo {

    private String id;

    private String title;

    private double version;

    private String status;

    private int position;

    private String startEndTime;

}
