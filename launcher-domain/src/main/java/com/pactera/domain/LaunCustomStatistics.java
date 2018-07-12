package com.pactera.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("自定义事件统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunCustomStatistics extends LaunBase {
	private static final long serialVersionUID = -1307460264188003191L;
	private @ApiModelProperty("版本") String version;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
	private @ApiModelProperty("事件名称") String customName;
	private @ApiModelProperty("事件消息数量") Long customNum;
	private @ApiModelProperty("消息平均时长") String customTime;
	private @ApiModelProperty("日期") Date time;
	private @ApiModelProperty("参数统计") String customParamName;
	
	@Transient
	private @ApiModelProperty("事件消息数量占比") String customProp;
}
