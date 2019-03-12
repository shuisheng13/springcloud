package com.pactera.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 主题实体类
 */
@Data
@Accessors(chain = true)
public class LaunThemeAdministration {

	@Id
	private String id;
	private Date createDate;
	private Date updateDate;

	private Long fileSize;
	private int longPalace;
	private int widePalace;
	private Long longResolution;
	private Long wideResolution;
	private String widthOrHigthRatio;
	private String folatPoint;
	private Long level;
	private Double version;
	private Long creatorChannelId;
	private  String typeId;
	private String title;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private String musicAppImgUrl;
	private String phoneAppImgUrl;
	private String weatherAppImgUrl;
	private String fmAppImgUrl;
	private String background;
	private String font;
	private String basicJson;
	private String widgetJson;
	private String themeJson;
	private String zipUrl;
	private String previewPath;
	private String urls;
	private String description;
    private Integer sort;
    private Integer downloadCount;
    private Integer usedCount;
    private String author;
    private Date releaseTime;
	private Boolean recommend;
	private Integer recommendSort;
	private BigDecimal price;
	private Integer tenantId;
	private Integer addition;
	private String creator;
	private Long layoutId;

	@Transient
	@JsonProperty("sTime")
	private @ApiModelProperty("页面传值用的起止时间") String sTime;
	@Transient
	@JsonProperty("eTime")
	private @ApiModelProperty("页面传值用的起止时间") String eTime;
	@Transient
	private @ApiModelProperty("页面传值用的分类名称") String typeName;
	@Transient
	private @ApiModelProperty("页面传值用的创建者名称") String createName;
	@Transient
	private @ApiModelProperty("页面传值用的版本名称") String versionName;
	@Transient
	private @ApiModelProperty("页面传值用的预览图数组") Map<String, String> filesJson;
	@Transient
	private @ApiModelProperty("页面传值用的字体路径") String fonts;
}
