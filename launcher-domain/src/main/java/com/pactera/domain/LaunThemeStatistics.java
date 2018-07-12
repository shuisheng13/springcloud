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
@ApiModel("主题统计表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunThemeStatistics extends LaunBase {
	private static final long serialVersionUID = -1189783465474629894L;
	private @ApiModelProperty("主题id") Long themeId;
	private @ApiModelProperty("渠道ID(null为全部渠道)") Long channelId;
	private @ApiModelProperty("使用次数") Long count;
	private @ApiModelProperty("有效主题数") Long effeTheme;
	private @ApiModelProperty("使用次数占比") String themeProp;
	private @ApiModelProperty("使用车辆次数") Long countCar;
	private @ApiModelProperty("平均时长") String avgTime;
	private @ApiModelProperty("数据产生时间") Date numStartTime;

	@Transient
	private @ApiModelProperty("日期内使用次数") String numTimeCount;
	@Transient
	private @ApiModelProperty("累计车辆") String numCarCount;
	@Transient
	private @ApiModelProperty("平均单次使用时长") String avgTimeDevCount;

}
