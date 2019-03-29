package com.pactera.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "laun_carousel")
@Entity
@ApiModel("轮播图")
@Data
@Accessors(chain = true)
public class LaunCarousel {

    private @ApiModelProperty("id") String id;

    private @ApiModelProperty("轮播图标题") String title;

    private @ApiModelProperty("轮播图状态") String status;

    private @ApiModelProperty("起始时间") Date startTime;

    private @ApiModelProperty("终止时间") Date endTime;

    private @ApiModelProperty("间隔时常") int interval;

    private @ApiModelProperty("应用版本") double version;

    private @ApiModelProperty("格式") int formatId;

    private @ApiModelProperty("轮播位") String position;

    private @ApiModelProperty("创建时间") Date createDate;

    private @ApiModelProperty("修改时间") Date updateDate;

}
