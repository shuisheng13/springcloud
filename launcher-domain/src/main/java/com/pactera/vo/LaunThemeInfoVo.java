package com.pactera.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 通过主题分类查询主题的实体
 * @Author zhaodong
 * @Date $ $
 */
@Getter
@Setter
public class LaunThemeInfoVo {

    //主题id
    private String id;

    //版本号
    private String version;

    //主题名
    private String title;

    //起止时间
    private Date endTime;

    //下载量
    private int downloadCount;

    //使用量
    private int usedCount;

    //作者
    private String author;

    //1-上架，0-未上架
    private int status;

}
