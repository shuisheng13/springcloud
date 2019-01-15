package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
@ApiModel("主题文件实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class LaunThemeFile extends LaunBase {

    private static final long serialVersionUID = 7522456641544795644L;
    private @ApiModelProperty("主题主键")
    String themeId;
    private @ApiModelProperty("文件名称")
    String fileName;
    private @ApiModelProperty("文件路径")
    String filePath;
    private @ApiModelProperty("文件下标")
    Integer fileIndex;
    private @ApiModelProperty("文件类型1:预览主图2:预览配图")
    Integer type;

}
