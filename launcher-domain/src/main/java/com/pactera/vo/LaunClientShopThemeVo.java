package com.pactera.vo;

import java.io.Serializable;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LaunClientShopThemeVo implements Serializable {

	private static final long serialVersionUID = 4732160826805333243L;

	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("主题大小") Long fileSize;
	private @ApiModelProperty("主题名称") String name;
	private @ApiModelProperty("主题包下载地址") String url;
	private @ApiModelProperty("主题预览图地址") String previewPath;
	private @ApiModelProperty("图片") String[] images;

}
