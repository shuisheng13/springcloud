package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("事件统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunEventStatistics extends LaunBase {
	private static final long serialVersionUID = -6496490475081660030L;
	
	private @ApiModelProperty("事件id") Long eventId;
	private @ApiModelProperty("事件名称") String eventName;
	private @ApiModelProperty("版本") String version;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
}
