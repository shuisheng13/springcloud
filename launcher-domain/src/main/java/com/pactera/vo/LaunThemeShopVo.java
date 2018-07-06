package com.pactera.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LaunThemeShopVo implements Serializable {

	private static final long serialVersionUID = 4732160826805333243L;

	private @ApiModelProperty("主键") Long categoryID;
	private @ApiModelProperty("分类数量") Integer categoryNum;
	private @ApiModelProperty("分类名称") String categoryName;
	private @ApiModelProperty("分类名称") List<LaunClientShopThemeVo> themes;

}
