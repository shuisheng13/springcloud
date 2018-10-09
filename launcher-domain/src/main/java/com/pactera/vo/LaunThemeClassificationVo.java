package com.pactera.vo;

import java.io.Serializable;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaunThemeClassificationVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8470830629255311424L;

	private @ApiModelProperty("新建主题分类名称") Long id;

	private @ApiModelProperty("新建主题分类名称") String classificationName;

	private @ApiModelProperty("主题使用的数量") Integer num;

}
