package com.pactera.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
			returnMap.put("index", Integer.parseInt(jsonToMap.get("index").toString()));
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

	@SuppressWarnings("unchecked")
	public static Map<String, Object> orderViewPager(Map<String, Object> map) {
		List<Map<String, Object>> object = (List<Map<String, Object>>) map.get("children");

		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

		// 排序后的页面
		List<Map<String, Object>> orderList = new ArrayList<>();

		for (Map<String, Object> map2 : object) {
			String codeId = map2.get("codeId").toString();
			if (codeId != null && "ViewPager".equals(codeId)) {
				List<Map<String, Object>> viewPager = (List<Map<String, Object>>) map2.get("children");
				for (int i = 0; i < viewPager.size(); i++) {
					orderList.add(null);
				}
				for (Map<String, Object> map3 : viewPager) {
					int parseInt = Integer.parseInt(map3.get("index").toString());
					map3.remove("index");
					orderList.set(parseInt - 1, map3);
				}
				orderList.removeAll(Collections.singleton(null));

				map2.put("children", orderList);
				childList.add(map2);
			} else {
				childList.add(map2);
			}
		}
		map.put("children", childList);
		return map;
	}

	public static void main(String[] args) {

		/*
		 * String a = "2.3435435234234234"; float parseFloat =
		 * Float.parseFloat(a); System.out.println(parseFloat);
		 * 
		 * BigDecimal s = new BigDecimal(a); BigDecimal setScale = s.setScale(2,
		 * BigDecimal.ROUND_DOWN); System.out.println(9 / 2);
		 * System.out.println(setScale);
		 */
		String test = "{\"codeId\":\"ConstraintLayout\",\"columnCount\":51,\"rowCount\":30,\"properties\":{\"background\":{\"color\":\"#000000\",\"image\":\"1531386052615bg.png\",\"scaleType\":\"6\",\"alignType\":\"CENTER\"}},\"children\":[{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.wifistatus.WifiStatusWidget\",\"lattice\":\"45,0,48,3\",\"name\":\"WifiStatusWidget\",\"id\":1531469185770,\"properties\":{\"unavailable\":{\"image\":\"wifistatuswidget_wifi_unavailable.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal04\":{\"image\":\"wifistatuswidget_wifi_signal04.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal01\":{\"image\":\"wifistatuswidget_wifi_signal01.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal02\":{\"image\":\"wifistatuswidget_wifi_signal02.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal03\":{\"image\":\"wifistatuswidget_wifi_signal03.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"}}},{\"codeId\":\"ViewPager\",\"lattice\":\"0,7,51,30\",\"name\":\"ViewPager\",\"id\":\"1531470307831\",\"properties\":{\"appColumnNum\":5},\"children\":[{\"codeId\":\"ConstraintLayout\",\"name\":\"page_2\",\"width\":-1,\"id\":1531980428135,\"columnCount\":51,\"rowCount\":23,\"height\":-1,\"index\":2,\"children\":[{\"codeId\":\"PosterView\",\"lattice\":\"18,11,32,22\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471252363,\"posterId\":180711614283226},{\"codeId\":\"PosterView\",\"lattice\":\"33,0,49,22\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471264988,\"posterId\":180711992353082},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.phone.PhoneWidget\",\"lattice\":\"2,0,17,22\",\"name\":\"PhoneWidget\",\"id\":1531471010937,\"properties\":{\"phone_stop_calls_img_pressed\":{\"image\":\"phone_widget_stop_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_stop_calls_img\":{\"image\":\"phone_widget_stop_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_phone_bluetooth_name\":{\"size\":\"20\",\"color\":\"#118efc\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"李明的iphone\"},\"phone_phone_time\":{\"size\":\"20\",\"color\":\"#118efc\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"1:33\"},\"phone_answer_calls_img_pressed\":{\"image\":\"phone_widget_answer_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"挂断\"},\"phone_display_pic_height\":120,\"phone_display_pic_top\":0,\"phone_display_bg\":{\"image\":\"phone_widget_display_bg.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_answer_calls_img\":{\"image\":\"phone_widget_answer_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_img_pressed\":{\"image\":\"phone_widget_stop_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_widget_background\":{\"image\":\"phone_widget_background.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_name\":{\"size\":\"26\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"李晓明\"},\"phone_stop_calls_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"拒接\"},\"phone_bluetooth_setting_text\":{\"size\":\"26\",\"color\":\"#ffffff\",\"background\":{\"image\":\"phone_widget_bluetooth_setting_normal.png\"},\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"去设置\"},\"phone_display_pic_width\":120,\"phone_answer_calls_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"接听\"},\"phone_bluetooth_noconnect_text\":{\"size\":\"20\",\"color\":\"#a4a9af\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"蓝牙未连接\"},\"phone_display_pic\":{\"image\":\"phone_widget_display_pic.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_bg_width\":180,\"phone_model_text\":{\"size\":\"26\",\"color\":\"#a4a9af\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"电话\"},\"phone_widget_bluetooth_background\":{\"image\":\"phone_widget_bluetooth_background.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_img\":{\"image\":\"phone_widget_stop_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_bg_height\":180,\"phone_display_bg_top\":0}},{\"codeId\":\"PosterView\",\"lattice\":\"18,0,32,11\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471236702,\"posterId\":180711614283226}]},{\"codeId\":\"ConstraintLayout\",\"name\":\"page_0\",\"width\":-1,\"id\":1531980428135,\"columnCount\":51,\"rowCount\":23,\"height\":-1,\"index\":0,\"children\":[{\"codeId\":\"PosterView\",\"lattice\":\"18,11,32,22\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471252363,\"posterId\":180711614283226},{\"codeId\":\"PosterView\",\"lattice\":\"33,0,49,22\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471264988,\"posterId\":180711992353082},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.phone.PhoneWidget\",\"lattice\":\"2,0,17,22\",\"name\":\"PhoneWidget\",\"id\":1531471010937,\"properties\":{\"phone_stop_calls_img_pressed\":{\"image\":\"phone_widget_stop_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_stop_calls_img\":{\"image\":\"phone_widget_stop_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_phone_bluetooth_name\":{\"size\":\"20\",\"color\":\"#118efc\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"李明的iphone\"},\"phone_phone_time\":{\"size\":\"20\",\"color\":\"#118efc\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"1:33\"},\"phone_answer_calls_img_pressed\":{\"image\":\"phone_widget_answer_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"挂断\"},\"phone_display_pic_height\":120,\"phone_display_pic_top\":0,\"phone_display_bg\":{\"image\":\"phone_widget_display_bg.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_answer_calls_img\":{\"image\":\"phone_widget_answer_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_img_pressed\":{\"image\":\"phone_widget_stop_calls_selected.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_widget_background\":{\"image\":\"phone_widget_background.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_name\":{\"size\":\"26\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"李晓明\"},\"phone_stop_calls_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"拒接\"},\"phone_bluetooth_setting_text\":{\"size\":\"26\",\"color\":\"#ffffff\",\"background\":{\"image\":\"phone_widget_bluetooth_setting_normal.png\"},\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"去设置\"},\"phone_display_pic_width\":120,\"phone_answer_calls_text\":{\"size\":\"16\",\"color\":\"#ffffff\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"接听\"},\"phone_bluetooth_noconnect_text\":{\"size\":\"20\",\"color\":\"#a4a9af\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"蓝牙未连接\"},\"phone_display_pic\":{\"image\":\"phone_widget_display_pic.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_bg_width\":180,\"phone_model_text\":{\"size\":\"26\",\"color\":\"#a4a9af\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"电话\"},\"phone_widget_bluetooth_background\":{\"image\":\"phone_widget_bluetooth_background.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_hang_up_img\":{\"image\":\"phone_widget_stop_calls_normal.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"phone_display_bg_height\":180,\"phone_display_bg_top\":0}},{\"codeId\":\"PosterView\",\"lattice\":\"18,0,32,11\",\"appId\":\"com.mapbar.android.mapbarmap\",\"name\":\"图吧导航\",\"id\":1531471236702,\"posterId\":180711614283226}]},{\"codeId\":\"ConstraintLayout\",\"name\":\"page_1\",\"width\":-1,\"id\":1531980428131,\"columnCount\":51,\"rowCount\":23,\"index\":1,\"height\":-1,\"children\":[{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.navi02.MapBarNaviWidget\",\"lattice\":\"2,0,17,22\",\"name\":\"MapBarNaviWidget\",\"id\":1531470658612,\"properties\":{\"naviTurnIconInnerWidth\":\"160\",\"naviTurnIconWidth\":\"180\",\"naviUnExpandDirection\":{\"size\":\"20\",\"color\":\"#a4a9af\",\"def_text\":\"\",\"alignType\":\"\",\"font_style\":[],\"line_spacing\":\"\"},\"naviTurnIconHeight\":\"180\",\"naviTurnIconInnerHeight\":\"160\",\"homePressPic\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_mapbarnaviwidget_navi_icon_home_press.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"homePic\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_mapbarnaviwidget_navi_icon_home.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"naviTurnIconBg\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_navi_mapbarnaviwidget_started_turn_bg.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"naviTurnIconMarginTop\":\"40\",\"naviUnExpandDistance\":{\"size\":\"40\",\"color\":\"#FFFFFF\",\"def_text\":\"\",\"alignType\":\"\",\"font_style\":[],\"line_spacing\":\"\"},\"naviStartedUnexpandBg\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_navi_mapbarnaviwidget_started_unexpand_bg.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"naviUnExpandRoadName\":{\"size\":\"32\",\"color\":\"#118efc\",\"def_text\":\"\",\"alignType\":\"\",\"font_style\":[],\"line_spacing\":\"\"},\"companyPic\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_navi_mapbarnaviwidget_icon_company.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"background\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_navi_mapbarnaviwidget_unstarted_bg.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"},\"naviName\":{\"size\":\"26\",\"color\":\"#a4a9af\",\"def_text\":\"导航\",\"alignType\":\"\",\"font_style\":[],\"line_spacing\":\"\"},\"companyPicPress\":{\"image\":\"com_mapbar_dynamiclauncher_widgets_navi_mapbarnaviwidget_icon_company_press.png\",\"color\":\"\",\"alignType\":\"\",\"scaleType\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.radio.RadioWidget02\",\"lattice\":\"18,0,33,22\",\"name\":\"radio\",\"id\":1531723072384,\"properties\":{\"prevButtonPressed\":{\"image\":\"1531722728433.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelHeight\":\"134\",\"pauseButtonPressed\":{\"image\":\"1531722718825.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelPadding\":\"20\",\"channelIDWidth\":\"238\",\"animationBackground\":{\"image\":\"1531722639412.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"favoriteSelected\":{\"size\":\"22\",\"color\":\"#FBE28D\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"channelNameWidth\":\"238\",\"unPlayButton\":{\"image\":\"1531722709324.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"unPlayButtonPressed\":{\"image\":\"1531722712039.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelMethod\":{\"size\":\"22\",\"color\":\"#118EFC\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"favoriteEmpty\":{\"size\":\"22\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"isShowSwitchDrawable\":\"true\",\"channelNameHeight\":\"50\",\"playButtonPressed\":{\"image\":\"1531722705251.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"prevButton\":{\"image\":\"1531722725316.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"animBackgroundSize\":\"150\",\"unPlayBackground\":{\"image\":\"1531722870367.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"animBackground\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"},\"channelIDHeight\":\"50\",\"pauseButton\":{\"image\":\"1531722715966.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelMethodHeight\":\"40\",\"favoriteBackground\":{\"image\":\"1531722848074.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"nextButtonPressed\":{\"image\":\"1531722737466.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"switchToPlayDrawablePressed\":{\"image\":\"1531721252972.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"playButton\":{\"image\":\"1531722702041.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"nextButton\":{\"image\":\"1531722733692.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"switchToListDrawablePressed\":{\"image\":\"1531721239496.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelMethodBackground\":{\"image\":\"1531722576263.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"marginBetweenChannelIDAndName\":\"5\",\"unPlayTextMarginTop\":\"256\",\"switchToListDrawable\":{\"image\":\"1531721236310.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"unPlayText\":{\"size\":\"26\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"isHasAnimator\":\"false\",\"background\":{\"image\":\"1531721227111.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelWidth\":\"238\",\"favoriteSelectedIcon\":{\"image\":\"1531722843568.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"switchToPlayDrawable\":{\"image\":\"1531721248387.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"channelName\":{\"size\":\"20\",\"color\":\"#C39D60\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"marginBottom\":\"30\",\"channelNameBackground\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"},\"favoriteNormal\":{\"size\":\"22\",\"color\":\"#B3B3B4\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"channelID\":{\"size\":\"48\",\"color\":\"#FBE28D\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"channelIDBackground\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"},\"channelMethodWidth\":\"80\",\"marginTop\":\"83\",\"favoriteSelectedBackground\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.btmusic.BTMusicWidget02\",\"lattice\":\"34,0,49,22\",\"name\":\"musicView\",\"id\":1531739861858,\"properties\":{\"localAblumnSize\":\"104\",\"prevButtonPressed\":{\"image\":\"1531732965810.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localAblumMarginTop\":\"48\",\"localAblumnBackgroundHeight\":\"174\",\"btUnPlayTextMarginTop\":\"250\",\"songer\":{\"size\":\"20\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"isBTHasAnimator\":\"true\",\"unPlayButtonPressed\":{\"image\":\"1531732984033.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btAblumMarginTop\":\"48\",\"isLocalAblumCircle\":\"true\",\"btAblumnBackground\":{\"image\":\"1531733135980.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localAnimDrawable\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"},\"localSwitchDrawablePressed\":{\"image\":\"1531733016382.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"prevButton\":{\"image\":\"1531732962704.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btAblumnWidth\":\"180\",\"btAblumnHeight\":\"180\",\"gotoSettingWidth\":\"180\",\"pauseButton\":{\"image\":\"1531732988178.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localAblumnWidth\":\"180\",\"localAblumnBackgroundWidth\":\"174\",\"btAnimSize\":\"94\",\"btBackground\":{\"image\":\"1531732934630.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"nextButtonPressed\":{\"image\":\"1531733007903.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"nextButton\":{\"image\":\"1531733005287.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btAblumnBackgroundWidth\":\"174\",\"localAblumnBackgroundShadow\":{\"image\":\"1531733162628.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btSwitchDrawable\":{\"image\":\"1531733019988.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btAnimDrawable\":{\"image\":\"\",\"alignType\":\"\",\"color\":\"#00FFFFFF\",\"scaleType\":\"\"},\"btPlayMarginBottom\":\"30\",\"pauseButtonPressed\":{\"image\":\"1531732993360.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btSwitchDrawablePressed\":{\"image\":\"1531733024605.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"isLocalHasAnimator\":\"true\",\"btAblumnBackgroundHeight\":\"174\",\"unPlayButton\":{\"image\":\"1531732977049.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"gotoBTSetting\":{\"size\":\"26\",\"color\":\"#EFD17B\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"gotoSettingHeight\":\"80\",\"btAblumnBackgroundShadow\":{\"image\":\"1531733156905.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"unSettingBT\":{\"size\":\"20\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"btDefaultAblum\":{\"image\":\"1531733029045.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"isBTAblumCircle\":\"true\",\"localUnPlayText\":{\"size\":\"26\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"unPlayDefaultBT\":{\"image\":\"1531732947584.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"playButtonPressed\":{\"image\":\"1531732973115.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"gotoBTSettingBackgroundPressed\":{\"image\":\"1531733313127.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localDefaultAblum\":{\"image\":\"1531733032148.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localAnimSize\":\"94\",\"localBackground\":{\"image\":\"1531732937712.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"gotoBTSettingBackground\":{\"image\":\"1531733307541.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localPlayMarginBottom\":\"30\",\"playButton\":{\"image\":\"1531732969693.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localUnPlayTextMarginTop\":\"250\",\"musicName\":{\"size\":\"26\",\"color\":\"#FBE28D\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"btUnPlayText\":{\"size\":\"26\",\"color\":\"#FFFFFF\",\"background\":\"\",\"line_spacing\":\"\",\"alignType\":\"\",\"font_style\":[],\"def_text\":\"\"},\"localAblumnBackground\":{\"image\":\"1531733141068.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localMusicNameMarginTop\":\"250\",\"unPlayDefaultLocal\":{\"image\":\"1531732944814.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"localAblumnHeight\":\"180\",\"btAblumnSize\":\"104\",\"localSwitchDrawable\":{\"image\":\"1531733014034.png\",\"alignType\":\"\",\"color\":\"\",\"scaleType\":\"\"},\"btMusicNameMarginTop\":\"250\"}}]}]},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.earphonestatus.EarPhoneStatusWidget\",\"lattice\":\"42,0,45,3\",\"name\":\"EarPhoneStatusWidget\",\"id\":1531469202496,\"properties\":{\"mute\":{\"image\":\"earphonestatuswidget_earphone_mute.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"unmute\":{\"image\":\"earphonestatuswidget_earphone_unmute.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.weathertemperature.WeatherTemperatureWidget\",\"lattice\":\"16,3,19,5\",\"name\":\"Temperature\",\"id\":1531469401056,\"properties\":{\"style\":{\"text\":\"26°\",\"size\":\"25\",\"color\":\"#FFFFFF\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"LT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgetlib.widget.TextWidget\",\"lattice\":\"40,5,46,7\",\"name\":\"TextWidget\",\"id\":1531469680347,\"properties\":{\"style\":{\"alignType\":\"RT\",\"size\":\"18\",\"color\":\"#999999\",\"def_text\":\"今日限行：\",\"background\":{\"color\":\"#00000000\"},\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.pmlevel.PMLevelWidget\",\"lattice\":\"20,5,23,7\",\"name\":\"PMLevelWidget\",\"id\":1531469495324,\"properties\":{\"style\":{\"text\":\"优\",\"size\":\"18\",\"color\":\"#999999\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"LT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.calendar.CalenderWidget\",\"lattice\":\"2,3,11,7\",\"name\":\"time\",\"id\":1531469243640,\"properties\":{\"format\":\"HH:mm\",\"style\":{\"text\":\"19:22\",\"alignType\":\"LT\",\"size\":\"60\",\"color\":\"#FFFFFF\",\"background\":{\"color\":\"#00000000\"},\"def_text\":\"\",\"font_style\":[\"bold\",\"italic\",\"underline\"],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.trafficcity.TrafficCityWidget\",\"lattice\":\"40,3,49,5\",\"name\":\"TrafficCityWidget\",\"id\":1531469712325,\"properties\":{\"style\":{\"text\":\"北京\",\"size\":\"18\",\"color\":\"#999999\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"RT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.weatherdesc.WeatherDescWidget\",\"lattice\":\"23,5,31,7\",\"name\":\"WeatherDescWidget\",\"id\":1531469581306,\"properties\":{\"style\":{\"text\":\"晴\",\"size\":\"18\",\"color\":\"#999999\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"LT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.trifficnumber.TrafficNumberWidget\",\"lattice\":\"46,5,49,7\",\"name\":\"TrafficNumberWidget\",\"id\":1531469631083,\"properties\":{\"style\":{\"text\":\"2 7\",\"size\":\"18\",\"color\":\"#FFFFFF\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"RT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.weathericon.WeatherIconWidget\",\"lattice\":\"11,3,16,7\",\"name\":\"WeatherIconWidget\",\"id\":1531469291119,\"properties\":{\"cloudy_sunny\":{\"image\":\"weatherinfosource_icon_cloudy_sun.png\",\"scaleType\":\"5\",\"alignType\":\"LT\",\"color\":\"\"},\"cloudy\":{\"image\":\"weatherinfosource_icon_cloudy.png\",\"scaleType\":\"5\",\"alignType\":\"LT\",\"color\":\"\"},\"sunny\":{\"image\":\"weatherinfosource_icon_sun.png\",\"scaleType\":\"5\",\"alignType\":\"LT\",\"color\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.weathertemperaturerange.WeatherTemperatureRangeWidget\",\"lattice\":\"16,5,20,7\",\"name\":\"TemperatureRange\",\"id\":1531469435743,\"properties\":{\"style\":{\"text\":\"晴\",\"size\":\"18\",\"color\":\"#999999\",\"background\":{\"color\":\"#00000000\"},\"alignType\":\"LT\",\"def_text\":\"\",\"font_style\":[],\"line_spacing\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.mobilestatus.MobileStatusWidget\",\"lattice\":\"48,0,51,3\",\"name\":\"MobileStatusWidget\",\"id\":1531469168062,\"properties\":{\"unavailable\":{\"image\":\"mobilestatuswidget_mobile_unavailable.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal04\":{\"image\":\"mobilestatuswidget_mobile_signal04.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal05\":{\"image\":\"mobilestatuswidget_mobile_signal05.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal01\":{\"image\":\"mobilestatuswidget_mobile_signal01.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal02\":{\"image\":\"mobilestatuswidget_mobile_signal02.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"signal03\":{\"image\":\"mobilestatuswidget_mobile_signal03.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.usbstatus.UsbStatusWidget\",\"lattice\":\"36,0,39,3\",\"name\":\"UsbStatusWidget\",\"id\":1531469222387,\"properties\":{\"unavailable\":{\"image\":\"usbstatuswidget_usb_unavailable.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"available\":{\"image\":\"usbstatuswidget_usb_available.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"}}},{\"codeId\":\"com.mapbar.dynamiclauncher.widgets.blzstatus.BlzStatusWidget\",\"lattice\":\"39,0,42,3\",\"name\":\"BlzStatusWidget\",\"id\":1531469210064,\"properties\":{\"connected\":{\"image\":\"blzstatuswidget_blz_connected.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"unread\":{\"image\":\"blzstatuswidget_blz_unread.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"unavailable\":{\"image\":\"blzstatuswidget_blz_unavailable.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"available\":{\"image\":\"blzstatuswidget_blz_available.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"}}}]}";
		Map<String, Object> jsonToMap = (Map<String, Object>) JsonUtils.JsonToMap(test);
		Map<String, Object> orderViewPager = orderViewPager(jsonToMap);
		System.out.println(JsonUtils.ObjectToJson(orderViewPager));
	}

}
