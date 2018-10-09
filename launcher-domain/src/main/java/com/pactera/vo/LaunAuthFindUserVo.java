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
public class LaunAuthFindUserVo implements Serializable {

	private static final long serialVersionUID = 2302918841899189483L;
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("用户名") String userName;
	private @ApiModelProperty("渠道ID") Long channelId;
	private @ApiModelProperty("用户类型：0-思维超级管理员，1-渠道管理员，2-思维管理员") Integer userType;
	private @ApiModelProperty("备注") String remarks;
	@Transient
	private @ApiModelProperty("渠道的名称") String channelName;
	@Transient
	private @ApiModelProperty("权限集合") List<LaunPermissions> listPermissions;

}
