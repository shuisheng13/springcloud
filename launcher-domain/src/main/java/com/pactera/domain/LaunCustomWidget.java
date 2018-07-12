package com.pactera.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table
@Entity
@ApiModel("自定义widget")
@EqualsAndHashCode(callSuper=true)
public class LaunCustomWidget extends LaunBase implements Serializable {
	private static final long serialVersionUID = -6418974588876964726L;
	private @ApiModelProperty("自定义widget名称") String name;
	private @ApiModelProperty("自定义widget类型") Long category;
	private @ApiModelProperty("自定义widget版本") String version;
	private @ApiModelProperty("默认尺寸") String size;
	private @ApiModelProperty("图片") String prewImage;

}
