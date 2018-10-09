package com.pactera.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.pactera.domain.LaunPermissions;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaunAuthChannelVo implements Serializable {

	private static final long serialVersionUID = -7540215814761212509L;
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("渠道名称") String name;
	private @ApiModelProperty("渠道LOGO") String logo;
	@Transient
	private @ApiModelProperty("权限的子集") List<LaunPermissions> listPermissions;

}
