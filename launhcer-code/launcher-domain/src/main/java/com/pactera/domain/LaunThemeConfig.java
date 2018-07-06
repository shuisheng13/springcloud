package com.pactera.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("主题widget配置实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunThemeConfig extends LaunBase {

	private static final long serialVersionUID = -3836221880420696400L;
	private @ApiModelProperty("是否使用相对布局1:是2:否") int relativeLayout;
	private @ApiModelProperty("配置分类1:滚动面板2:widget3:海报") int type;
	private @ApiModelProperty("widgetId") Long widgetId;
	private @ApiModelProperty("配置名称") String configName;
	private @ApiModelProperty("是否锁定宽高比") String widthToHeight;
	private @ApiModelProperty("横向宽") String width;
	private @ApiModelProperty("横向宽权重") String widthWieghts;
	private @ApiModelProperty("纵向高") String height;
	private @ApiModelProperty("纵向高权重") String heightWieghts;
	private @ApiModelProperty("主题id") Long launThemeId;
	private @ApiModelProperty("left , top, right，bottom宫格坐标（左、上、右、下）") String lattice;
	private @ApiModelProperty("json资源") String property;
	private @ApiModelProperty("父节点id") Long parentId;

	@Transient
	private @ApiModelProperty("widget图片") String prewImage;
	@Transient
	private @ApiModelProperty("widget配置详情list") List<LaunWidgetWidthToHight> launWidgetWidthToHightList;
}
