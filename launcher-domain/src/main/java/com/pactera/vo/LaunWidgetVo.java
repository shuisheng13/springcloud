package com.pactera.vo;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LaunWidgetVo implements Serializable{

	private static final long serialVersionUID = 4732160826805333243L;
	
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("创建时间") Date createDate;
	private @ApiModelProperty("修改时间") Date updateDate;
	private @ApiModelProperty("widget名称") String name;
	private @ApiModelProperty("widget类型 0:基本，1:变体") Integer type;
	private @ApiModelProperty("默认尺寸") String defaultSize;
	private @ApiModelProperty("图片") String prewImage;
	private @ApiModelProperty("分类id") Long category;
	private @ApiModelProperty("版本") Integer version;
	private @ApiModelProperty("描述") String description;
	private @ApiModelProperty("预留字段") String tag;
	private @ApiModelProperty("widget唯一标识") String codeId;
	private @ApiModelProperty("widget属性") String property;
	private @ApiModelProperty("组合变体的父节点") Long parentId;
	private @ApiModelProperty("坐标") String lattice;
	private @ApiModelProperty("根节点") Long rootId;
	private @ApiModelProperty("创建人id") Long creator;
	private @ApiModelProperty("创建类型") Integer createway;
	private @ApiModelProperty("使用渠道") Integer channelway;
	private @ApiModelProperty("组合变体，组合基础变体的所属id") Long belongId;
	private @ApiModelProperty("外城widget的json") String gproperty;
	private @ApiModelProperty("自定义widget->json") String customWidgetJson;
	private @ApiModelProperty("预览图路径") String path;	
	private @ApiModelProperty("widget类型名称") String widgetTypeName;
	private @ApiModelProperty("分类名称") String typeName;
	private @ApiModelProperty("版本名称") String versionName;
	private @ApiModelProperty("使用渠道名称") String channelName;
	private @ApiModelProperty("创建渠道名称") String channelUserName;
	private @ApiModelProperty("创建人渠道") String channelId;
	
}
