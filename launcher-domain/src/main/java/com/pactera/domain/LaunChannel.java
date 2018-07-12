package com.pactera.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("渠道实体")
@Data
@EqualsAndHashCode(callSuper=true)
public class LaunChannel extends LaunBase {

	private static final long serialVersionUID = -1382053140111935395L;

	private @ApiModelProperty("渠道名称") String name;
	private @ApiModelProperty("渠道ID") String channelId;
	private @ApiModelProperty("渠道LOGO") String logo;
	private @ApiModelProperty("渠道管理员主键") Long userId;
	private @ApiModelProperty("渠道状态：1代表删除") int channelStatus;
	@Transient
	private @ApiModelProperty("渠道管理员信息") LaunUser user;
	@Transient
	private @ApiModelProperty("权限的子集") List<LaunPermissions> listPermissions;

}
