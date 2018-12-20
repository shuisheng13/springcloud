package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

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
@Accessors(chain = true)
public class LaunBase implements Serializable {
	private static final long serialVersionUID = 8107925410233983481L;
	@Id
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("创建时间") Date createDate;
	private @ApiModelProperty("修改时间") Date updateDate;
}
