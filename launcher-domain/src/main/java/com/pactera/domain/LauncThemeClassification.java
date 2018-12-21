package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table
@Entity
@ApiModel("主题分类")
@Data
@EqualsAndHashCode(callSuper=true)
public class LauncThemeClassification extends LaunBase {

    private static final long serialVersionUID = 7592025457847638111L;

    @ApiModelProperty("主题分类id")
    private String classificationId;

    @ApiModelProperty("主题分类名")
    private String classificationName;

    @ApiModelProperty("排序号")
    private int sort;

    @ApiModelProperty("主题总数")
    private int quantity;

    @ApiModelProperty("上架主题数")
    private int shelfCount;

    @Transient
    @ApiModelProperty("文件地址")
    private String coverImage;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("上架状态")
    private String shelfStatus;

    @Transient
    @ApiModelProperty("租户id")
    private String tenantId;

    @Transient
    @ApiModelProperty("是否有效")
    private int disable;

}
