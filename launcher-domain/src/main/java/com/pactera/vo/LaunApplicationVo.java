package com.pactera.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:应用vo
 * @author:Scott
 * @since:2018年6月11日 下午6:00:47
 */
@Setter
@Getter
public class LaunApplicationVo implements Serializable {
	private static final long serialVersionUID = 8051624494275287980L;
	private @ApiModelProperty("渠道ID") String channelId;
	private @ApiModelProperty("应用名称") String name;
	private @ApiModelProperty("应用安装包地址") String packagePath;
	private @ApiModelProperty("应用图标地址") String logoPath;
	private @ApiModelProperty("应用包名") String packageName;
	private @ApiModelProperty("应用id") String packageId;
	private @ApiModelProperty("应用昨日启动次数") Integer yesterdayStartCount;
	private @ApiModelProperty("启动次数占比") String startCountRatio;
	private @ApiModelProperty("海报开始时间") Date startTime;
	private @ApiModelProperty("海报结束时间") Date endTime;
	private @ApiModelProperty("上传文件名称") String fileName;
	private @ApiModelProperty("主键") Long id;

	// 合并的行数
	private @ApiModelProperty("合并的行数") Integer megerRow;
	private @ApiModelProperty("渠道名称") String channelName;

	private @ApiModelProperty("是否配置海报") Integer configed;

}
