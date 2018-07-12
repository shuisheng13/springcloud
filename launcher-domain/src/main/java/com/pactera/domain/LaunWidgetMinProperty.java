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
@ApiModel("Widget最小属性")
public class LaunWidgetMinProperty extends LaunBase {
	private static final long serialVersionUID = -907480504174322774L;
	private @ApiModelProperty("属性名字") String name;
	private @ApiModelProperty("属性值") String valueData;
	private @ApiModelProperty("父级属性id") Long parantId;
	private @ApiModelProperty("数据类型 0单一数据，1多个数据， 2代表数组（String），3代表数组（json对象）") Integer dataType;
	private @ApiModelProperty("是否为同一数组，0代表否，1代表是") String completeType;
	private @ApiModelProperty("父级标示") Long widgetPropertyId;

	@Transient
	private @ApiModelProperty("子属性对象") LaunWidgetMinProperty launWidgetMinProperty;

	@Transient
	private @ApiModelProperty("子属性集合") LaunWidgetMinProperty launWidgetMinPropertyList;
}
