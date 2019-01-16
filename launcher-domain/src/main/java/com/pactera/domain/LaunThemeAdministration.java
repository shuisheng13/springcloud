package com.pactera.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 主题实体类
 */
@Table
@Entity
@Data
@Accessors(chain = true)
public class LaunThemeAdministration {
	private static final long serialVersionUID = -2054946094353463347L;

	@Id
	private @ApiModelProperty("主键") String id;
	private @ApiModelProperty("创建时间") Date createDate;
	private @ApiModelProperty("修改时间") Date updateDate;

	private @ApiModelProperty("主题大小") Long fileSize;
	private @ApiModelProperty("主题宫格长") int longPalace;
	private @ApiModelProperty("主题宫格宽") int widePalace;
	private @ApiModelProperty("主题分辨率长") Long longResolution;
	private @ApiModelProperty("主题分辨率宽") Long wideResolution;
	private @ApiModelProperty("新建尺寸长宽比") String widthOrHigthRatio;
	private @ApiModelProperty("新建尺寸浮动") String folatPoint;
	private @ApiModelProperty("会员等级1:普通会员") Long level;
	private @ApiModelProperty("主题版本") String version;
	private @ApiModelProperty("渠道id") Long creatorChannelId;
	private @ApiModelProperty("渠道id") Long createId;
	private @ApiModelProperty("主题分类") String typeId;
	private @ApiModelProperty("主题名称") String title;
	private @ApiModelProperty("主题状态 1上架 2下架 3启用 4禁用 5删除") Integer status;
	private @ApiModelProperty("开始时间") Date startTime;
	private @ApiModelProperty("结束时间") Date endTime;
	private @ApiModelProperty("音乐App") String musicAppImgUrl;
	private @ApiModelProperty("电话App") String phoneAppImgUrl;
	private @ApiModelProperty("天气App") String weatherAppImgUrl;
	private @ApiModelProperty("FM") String fmAppImgUrl;
	private @ApiModelProperty("主题背景") String background;
	private @ApiModelProperty("主题字体") String font;
	private @ApiModelProperty("主题底屏json") String basicJson;
	private @ApiModelProperty("主题信息组件json") String widgetJson;
	private @ApiModelProperty("主题信息组件json") String themeJson;
	private @ApiModelProperty("主题包url地址") String zipUrl;
	private @ApiModelProperty("api接口主图") String previewPath;
	private @ApiModelProperty("api接口图片urls") String urls;
	private @ApiModelProperty("主题描述") String description;
    private @ApiModelProperty("排序") Integer sort;
    private @ApiModelProperty("下载量") Integer downloadCount;
    private @ApiModelProperty("使用量") Integer usedCount;
    private @ApiModelProperty("作者") String author;
    private @ApiModelProperty("发布时间") Date releaseTime;
	private @ApiModelProperty("是否是推荐主题") Boolean recommend;
	private @ApiModelProperty("推荐排序") Integer recommendSort;
	private BigDecimal price;
	private String tenantId;
	private Long addition;

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
	private @ApiModelProperty("页面传值用的渠道数组") String creator;
	@Transient
	private @ApiModelProperty("页面传值用的字体路径") String fonts;

}
