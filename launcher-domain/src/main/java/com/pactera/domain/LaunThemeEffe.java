package com.pactera.domain;

import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "laun_theme_effe")
@EqualsAndHashCode(callSuper = true)
public class LaunThemeEffe extends LaunBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239266493717594709L;

	private @ApiModelProperty("有效主题数量") Long effeTheme;

	private @ApiModelProperty("渠道ID") String channelId;

	private @ApiModelProperty("统计日") String numStartTime;

	private @ApiModelProperty("渠道名称") String channelName;
}