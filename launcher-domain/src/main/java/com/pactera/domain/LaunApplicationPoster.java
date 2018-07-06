package com.pactera.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @description:应用海报
 * @author:Scott
 * @since:2018年4月25日 下午11:32:26
 */
@Table
@Entity
@ApiModel("应用海报")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunApplicationPoster extends LaunBase {

	private static final long serialVersionUID = 6625282490644841632L;

	private @ApiModelProperty("应用ID") Long applicationId;
	private @ApiModelProperty("海报宫格宽度") Integer width;
	private @ApiModelProperty("海报宫格高度") Integer height;
	private @ApiModelProperty("海报图片地址") String posterPath;
	private @ApiModelProperty("是否是默认海报") Integer type;
	private @ApiModelProperty("海报排序") Integer orders;

	@Transient
	private @ApiModelProperty("海报开始时间") Date startTime;

	@Transient
	private @ApiModelProperty("海报结束时间") Date endTime;
	
	@Transient
	private @ApiModelProperty("海报id") String packageName;
	
	@Transient
	private @ApiModelProperty("海报name") String name;
}
