package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("主题文件实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunThemeFile extends LaunBase {

	private static final long serialVersionUID = 7522456641544795644L;
	private @ApiModelProperty("主题主键") Long themeId;
	private @ApiModelProperty("文件名称") String fileName;
	private @ApiModelProperty("文件路径") String filePath;
	private @ApiModelProperty("文件下标") Integer index;
	private @ApiModelProperty("文件类型1:预览主图2:预览配图") Integer type;

}
