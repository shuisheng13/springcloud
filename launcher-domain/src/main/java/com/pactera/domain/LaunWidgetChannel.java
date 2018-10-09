package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("widget关联渠道")
public class LaunWidgetChannel {
	private @ApiModelProperty("widgetId") Long widgetId;
	private @ApiModelProperty("渠道Id") Long channelId;

}
