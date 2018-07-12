package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper = true)
@ApiModel("Widget实体")
public class LaunWidget extends LaunBase {
	private static final long serialVersionUID = -907480504174322774L;
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
	
	@Transient
	private @ApiModelProperty("预览图路径") String path;	
	@Transient
	private @ApiModelProperty("widget类型名称") String widgetTypeName;
	@Transient
	private @ApiModelProperty("分类名称") String typeName;
	@Transient
	private @ApiModelProperty("版本名称") String versionName;
	@Transient
	private @ApiModelProperty("使用渠道名称") String channelName;
	@Transient
	private @ApiModelProperty("创建渠道名称") String channelUserName;

}
