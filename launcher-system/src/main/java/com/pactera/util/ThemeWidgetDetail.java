package com.pactera.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.domain.LaunWidget;
import com.pactera.utlis.JsonUtils;

/**
 * @description:主题相关处理json
 * @author:LL
 * @since:2018年5月16日 下午8:17:52
 */
@Component
public class ThemeWidgetDetail {

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;
	private static ThemeWidgetDetail ntClient;

	/*
	 * @Autowired private LaunThemeConfigService launThemeConfigService; private
	 * static ThemeWidgetDetail themeConfigServiceClient;
	 */

	/**
	 * 创建静态函数调用service方法
	 * 
	 * @author LL
	 * @date 2018年5月18日 下午2:24:21
	 * @param
	 * @return void
	 */
	@PostConstruct
	public void init() {
		ntClient = this;
		ntClient.launWidgetManagerService = this.launWidgetManagerService;
		// ntClient.themeConfigServiceClient = this.themeConfigServiceClient;
	}

	/**
	 * 组合widget或创建主题时：将基础widget转化为layout.json中的格式
	 * 
	 * @author LL
	 * @date 2018年5月16日 下午8:19:27
	 * @param 基础widget
	 *            properties字段
	 * @return String
	 */
	public static Map<String, Object> baseWidget2Json(List<Map<String, Object>> json) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// List<Map> jsonToList = JsonUtils.jsonToList(json, Map.class);
		for (Map<String, Object> map : json) {
			String key = (String) map.get("key");
			Object defaultVal = map.get("default");
			returnMap.put(key, defaultVal);
		}
		return returnMap;
	}

	/**
	 * 解析widget
	 * 
	 * @author LL
	 * @date 2018年5月18日 下午2:04:45
	 * @param Map<String,Object>单个widget的map对象
	 * @param flag
	 *            是否使用相对布局判断
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parserWidget(Map<String, Object> map, boolean flag) {
		Map<String, Object> objMap = new HashMap<String, Object>();
		try {

			Integer type = Integer.parseInt(map.get("type").toString());
			String name = (String) map.get("name");
			Long id = Long.parseLong(map.get("Uid").toString());
			Long widgetId = Long.parseLong(map.get("id").toString());
			String codeId = (String) map.get("codeId");
			Integer isPoster = (Integer) map.get("isPoster");

			Map<String, String> fileMap = new HashMap<String, String>();
			// 获取File文件map集合
			if (map.get("file") != null) {
				fileMap = (Map<String, String>) map.get("file");
			}
			// 宫格布局中位置信息
			String lattice = null;
			Map<String, Object> relativeMeg = null;
			// 判断布局该widget布局方式
			Integer isRelative = (Integer) map.get("isRelative");

			if (flag && ConstantUtlis.LATTICE_LAYOUT != isRelative) {
				relativeMeg = getRelativeMeg(map);
				objMap.putAll(relativeMeg);
			} else {
				// 计算该widget在此次编辑中的位置
				lattice = getLattice(map);
				objMap.put("lattice", lattice);
			}

			// 添加公共属性
			objMap.put("id", id);
			objMap.put("codeId", codeId);
			objMap.put("name", name);
			objMap.put("fileMap", fileMap);
			objMap.put("widgetId", widgetId);
			// 该widget为海报
			if (isPoster != null && 1 == isPoster) {
				objMap.put("type", 2);
				String appId = (String) map.get("appId");
				objMap.put("posterId", widgetId);
				objMap.put("appId", appId);
				return objMap;
			}
			// 自定义widget处理逻辑
			if (type != null && type == 2) {
				// 查询自定义widget的json格式，替换其中的位置，id，name属性
				LaunWidget findWidgetById = ntClient.launWidgetManagerService.findWidgetById(widgetId);
				String customWidgetJson = findWidgetById.getCustomWidgetJson();
				Map<String, Object> customWidgetMap = (Map<String, Object>) JsonUtils.JsonToMap(customWidgetJson);
				fileMap = (Map<String, String>) customWidgetMap.get("fileMap");
				Map<String, Object> widgetMap = (Map<String, Object>) customWidgetMap.get("widgetMap");
				// 插入新属性
				widgetMap.put("codeId", codeId);

				if (flag && ConstantUtlis.LATTICE_LAYOUT != isRelative) {
					widgetMap.putAll(relativeMeg);
				} else {
					// 计算该widget在此次编辑中的位置
					widgetMap.put("lattice", lattice);
				}
				widgetMap.put("name", name);
				widgetMap.put("id", id);
				widgetMap.put("widgetId", widgetId);
				widgetMap.put("type", 1);
				widgetMap.put("fileMap", fileMap);
				return widgetMap;
			}
			objMap.put("type", 1);
			// 获取properties属性obj
			List<Map<String, Object>> object = (List<Map<String, Object>>) map.get("setting");
			Map<String, Object> baseWidgetJson = baseWidget2Json(object);
			objMap.put("properties", baseWidgetJson);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PARSE_THEME_JSON);
		}

		return objMap;
	}

	/**
	 * 封装自定义widget json信息
	 * 
	 * @author LL
	 * @date 2018年5月17日 下午2:16:36
	 * @param scolleJson内部属性json
	 * @param bottomJson底屏json
	 * @return String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String packageCustomWidget(String scolleJson, String bottomJson) {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			// 封装文件map
			Map<String, String> fileMaps = new HashMap<String, String>();

			// 封装底屏json文件
			Map<String, Object> widgetBottomMap = widgetBottom2Json(bottomJson);
			// 去除无用字段
			widgetBottomMap.remove("type");

			List<Map<String, Object>> widgetList = new ArrayList<>();

			List<Map> jsonToList = JsonUtils.jsonToList(scolleJson, Map.class);
			for (Map<String, Object> map : jsonToList) {
				Map<String, Object> objMap = parserWidget(map, false);

				// 叠加filemap
				Map<String, String> fileMap = (Map<String, String>) objMap.get("fileMap");
				fileMaps.putAll(fileMap);

				// 去除无用字段
				objMap.remove("type");
				objMap.remove("widgetId");
				objMap.remove("fileMap");
				widgetList.add(objMap);
			}
			widgetBottomMap.put("children", widgetList);

			returnMap.put("widgetMap", widgetBottomMap);
			returnMap.put("fileMap", fileMaps);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PARSE_THEME_JSON);
		}

		return JsonUtils.ObjectToJson(returnMap);
	}

	/**
	 * 封装自定义widget底屏
	 * 
	 * @author LL
	 * @date 2018年5月17日 下午2:16:36
	 * @param
	 * @return String
	 */
	public static Map<String, Object> widgetBottom2Json(String bottomJson) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 封装底屏json文件
			Map<?, ?> jsonToMap = JsonUtils.JsonToMap(bottomJson);
			Long id = Long.parseLong(jsonToMap.get("Uid").toString());
			returnMap.put("id", id);
			// String codeId = (String) jsonToMap.get("codeId");
			String codeId = "ConstraintLayout";
			returnMap.put("codeId", codeId);
			String name = (String) jsonToMap.get("name");
			returnMap.put("name", name);
			// 获取properties属性obj
			Integer columnCount = 0;
			if (jsonToMap.get("columnCount") != null) {
				columnCount = Integer.parseInt(jsonToMap.get("columnCount").toString());
			}
			Integer rowCount = 0;
			if (jsonToMap.get("rowCount") != null) {
				rowCount = Integer.parseInt(jsonToMap.get("rowCount").toString());
			}
			returnMap.put("columnCount", columnCount);
			returnMap.put("rowCount", rowCount);
			returnMap.put("height", -1);
			returnMap.put("width", -1);
			returnMap.put("type", 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PARSE_THEME_JSON);
		}
		return returnMap;
	}

	/**
	 * 封装主题底屏
	 * 
	 * @author LL
	 * @date 2018年5月17日 下午2:16:36
	 * @param
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> themeBottom2Json(String bottomJson) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 封装底屏json文件
			Map<?, ?> jsonToMap = JsonUtils.JsonToMap(bottomJson);
			String codeId = "ConstraintLayout";
			returnMap.put("codeId", codeId);
			// 获取properties属性obj
			Long rowCount = Long.parseLong(jsonToMap.get("row").toString());
			Long columnCount = Long.parseLong(jsonToMap.get("col").toString());
			returnMap.put("columnCount", columnCount);
			returnMap.put("rowCount", rowCount);

			Map<String, String> backgroundItem = (Map<String, String>) jsonToMap.get("backGrounditem");
			Map<String, String> file = (Map<String, String>) jsonToMap.get("file");
			Map<String, Map<String, String>> background = new HashMap<String, Map<String, String>>();
			background.put("background", backgroundItem);
			returnMap.put("properties", background);
			returnMap.put("fileMap", file);
			returnMap.put("type", 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PARSE_THEME_JSON);
		}
		return returnMap;
	}

	/**
	 * 封装滚动屏底屏
	 * 
	 * @author LL
	 * @date 2018年5月17日 下午2:16:36
	 * @param
	 * @return String
	 */
	public static Map<String, Object> themeScroll2Json(String bottomJson) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 封装底屏json文件
			Map<?, ?> jsonToMap = JsonUtils.JsonToMap(bottomJson);
			String codeId = "ConstraintLayout";
			returnMap.put("codeId", codeId);
			// 获取properties属性obj
			Long columnCount = Long.parseLong(jsonToMap.get("row").toString());
			Long longPalace = Long.parseLong(jsonToMap.get("col").toString());
			returnMap.put("columnCount", columnCount);
			returnMap.put("rowCount", longPalace);

			String background = (String) jsonToMap.get("background");
			Map<?, ?> backgroundMap = JsonUtils.JsonToMap(background);
			returnMap.put("properties", backgroundMap);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PARSE_THEME_JSON);
		}
		return returnMap;
	}

	/**
	 * 计算widget坐标
	 * 
	 * @author LL
	 * @date 2018年5月20日 下午5:11:06
	 * @param map
	 *            widget
	 * @return String
	 */
	public static String getLattice(Map<String, Object> map) {

		StringBuffer returnStr = new StringBuffer();
		// 左，上，右，下
		Integer left = 0;
		Integer top = 0;
		Integer right = 0;
		Integer down = 0;
		/**
		 * "x": 4, "y": 0, "width": 2, "height": 2,
		 */
		left = Math.round(Float.parseFloat(map.get("x").toString()));
		top = Math.round(Float.parseFloat(map.get("y").toString()));
		right = Math.round(Float.parseFloat(map.get("width").toString())) + left;
		down = Math.round(Float.parseFloat(map.get("height").toString())) + top;

		return returnStr.append(left).append(",").append(top).append(",").append(right).append(",").append(down)
				.toString();
	}

	/**
	 * 封装LaunThemeConfig对象
	 * 
	 * @author LL
	 * @date 2018年5月21日 上午10:43:34
	 * @param map属性信息
	 * @param themeId所属主题id
	 * @param parentId父节点id
	 * @return LaunThemeConfig
	 */
	public static LaunThemeConfig getThemeConfigObj(Map<String, Object> map, Long parentId, Long themeId) {

		LaunThemeConfig themeConfig = new LaunThemeConfig();
		themeConfig.setParentId(parentId);
		themeConfig.setLaunThemeId(themeId);

		Integer type = (Integer) map.get("type");
		Long widgetId = (Long) map.get("widgetId");
		String name = (String) map.get("name");
		themeConfig.setWidgetId(widgetId);
		themeConfig.setType(type);
		themeConfig.setConfigName(name);
		themeConfig.setRelativeLayout(2);// 使用宫格布局

		map.remove("type");
		map.remove("widgetId");
		map.remove("fileMap");
		String property = JsonUtils.ObjectToJson(map);
		themeConfig.setProperty(property);

		return themeConfig;
	}

	/**
	 * 计算wiget在绝对布局中的距离信息
	 * 
	 * @author LL
	 * @date 2018年6月30日 下午3:32:11
	 * @param
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRelativeMeg(Map<String, Object> map) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 高
		BigDecimal height = getBigDecimalVal(map.get("seerelativeH"));
		returnMap.put("height", height);
		// 宽
		BigDecimal width = getBigDecimalVal(map.get("seerelativeW"));
		returnMap.put("width", width);
		// 横向权重
		if (map.get("weightX") != null && !"".equals(map.get("weightX"))) {
			returnMap.put("horizontalWeight", Integer.parseInt(map.get("weightX").toString()));
		}
		// 纵向权重
		if (map.get("weightY") != null && !"".equals(map.get("weightY"))) {
			returnMap.put("verticalWeight", Integer.parseInt(map.get("weightY").toString()));
		}
		// 是否有锁定宽高比
		Map<String, Object> lockMap = (Map<String, Object>) map.get("lock");
		if (lockMap.get("lockRatioValue") != null && !"".equals(lockMap.get("lockRatioValue"))) {
			returnMap.put("dimensionRatio", lockMap.get("lockRatioValue").toString());
		}

		BigDecimal seerelativeX = getBigDecimalVal(map.get("seerelativeX"));
		BigDecimal seerelativeY = getBigDecimalVal(map.get("seerelativeY"));

		// 是否用到相对于某个控件
		if (map.get("isRelPosition") != null) {
			Integer isRelPosition = Integer.parseInt(map.get("isRelPosition").toString());
			if (isRelPosition == 1) {
				Map<String, Object> relPositionMap = (Map<String, Object>) map.get("relPosition");

				Map<String, Object> crosswiseLeftMap = (Map<String, Object>) relPositionMap.get("crosswiseLeft");
				Map<String, Object> leftMap = getRelPositionMap(crosswiseLeftMap, "marginLeft");
				if (leftMap != null) {
					returnMap.putAll(leftMap);
				}

				Map<String, Object> crosswiseRightMap = (Map<String, Object>) relPositionMap.get("crosswiseRight");
				Map<String, Object> rightMap = getRelPositionMap(crosswiseRightMap, "marginRight");
				if (rightMap != null) {
					returnMap.putAll(rightMap);
				}

				// 如果关联了控件位置，当左右都没关联时，默认已顶点为坐标
				if (rightMap == null && leftMap == null) {
					returnMap.put("marginLeft", seerelativeX);
					returnMap.put("leftToLeft", 0);
				}

				Map<String, Object> endwiseTopMap = (Map<String, Object>) relPositionMap.get("endwiseTop");
				Map<String, Object> topMap = getRelPositionMap(endwiseTopMap, "marginTop");
				if (topMap != null) {
					returnMap.putAll(topMap);
				}

				Map<String, Object> endwiseBottomMap = (Map<String, Object>) relPositionMap.get("endwiseBottom");
				Map<String, Object> bottomMap = getRelPositionMap(endwiseBottomMap, "marginBottom");
				if (bottomMap != null) {
					returnMap.putAll(bottomMap);
				}

				// 如果关联了控件位置，当上下都没关联时，默认已顶点为坐标
				if (bottomMap == null && topMap == null) {
					returnMap.put("marginTop", seerelativeY);
					returnMap.put("topToTop", 0);
				}
			} else {
				// 横向距离
				returnMap.put("marginLeft", seerelativeX);
				// 纵向距离
				returnMap.put("marginTop", seerelativeY);
				returnMap.put("leftToLeft", 0);
				returnMap.put("topToTop", 0);
			}
		}

		return returnMap;
	}

	/**
	 * 相对布局中，组件位置
	 * 
	 * @author LL
	 * @date 2018年7月18日 上午11:17:54
	 * @param
	 * @return String
	 */
	public static Map<String, Object> getRelPositionMap(Map<String, Object> map, String type) {

		Map<String, Object> returnMap = null;

		if (map.get("direct") != null && !"".equals(map.get("direct"))) {
			if (map.get("directWho") != null && !"".equals(map.get("directWho"))) {
				returnMap = new HashMap<String, Object>();
				String crosswiseKey = getCrosswiseKey(map.get("direct"));
				returnMap.put(crosswiseKey, Long.parseLong((String) map.get("directWho")));
				if (map.get("directdp") != null && !"".equals(map.get("directdp"))) {
					int parseInt = Integer.parseInt(map.get("directdp").toString());
					returnMap.put(type, parseInt);
				}
			}
		}

		return returnMap;
	}

	/**
	 * 获取相对于布局中，相对于组件的key
	 * 
	 * @author LL
	 * @date 2018年7月18日 上午11:12:28
	 * @param direct对齐方式
	 * @return String
	 */
	public static String getCrosswiseKey(Object direct) {
		String returnStr = "";
		if (direct != null && !"".equals(direct)) {
			int parseInt = Integer.parseInt(direct.toString());
			if (parseInt == 0) {
				returnStr = "leftToRight";
			} else if (parseInt == 1) {
				returnStr = "leftToLeft";
			} else if (parseInt == 2) {
				returnStr = "rightToLeft";
			} else if (parseInt == 3) {
				returnStr = "rightToRight";
			} else if (parseInt == 4) {
				returnStr = "topTotop";
			} else if (parseInt == 5) {
				returnStr = "topToBottom";
			} else if (parseInt == 6) {
				returnStr = "bottomToTop";
			} else if (parseInt == 7) {
				returnStr = "bottomToBottom";
			}
		}
		return returnStr;
	}

	/**
	 * 获取小数值
	 * 
	 * @author LL
	 * @date 2018年7月18日 上午10:46:50
	 * @param
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimalVal(Object value) {

		if (value != null) {
			BigDecimal x = new BigDecimal(value.toString());
			BigDecimal seerelativeX = x.setScale(2, BigDecimal.ROUND_DOWN);
			return seerelativeX;
		} else {
			return new BigDecimal(0);
		}
	}

	public static void main(String[] args) {

		String a = "2.3435435234234234";
		float parseFloat = Float.parseFloat(a);
		System.out.println(parseFloat);

		BigDecimal s = new BigDecimal(a);
		BigDecimal setScale = s.setScale(2, BigDecimal.ROUND_DOWN);
		System.out.println(9 / 2);
		System.out.println(setScale);
	}

}
