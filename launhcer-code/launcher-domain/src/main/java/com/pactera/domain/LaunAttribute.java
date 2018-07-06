package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:应用实体
 * @author:wangyaqun
 * @since:2018年4月25日 下午11:29:43
 */
@Table
@Entity
@ApiModel("属性表")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunAttribute extends LaunBase {
	private static final long serialVersionUID = 3581928013203123985L;
	private @ApiModelProperty("属性名称") String attributeValue ;
	private @ApiModelProperty("version:laun版本") String attributeKey;
	private @ApiModelProperty("0,1,2,3") Integer attributeKeyIndex;
	private @ApiModelProperty("0:不可用;1:可用") Integer attributeStatus;
	private @ApiModelProperty("描述") String des;
}
