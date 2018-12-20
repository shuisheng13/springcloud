package com.pactera.config.exception.status;

public enum SuccessStatus {
	OPERATION_SUCCESS(200000, "请求成功"),
	THEMEAPI_NOTHEME(200001, "暂无强推主题"),
	UPDATE_DESCRIPTION_FAIL(200002, "未能正确更新描述信息"),
	/**
	 * widget管理
	 */
	WIDGETSAVE_SUCCESS(300001,"保存成功"),
	WIDGETUPDATE_SUCCESS(300002,"修改成功"),
	WIDGETUPLOAD_SUCCESS(300003,"文件上传成功");
	
	private int status;

	private String message;

	private SuccessStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int status() {
		return status;
	}

	public String message() {
		return message;
	}
}
