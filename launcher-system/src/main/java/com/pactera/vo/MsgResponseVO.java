package com.pactera.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author:LL
 * @since:2018年7月31日 下午3:01:58
 */
@Setter
@Getter
public class MsgResponseVO<T> {

	/**
	 * 服务器返回信息
	 */
	String message;

	/**
	 * 服务器返回状态码
	 */
	String code;

	/**
	 * 服务器返回时间
	 */
	Date time;

	/**
	 * 增量增加的数据集合
	 */
	@JsonProperty(value = "data")
	private List<T> data;

}
