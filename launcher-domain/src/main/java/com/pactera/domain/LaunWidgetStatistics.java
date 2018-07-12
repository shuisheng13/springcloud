package com.pactera.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("Widget统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunWidgetStatistics extends LaunBase {
	private static final long serialVersionUID = -4391875596649085736L;
	private @ApiModelProperty("版本") String version;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
	private @ApiModelProperty("应用id") Long applicationId;
	private @ApiModelProperty("应用名称") String applicationName;
	private @ApiModelProperty("启动次数") Long startUpNum;
	private @ApiModelProperty("日期") Date widgetTime;
	private @ApiModelProperty("累计车辆") Long carNum;

}
