package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper=true)
@ApiModel("Widget文件")
public class LaunWidgetFile extends LaunBase {
	private static final long serialVersionUID = -907480504174322774L;
	//private @ApiModelProperty("上传人") Long userId;
	private @ApiModelProperty("文件路径") String path;
	private @ApiModelProperty("文件原名字") String fileName;
	private @ApiModelProperty("widget主键") Long widgetId;
	private @ApiModelProperty("文件类型") Integer type;
	//Jump添加
	private @ApiModelProperty("上传用户名") String author;

}
