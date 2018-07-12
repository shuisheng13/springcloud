package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper=true)
@ApiModel("Widget属性")
public class LaunWidgetProperty extends LaunBase {
	private static final long serialVersionUID = -907480504174322774L;
	private @ApiModelProperty("Widget主键") Long widgetId;
	private @ApiModelProperty("Widget属性主键") Long widgetPropertyId;
}
