package com.pactera.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Widget配置实体")
public class LaunWidgetConfig extends LaunBase {

    private static final long serialVersionUID = -3836221880420696400L;
    private @ApiModelProperty("widgetId")
    Long widgetId;
    private @ApiModelProperty("配置名称")
    String configName;
    private @ApiModelProperty("是否锁定宽高比")
    String widthToHeight;
    private @ApiModelProperty("横向宽")
    String width;
    private @ApiModelProperty("横向宽权重")
    String widthWieghts;
    private @ApiModelProperty("纵向高")
    String height;
    private @ApiModelProperty("纵向高权重")
    String heightWieghts;
    private @ApiModelProperty("父Widget的id")
    Long launParantId;

}