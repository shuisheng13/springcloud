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
@ApiModel("用户角色关联实体")
@EqualsAndHashCode(callSuper = true)
public class LaunUserRole  extends LaunBase{
	private static final long serialVersionUID = 7865780752662503603L;
	private @ApiModelProperty("用户主键") Long userId;
	private @ApiModelProperty("角色主键") Long roleId;
}
