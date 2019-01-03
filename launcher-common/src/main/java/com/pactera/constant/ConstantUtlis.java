package com.pactera.constant;

public interface ConstantUtlis {

	// 自定义标识字段--失败标示
	String FAILURE_SATE = "0";
	// 自定义标识字段--成功标示
	String SUCCESS_SATE = "1";
	// 自定义标识字段--返回数
	int RETURN_NUMBER = 0;
	// 自定义标识字段--上架
	int UP_SHELF = 1;
	// 自定义标识字段--下架
	int DOWN_SHELF = 2;
	// 成功返回
	int SUCCESS = 1;
	// 自定义标识字段
	String USER_TYPE_ONE = "1";
	// 自定义标识字段
	String USER_TYPE_TWO = "2";
	// 自定义标识字段
	String ZERO = "0";
	// 自定义标识字段
	String NULL = "null";
	// 自定义标识字段
	String GET = "GET";
	// 自定义标识字段
	String ANONY_MOUS_USER = "anonymousUser";
	// 自定义标识字段-渠道不能删除
	String NOT_DELETE_CHANNEL = "2";
	// 自定义标识字段-主题商店redis-key
	String THEME_REDIS_FLAG = "themeShop";
	// 自定义标识字段-主题商店redis-key
	String THEME_REDIS_REFRESH = "themeShop_refresh";
	// 自定义标识字段-今日概况的key
	String TODAY_STATISTICS = "todayStatistics";
	// 自定义标识字段-宫格布局
	int LATTICE_LAYOUT = 0;
	// 自定义标识字段-统计标识
	String STATISTICS_ONE = "1";
	// 自定义标识字段-统计标识
	String STATISTICS_TWO = "2";
	// 自定义标识字段-统计标识
	String STATISTICS_THREE = "3";
	// 自定义标识字段-统计标识
	String STATISTICS_FOUR = "4";
	// 自定义标识字段-统计标识
	String STATISTICS_FIVE = "5";

	String PRIVATE_THEME = "SYZT";

	interface theme {
		String TMP_THEME_PREIX = "launcher_upload_theme_";

	}

	interface file {
		String DOT = ".";
        String ZIP = "zip";
        String DOT_ZIP = DOT + ZIP;
	}

	interface operateType {

		int DOWNLOAD = 0;
		int USED = 1;
	}

	enum recommend {
		RECOMMEND,
		NOT_RECOMMEND
	}
}
