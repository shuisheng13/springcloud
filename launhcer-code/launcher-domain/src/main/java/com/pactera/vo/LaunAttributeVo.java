package com.pactera.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LaunAttributeVo {
	private @ApiModelProperty("主键") Long id;
	private @ApiModelProperty("创建时间") Date createDate;
	private @ApiModelProperty("修改时间") Date updateDate;
	private @ApiModelProperty("属性名称") String attributeValue ;
	private @ApiModelProperty("version:laun版本") String attributeKey;
	private @ApiModelProperty("0,1,2,3") Integer attributeKeyIndex;
	private @ApiModelProperty("0:不可用;1:可用") Integer attributeStatus;
	private @ApiModelProperty("描述") String des;
}
