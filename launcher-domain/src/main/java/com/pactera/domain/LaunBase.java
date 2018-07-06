package com.pactera.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @description:实体基类
 * @author:Scott
 * @since:2018年4月26日 下午12:29:43
 */
@Setter
@Getter
@MappedSuperclass
@ApiModel("实体基类")
public class LaunBase implements Serializable {
	private static final long serialVersionUID = 8107925410233983481L;
	@Id
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("创建时间") Date createDate;
	private @ApiModelProperty("修改时间") Date updateDate;
}
