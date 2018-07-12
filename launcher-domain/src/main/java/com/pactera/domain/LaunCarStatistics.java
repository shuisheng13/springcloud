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
@ApiModel("车辆统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunCarStatistics extends LaunBase {
	private static final long serialVersionUID = 6872465354088919368L;
	private @ApiModelProperty("版本") String version;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
	private @ApiModelProperty("日期") Date carTime;
	private @ApiModelProperty("每日新增车辆") Long carNum;
	private @ApiModelProperty("活跃车辆") Long carActive;
	private @ApiModelProperty("启动次数") Long carStart;
	private @ApiModelProperty("升级车辆数量") Long upGradeNum;
	private @ApiModelProperty("累计车辆数量") Long addUpNum;
	private @ApiModelProperty("平均单次使用时长") String carAvgTime;

	@Transient
	private @ApiModelProperty("启动次数占比") String carStartProp;
	@Transient
	private @ApiModelProperty("活跃车辆占比") String carActiveProp;
	@Transient
	private @ApiModelProperty("新增车辆占比") String carProp;
	@Transient
	private @ApiModelProperty("累计车辆占比") String addUpNumProp;
	@Transient
	private @ApiModelProperty("昨日新增车辆") Long addUpNumYes;
	@Transient
	private @ApiModelProperty("昨日活跃车辆") Long carActiveYes;

}
