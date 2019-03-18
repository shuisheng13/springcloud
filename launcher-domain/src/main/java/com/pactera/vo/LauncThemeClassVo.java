package com.pactera.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 主题分类
 * @Author zhaodong
 * @Description
 * @Date 2018年12月20日 14:04
 */
@Data
@Accessors(chain = true)
public class LauncThemeClassVo {

    private String id;

    //private String classificationId;

    private String classificationName;

    private int sort;

    private int quantity;

    private int shelfCount;

    private String coverImage;

    private String creator;

    private String shelfStatus;

    private String tenantId;

    private int disable;

    private Long formatId;

    private Date createDate;

    private Date updateDate;
}
