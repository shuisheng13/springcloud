package com.pactera.config.exception.status;

public enum ErrorStatus {
	THREAD_ERROR(100003,"线程池异常"),
	USER_ISNULL(110001,"用户信息为空"),
	USER_NOTFOUND(110002,"用户信息错误"),
	USER_ISHAVE(110003,"用户已存在"),
    TOKEN_ERROR(110004,"token不可用"),
    PASSWORD_ERROR(110005,"密码错误"),
    AUTHENTICATION_FAILED(110006,"认证失败"),
    NOT_USER(110007,"用户不存在"),
    USER_LOGINS(110008,"用户已经登录"),
    CHANNEL_SAVE(110009,"添加渠道异常"),
    ROLE_DELETE(110010,"删除角色异常"),
    CHANNEL_MORE(110011,"渠道重复"),
    NOT_PERMISSIONS(110012,"没有权限"),
	SYSTEM(100000,"系统正在维护"),
	PARAMETER_ERROR(100001,"参数非法"),
	PACK_THEME_ZIP(140001,"主题打包IO异常"),
	PACK_THEME_DOWNLOAD(140002,"下载资源文件异常"),
	SYS_ERROR(140004,"系统错误"),
	PARSE_THEME_JSON(140003,"解析主题组件异常"),
	FASTDFS_ERROR(140004,"文件服务器异常"),
	WIDGETUPLOAD_ERROR(140005,"文件上传失败"),
	WIDGETPARSE_ERROR(140006,"widget解析异常"),
	WIDGETSIZE_ERROR(140007,"widget大小格式不正确"),
	WIDGETNAMEISNULL_ERROR(140008,"widget名称校验失败"),
	WIDGETIMG_ERROR(140009,"压缩包图片与json中的数量不一致"),
	WIDGETPRIVEW_ERROR(140010,"封面不能为空"),


	HTTP_REQUEST_METHOD_ERROR(400001,"请求方式错误"),
	THEMEJSON_ERROR(400002,"themeJson格式有误，转换异常"),
	NOT_APIKEY(140014,"无效apiKey"),

	UPLOAD_THEME_ILLEGAL_FILE(150000,"主题资源包有误"),
	UPLOAD_THEME_NO_IMGS(150001,"上传资源包中缺少imgs包"),
	UPLOAD_THEME_NO_CONFIG(150002,"上传资源包中缺少config文件"),
	UPLOAD_THEME_NO_SKIN(150003,"上传资源包中缺少skin文件"),
	UPLOAD_THEME_TOO_MANY_IMG(150004,"上传资源包中图片数量有误"),
	UPLOAD_THEME_IMG_ERROR(150005,"资源包中图片配置有误"),

	IO_ERROR(500001,"IO异常"),

    NAME_CLASS_LAUNTHEM_ADD(140011, "添加主题分类重名"),
    NAME_CLASS_LAUNTHEM_REEOR(140012, "添加主题分类字数超过8字符"),
    NAME_CLASS_LAUNTHEM_UP(140013, "编辑主题分类重名"),
	SERVICE_ERROR_CLIENTEXCEPTION(140014,"连接租户服务异常"),
	SERVICE_ERROR_NOT_TENANT(140015,"不是普通租户"),

	SERVICE_ERROR_NOT_TENANTINFO(140016,"连接saas成功,但是查询租户id为空、根据租户id查询租户信息不存在"),
	SERVICE_ERROR_NOT_TENANTINFONAME(140017,"租户名字为空"),
	SERVICE_ERROR_NOT_TENANTINFOAPIKEY(140018,"租户apiKey为空"),
	SERVICE_ERROR_NOT_TENANTINFOENNAME(140019,"租户英文名字为空");


	
	private int status;

	private String message;

	private ErrorStatus(int status, String message) {
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
