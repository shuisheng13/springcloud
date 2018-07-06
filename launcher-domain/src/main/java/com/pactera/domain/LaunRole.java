package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@ApiModel("角色实体")
@EqualsAndHashCode(callSuper = true)
public class LaunRole extends LaunBase {
	private static final long serialVersionUID = 6578646501899410385L;
	private @ApiModelProperty("角色名字") String name;
	private @ApiModelProperty("角色的级别") Integer levels;
	private @ApiModelProperty("角色描述") String introducs;
	private @ApiModelProperty("角色的状态") Integer status;
}
