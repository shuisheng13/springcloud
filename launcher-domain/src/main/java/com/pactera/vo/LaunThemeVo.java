package com.pactera.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LaunThemeVo implements Serializable {

	private static final long serialVersionUID = 8860802962131092316L;

	private @ApiModelProperty("主键id") String id;
	private @ApiModelProperty("排序权重") Integer sort;
	private @ApiModelProperty("最低试用版本") double version;
	private @ApiModelProperty("分辨率长") Long longResolution;
	private @ApiModelProperty("分辨率宽") Long wideResolution;
	private @ApiModelProperty("主题名称") String title;
	private @ApiModelProperty("下载量") Integer downloadCount;
	private @ApiModelProperty("应用量") Integer usedCount;
	private @ApiModelProperty("创建人id") String createId;
	private @ApiModelProperty("创建人") String creator;
	private @ApiModelProperty("主题描述") String description;
	private @ApiModelProperty("作者") String author;
	private @ApiModelProperty("发布时间") Date releaseTime;


	private @ApiModelProperty("主题大小") Long fileSize;
	private @ApiModelProperty("开始时间") Date startTime;
	private @ApiModelProperty("结束时间") Date endTime;
	private @ApiModelProperty("主题底屏json") String basicJson;
	private @ApiModelProperty("主题信息组件json") String widgetJson;
	private @ApiModelProperty("主题信息组件json") String themeJson;
	//private @ApiModelProperty("页面传值用的渠道名称") String channelName;
    private String typeId;
	private @ApiModelProperty("分类名称") String typeName;
	//private @ApiModelProperty("页面传值用的版本名称") String versionName;
	private @ApiModelProperty("主题包地址") String zipUrl;
	private @ApiModelProperty("主题状态1:未上架2:已上架3:已下架4:删除") Integer status;
	private @ApiModelProperty("价格") BigDecimal price;
	private Boolean recommend;
	private Integer recommendSort;
	private Integer addition;
	private String versionName;
	private Long layoutId;
	private String layoutName;
}
