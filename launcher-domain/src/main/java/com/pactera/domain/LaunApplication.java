package com.pactera.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:应用实体
 * @author:Scott
 * @since:2018年4月25日 下午11:29:43
 */
@Table
@Entity
@ApiModel("应用实体")
@Data
@EqualsAndHashCode(callSuper=true)
public class LaunApplication extends LaunBase {
	private static final long serialVersionUID = 6625282490644841632L;
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
	
	//合并的行数
	@Transient
	private @ApiModelProperty("合并的行数")Integer megerRow;
	@Transient
	private @ApiModelProperty("渠道名称")String channelName;
	
	@Transient
	private @ApiModelProperty("是否配置海报")Integer configed;

	

}
