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
@EqualsAndHashCode(callSuper = true)
@ApiModel("Widget分类")
public class LaunWidgetType extends LaunBase {
    private static final long serialVersionUID = 4347997164852090572L;
    private @ApiModelProperty("类型名称")
    String typeName;

}
