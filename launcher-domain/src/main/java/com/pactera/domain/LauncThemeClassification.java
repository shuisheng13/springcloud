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

    //@ApiModelProperty("主题分类id")
    //private String classificationId;

    private @ApiModelProperty("主键")String id;

    private @ApiModelProperty("主题分类名")String classificationName;

    private @ApiModelProperty("排序号") int sort;

    private @ApiModelProperty("主题总数")int quantity;

    private @ApiModelProperty("上架主题数")int shelfCount;

    @Transient
    private @ApiModelProperty("文件地址")String coverImage;

    private @ApiModelProperty("创建人")String creator;

    private  @ApiModelProperty("上架状态")String shelfStatus;

    @Transient
    private @ApiModelProperty("租户id")String tenantId;

    @Transient
    private @ApiModelProperty("是否有效")int disable;

}
