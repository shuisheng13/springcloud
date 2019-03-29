package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "laun_carousel_images")
@Entity
@ApiModel("轮播图文件")
@Data
@Accessors(chain = true)
public class LaunCarouselImages {

    private @ApiModelProperty("id")int id;

    private @ApiModelProperty("轮播图id")String launCarouselId;

    private @ApiModelProperty("主题id") String themeId;

    private @ApiModelProperty("文件地址") String pic;

    private @ApiModelProperty("创建时间")Date createDate;

}
