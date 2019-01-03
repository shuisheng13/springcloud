package com.pactera.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.exception.status.SuccessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel("返回值实体")
@SuppressWarnings("deprecation")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResultData<T> implements Serializable {
	private static final long serialVersionUID = 6688136731432037929L;
	private @ApiModelProperty("返回值") T data;
	private @ApiModelProperty("自定义返回的状态") Integer status;
	private @ApiModelProperty("自定义状态的消息内容") String message;
	private @ApiModelProperty("token值") String tokenValue;

	public ResultData() {
		this.status = SuccessStatus.OPERATION_SUCCESS.status();
		this.message = SuccessStatus.OPERATION_SUCCESS.message();
	}

	public ResultData(T data) {
		this.data = data;
		this.status = SuccessStatus.OPERATION_SUCCESS.status();
		this.message = SuccessStatus.OPERATION_SUCCESS.message();
	}
	
	public ResultData(ErrorStatus errorStatus) {
		this.status = errorStatus.status();
		this.message = errorStatus.message();
	}

	public ResultData(T data, SuccessStatus successStatus) {
		this.data = data;
		this.status = successStatus.status();
		this.message = successStatus.message();
	}

	public ResultData(T data, ErrorStatus errorStatus) {
		this.data = data;
		this.status = errorStatus.status();
		this.message = errorStatus.message();
	}
	
	public ResultData(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ResultData(Integer status, T data) {
		this.data = data;
		this.status = status;
	}

	public ResultData(T data, Integer status, String message) {
		this.data = data;
		this.status = status;
		this.message = message;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//public String getTokenValue() {
	//	return tokenValue;
	//}
	//
	//public void setTokenValue(String tokenValue) {
	//	this.tokenValue = tokenValue;
	//}
	
}
