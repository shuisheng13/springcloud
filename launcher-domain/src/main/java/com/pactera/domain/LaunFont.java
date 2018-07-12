package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("渠道实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunFont extends LaunBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4997882209803711422L;

	private @ApiModelProperty("字体名称") String fontName;
	private @ApiModelProperty("文件路径") String filePath;

}
