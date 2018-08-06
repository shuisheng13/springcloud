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
@ApiModel("权限实体")
@EqualsAndHashCode(callSuper = true)
public class LaunPermissions extends LaunBase {
	private static final long serialVersionUID = -5572219573333203853L;
	private @ApiModelProperty("权限名字") String name;
	private @ApiModelProperty("资源路径") String resources;
	private @ApiModelProperty("权限描述") String introducs;
	private @ApiModelProperty("权限的父级") Long parentId;
	private @ApiModelProperty("权限的级别") Integer levels;
	private @ApiModelProperty("权限的状态") Integer status;
}
