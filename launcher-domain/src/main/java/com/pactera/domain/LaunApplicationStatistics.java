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
@ApiModel("应用统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunApplicationStatistics extends LaunBase {
	private static final long serialVersionUID = 1340648781490883852L;
	private @ApiModelProperty("版本(null为全部版本)") String version;
	private @ApiModelProperty("渠道ID(null为全部渠道)") String channelId;
	private @ApiModelProperty("应用ID") String applicationId;
	private @ApiModelProperty("启动次数") Long startUpNum;
	private @ApiModelProperty("日期") Date applicationTime;
	@Transient
	private @ApiModelProperty("应用名称") String applicationName;
	@Transient
	private @ApiModelProperty("日期内启动次数") Long timeNum;
	@Transient
	private @ApiModelProperty("日期内启动占比") String timeProp;
	@Transient
	private @ApiModelProperty("启动次数占比") String applicationStartProp;

	// private @ApiModelProperty("地区名称") String applicationRegionName;
	// private @ApiModelProperty("地区id") Long applicationRegionId;
}
