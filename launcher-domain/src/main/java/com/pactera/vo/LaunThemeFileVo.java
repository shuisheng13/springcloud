package com.pactera.vo;

import java.io.Serializable;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LaunThemeFileVo implements Serializable {

	private static final long serialVersionUID = 4732160826805333243L;

	private @ApiModelProperty("文件名称") String fileName;
	private @ApiModelProperty("文件路径") String filePath;
	private @ApiModelProperty("文件类型1:预览主图2:预览配图") Integer type;

}
