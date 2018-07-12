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
@ApiModel("广告统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunAdverStatistics extends LaunBase {
	private static final long serialVersionUID = -8796126980177632194L;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
	private @ApiModelProperty("广告主") String adverMain;
	private @ApiModelProperty("广告位") String adverPosition;
	private @ApiModelProperty("广告标题") String adverTitle;
	private @ApiModelProperty("日期小时") Date adverHour;
	private @ApiModelProperty("展示次数") Long adverDisplayNum;
	private @ApiModelProperty("点击次数") Long adverClick;
	private @ApiModelProperty("点击率") String adverDisplay;
	private @ApiModelProperty("展示设备数") Long adverClickdevice;
	private @ApiModelProperty("广告费用") Long AdvertisingExpenses;
	private @ApiModelProperty("代理") String adverAgent;
}
