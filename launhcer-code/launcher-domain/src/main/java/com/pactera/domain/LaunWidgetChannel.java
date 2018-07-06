package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table
@Entity
@ApiModel("widget关联渠道")
public class LaunWidgetChannel {
	private @ApiModelProperty("widgetId") Long widgetId;
	private @ApiModelProperty("渠道Id") Long channelId;

}
