package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Widget添加对齐方式")
public class LaunWidgetWidthToHight extends LaunBase {

	private static final long serialVersionUID = 1663701158936318435L;
	private @ApiModelProperty("主题widget的id") Long widgetConfigId;
	private @ApiModelProperty("类型1:横向2:纵向") int type;
	private @ApiModelProperty("对齐方式") String align;
	private @ApiModelProperty("基于xx对齐") String baseAlign;
	private @ApiModelProperty("边距") Long distance;

}
