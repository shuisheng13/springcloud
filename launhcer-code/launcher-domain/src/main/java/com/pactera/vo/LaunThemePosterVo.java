package com.pactera.vo;

import java.io.Serializable;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LaunThemePosterVo implements Serializable {

	private static final long serialVersionUID = 4732160826805333243L;

	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("开始时间") Long startTime;
	private @ApiModelProperty("结束时间") Long endTime;
	private @ApiModelProperty("图片地址") String path;

}
