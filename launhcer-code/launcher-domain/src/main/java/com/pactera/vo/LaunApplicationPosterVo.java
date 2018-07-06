package com.pactera.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:应用海报vo
 * @author:Scott
 * @since:2018年6月11日 下午6:00:47
 */
@Setter
@Getter
public class LaunApplicationPosterVo implements Serializable {
	private static final long serialVersionUID = 1924753019422989665L;

	private @ApiModelProperty("主键id") Long id;
	private @ApiModelProperty("应用ID") Long applicationId;
	private @ApiModelProperty("海报宫格宽度") Integer width;
	private @ApiModelProperty("海报宫格高度") Integer height;
	private @ApiModelProperty("海报图片地址") String posterPath;
	private @ApiModelProperty("是否是默认海报") Integer type;
	private @ApiModelProperty("海报排序") Integer orders;

	private @ApiModelProperty("海报开始时间") Date startTime;

	private @ApiModelProperty("海报结束时间") Date endTime;

	private @ApiModelProperty("海报id") String packageName;

	private @ApiModelProperty("海报name") String name;
}
