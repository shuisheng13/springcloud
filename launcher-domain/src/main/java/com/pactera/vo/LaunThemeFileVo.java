package com.pactera.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LaunThemeFileVo implements Serializable {

	private static final long serialVersionUID = 4732160826805333243L;

	private @ApiModelProperty("文件名称") String fileName;
	private @ApiModelProperty("文件路径") String filePath;
	private @ApiModelProperty("文件类型1:widget 2:icon 3.lockscreen") Integer type;

}
