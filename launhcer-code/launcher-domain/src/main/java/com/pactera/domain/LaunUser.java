package com.pactera.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@ApiModel("用户实体")
@EqualsAndHashCode(callSuper = true)
public class LaunUser extends LaunBase {
	private static final long serialVersionUID = 6098705338103864860L;
	private @ApiModelProperty("用户名") String userName;
	@JsonIgnore
	private @ApiModelProperty("密码") String passWord;
	private @ApiModelProperty("真是姓名") String realName;
	private @ApiModelProperty("性别:0-女，1-男") int sex;
	private @ApiModelProperty("电话") String phone;
	private @ApiModelProperty("渠道ID") Long channelId;
	private @ApiModelProperty("用户类型：0-思维超级管理员，1-渠道管理员，2-思维管理员") Integer userType;
	private @ApiModelProperty("登陆时间") Date loginDate;
	private @ApiModelProperty("备注") String remarks;
	@Transient
	private @ApiModelProperty("权限集合") List<LaunPermissions> listPermissions;
	@Transient
	private @ApiModelProperty("渠道的名称") String channelName;

}
