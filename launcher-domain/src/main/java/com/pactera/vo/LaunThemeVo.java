package com.pactera.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaunThemeVo implements Serializable {

	private static final long serialVersionUID = 8860802962131092316L;

	private @ApiModelProperty("主键id") Long id;
	private @ApiModelProperty("渠道id") Long creatorChannelId;
	private @ApiModelProperty("主题名称") String title;
	private @ApiModelProperty("主题大小") Long fileSize;
	private @ApiModelProperty("开始时间") Date startTime;
	private @ApiModelProperty("结束时间") Date endTime;
	private @ApiModelProperty("主题底屏json") String basicJson;
	private @ApiModelProperty("主题信息组件json") String widgetJson;
	private @ApiModelProperty("主题信息组件json") String themeJson;
	private @ApiModelProperty("页面传值用的渠道名称") String channelName;
	private @ApiModelProperty("页面传值用的分类名称") String typeName;
	private @ApiModelProperty("页面传值用的版本名称") String versionName;
	private @ApiModelProperty("主题包地址") String zipUrl;
	private @ApiModelProperty("主题状态1:未上架2:已上架3:已下架4:删除") Integer status;
}
