package com.pactera.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("日志实体")
public class LaunBrowseLogs implements Serializable {
	private static final long serialVersionUID = -5453735788732292301L;
	@Id
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("用户主键") String userId;
	private @ApiModelProperty("用户名") String userName;
	private @ApiModelProperty("方法") String method;
	private @ApiModelProperty("方法名字") String methodName;
	private @ApiModelProperty("方法类型") String methodType;
	private @ApiModelProperty("渠道主键") String channelId;
	private @ApiModelProperty("Ip地址") String ip;
	private @ApiModelProperty("创建时间") Date createDate;
}
