package com.pactera.po;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保存轮播图实体
 * @Author zhaodong
 * @Date 13:56 2019/2/28
 **/
@Data
public class LaunCarouselSaOrUpPo {

    private String id;

    private String title;

    private int formatId;

    private double version;

    private String position;

    private long startTime;

    private long endTime;

    private int interval;

    private List<Map> carouselList;

}
