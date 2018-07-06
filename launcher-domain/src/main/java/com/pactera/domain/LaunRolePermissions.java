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
@ApiModel("角色权限关联实体")
@EqualsAndHashCode(callSuper = true)
public class LaunRolePermissions extends LaunBase {
	private static final long serialVersionUID = 6904958213570941148L;
	private @ApiModelProperty("角色") Long roleId;
	private @ApiModelProperty("权限") Long permissionsId;
}
