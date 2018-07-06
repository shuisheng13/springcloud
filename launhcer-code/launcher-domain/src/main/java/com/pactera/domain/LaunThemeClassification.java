package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@ApiModel("主题分类实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunThemeClassification extends LaunBase {

	private static final long serialVersionUID = -6514907319465030593L;

	private @ApiModelProperty("新建主题分类名称") String classificationName;

	@Transient
	private @ApiModelProperty("主题使用的数量") Integer num;
}
