package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.base.Tester;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.dao.LaunWidgetPropertyMapper;
import com.pactera.business.service.LaunThemeConfigService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.util.ThemeWidgetDetail;
import com.pactera.utlis.JsonUtils;

/**
 * 测试widget信息转为前端可用json
 * 
 * @author lzp
 *
 */
@SuppressWarnings("all")
public class ThemePackageT extends Tester {

	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;

	@Autowired
	private LaunWidgetManagerMapper launWidgetManagerMapper;

	@Autowired
	private LaunWidgetPropertyMapper launWidgetPropertyMapper;

	@Autowired
	private LaunThemeConfigService configService;

	@Autowired
	private LaunThemeService launThemeService;
	
	
	public void test1() {
		String themeJson = "{\"id\":\"180704186803838\",\"wideResolution\":1080,\"longResolution\":720,\"longPalace\":\"30\",\"widePalace\":\"51\",\"creator\":\"180703737054841\",\"phoneAppImgUrl\":\"group1/M00/00/54/wKgJGVs8MiCAMq2QAAL0tlar_Bo932.jpg\",\"weatherAppImgUrl\":\"group1/M00/00/63/wKgJGVtHB-iAYDxOAAAkd1JnXUc428.png\",\"musicAppImgUrl\":\"group1/M00/00/54/wKgJGVs8MiWASWqxAAO8PeUuC4s657.jpg\",\"fmAppImgUrl\":\"group1/M00/00/54/wKgJGVs8MjGALGyqAAO8PeUuC4s421.jpg\",\"level\":\"1\",\"version\":\"1\",\"typeId\":180605279586943,\"title\":\"DemoLL\",\"sTime\":1530633600000,\"eTime\":1784649600000,\"widthscroll\":22,\"heighscroll\":51,\"resolution\":1,\"gridOrRelative\":6,\"screeStyle\":\"\",\"filesJson\":{\"1530671224305-1.jpg\":\"group1/M00/00/54/wKgJGVs8MHCANr_9AAL0tlar_Bo688.jpg\",\"1530671227472-2.jpg\":\"group1/M00/00/54/wKgJGVs8MHOATMIuAAKGGXJ4sy0673.jpg\",\"1530671231172-4.jpg\":\"group1/M00/00/54/wKgJGVs8MHeAVsrPAAO8PeUuC4s179.jpg\"},\"fonts\":\"group1/M00/00/5B/wKgJGVtEH3-AWHRkAJTNmPmAEF4091.ttf\",\"status\":3}";
		String baseJson = "{\"background\":\"#000000\",\"width\":588,\"height\":392,\"row\":\"30\",\"col\":\"51\",\"selscaleType\":\"6\",\"alignType\":\"CENTER\",\"backGrounditem\":{\"color\":\"#000000\",\"image\":\"1531386052615bg.png\",\"scaleType\":\"6\",\"alignType\":\"CENTER\"},\"isnine\":false,\"file\":{\"1531386052615bg.png\":\"group1/M00/00/63/wKgJGVtHGJyAEMBpAAB4dOyp3rI464.png\"}}";
		String widgetJson = "[{\"Uid\":\"1531280392196\",\"x\":36,\"y\":0,\"width\":\"3\",\"height\":\"3\",\"name\":\"UsbStatusWidget\",\"isScroll\":0,\"setting\":[{\"description\":\"USB已连接时显示的图片\",\"default\":{\"image\":\"usbstatuswidget_usb_available.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"label\":\"USB已连接：\",\"type\":\"ImageSet\",\"key\":\"available\"}],\"backgroud\":\"usbstatuswidget_preview_usb.png\",\"category\":\"USB插入状态\",\"description\":\"这是一个USB状态控件\",\"file\":{\"com_mapbar_dynamiclauncher_widgets_UsbStatusWidget.dex\":\"group1/M00/00/5E/wKgJGVtFcPyAGgCWAAAQmD9u0a0083.dex\",\"usbstatuswidget_preview_usb.png\":\"group1/M00/00/5E/wKgJGVtFcPyACiFBAAAH0PV9QXg157.png\",\"usbstatuswidget_usb_available.png\":\"group1/M00/00/5E/wKgJGVtFcPyACQ2LAAA60qTniuA355.png\"},\"codeId\":\"com.mapbar.dynamiclauncher.widgets.usbstatus.UsbStatusWidget\",\"appId\":\"\",\"id\":180710163102365,\"isPoster\":0,\"type\":0,\"Zindex\":29,\"relativeH\":34,\"relativeW\":33.007407407407406,\"relativeX\":393.17647058823536,\"relativeY\":0,\"seerelativeH\":72,\"seerelativeW\":64,\"seerelativeX\":762.3529411764707,\"seerelativeY\":0,\"isRelative\":0,\"relPosition\":{},\"weightX\":\"\",\"weightY\":\"\"}]";
		// LaunThemeAdministration administration =
		// JsonUtils.jsonToClass(themeJson, LaunThemeAdministration.class);

		launThemeService.saveTheme(baseJson, widgetJson, themeJson, 1);
	}

	public void test2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wKgJElsH20OACU_pAAAiB_pGhMQ389.jpg", "/group1/M00/00/14/wKgJElsH20OACU_pAAAiB_pGhMQ389.jpg");
		Long themeId = 180526635159592L;
		launThemeService.saveAssembleConfigJson(null, map);
	}

	@Test
	public void saveThemeConfig() {

		Long themeId = 1L;
		String baseJson = "{\"background\":\"#000000\",\"width\":588,\"height\":392,\"row\":\"30\",\"col\":\"51\",\"selscaleType\":\"6\",\"alignType\":\"CENTER\",\"backGrounditem\":{\"color\":\"#000000\",\"image\":\"1531386052615bg.png\",\"scaleType\":\"6\",\"alignType\":\"CENTER\"},\"isnine\":false,\"file\":{\"1531386052615bg.png\":\"group1/M00/00/63/wKgJGVtHGJyAEMBpAAB4dOyp3rI464.png\"}}";
		String widgetJson = "[{\"Uid\":\"1531280392196\",\"x\":36,\"y\":0,\"width\":\"3\",\"height\":\"3\",\"name\":\"UsbStatusWidget\",\"isScroll\":0,\"setting\":[{\"description\":\"USB已连接时显示的图片\",\"default\":{\"image\":\"usbstatuswidget_usb_available.png\",\"scaleType\":\"01\",\"alignType\":\"LT\",\"color\":\"\"},\"label\":\"USB已连接：\",\"type\":\"ImageSet\",\"key\":\"available\"}],\"backgroud\":\"usbstatuswidget_preview_usb.png\",\"category\":\"USB插入状态\",\"description\":\"这是一个USB状态控件\",\"file\":{\"com_mapbar_dynamiclauncher_widgets_UsbStatusWidget.dex\":\"group1/M00/00/5E/wKgJGVtFcPyAGgCWAAAQmD9u0a0083.dex\",\"usbstatuswidget_preview_usb.png\":\"group1/M00/00/5E/wKgJGVtFcPyACiFBAAAH0PV9QXg157.png\",\"usbstatuswidget_usb_available.png\":\"group1/M00/00/5E/wKgJGVtFcPyACQ2LAAA60qTniuA355.png\"},\"codeId\":\"com.mapbar.dynamiclauncher.widgets.usbstatus.UsbStatusWidget\",\"appId\":\"\",\"id\":180710163102365,\"isPoster\":0,\"type\":0,\"Zindex\":29,\"relativeH\":34,\"relativeW\":33.007407407407406,\"relativeX\":393.17647058823536,\"relativeY\":0,\"seerelativeH\":72,\"seerelativeW\":64,\"seerelativeX\":762.3529411764707,\"seerelativeY\":0,\"isRelative\":0,\"relPosition\":{},\"weightX\":\"\",\"weightY\":\"\"}]";

		// 定义下载文件map
		Map<String, String> fileMaps = new HashMap<String, String>();

		/**
		 * 保存主题父级config
		 */
		Map<String, Object> widgetBottom2Json = ThemeWidgetDetail.themeBottom2Json(baseJson);
		Object fileMapObj = widgetBottom2Json.get("fileMap");
		if (fileMapObj != null) {
			fileMaps.putAll((Map<String, String>) fileMapObj);
		}
		// 封装保存对象
		LaunThemeConfig themeConfigObj = ThemeWidgetDetail.getThemeConfigObj(widgetBottom2Json, 0L, themeId);
		// 持久化数据
		Long parentConfigId = configService.save(themeConfigObj);
		List<Map> jsonToList = JsonUtils.jsonToList(widgetJson, Map.class);
		for (Map<String, Object> map : jsonToList) {

			Integer isScroll = map.get("isScroll") == null ? null : Integer.parseInt(map.get("isScroll").toString());

			if (isScroll != null && isScroll == 1) {// 处理滚动屏数据
				/**
				 * 插入滚动屏信息
				 */
				String id = (String) map.get("Uid");
				// 判断布局该widget布局方式
				Integer isRelative = (Integer) map.get("isRelative");

				Map<String, Object> viewpagerJson = new HashMap<String, Object>();
				if (ConstantUtlis.LATTICE_LAYOUT == isRelative) {
					// 计算该widget在此次编辑中的位置
					String lattice = ThemeWidgetDetail.getLattice(map);
					viewpagerJson.put("lattice", lattice);
				} else {
					Map<String, Object> relativeMeg = ThemeWidgetDetail.getRelativeMeg(map);
					viewpagerJson.putAll(relativeMeg);
				}
				// 滚屏属性
				Map<String, Object> object = (Map<String, Object>) map.get("setting");

				Map<String, Integer> properties = new HashMap<String, Integer>();
				if (object.get("approw") != null) {
					properties.put("appColumnNum", Integer.parseInt(object.get("appcol").toString()));
				}
				if (object.get("appcol") != null) {
					properties.put("appColumnNum", Integer.parseInt(object.get("approw").toString()));
				}

				viewpagerJson.put("id", id);
				viewpagerJson.put("name", "ViewPager");
				viewpagerJson.put("codeId", "ViewPager");
				viewpagerJson.put("type", 1);
				viewpagerJson.put("properties", properties);
				LaunThemeConfig viewpagerConfig = ThemeWidgetDetail.getThemeConfigObj(viewpagerJson, parentConfigId,
						themeId);
				// 持久化数据
				Long viewpagerConfigId = configService.save(viewpagerConfig);

				// 滚屏的长宽
				Integer columnCount = Integer.parseInt(object.get("col").toString());
				Integer rowCount = Integer.parseInt(object.get("row").toString());
				// 滚动屏下有多个面板(contentLayOut),
				List<Map<String, Object>> widgetsJsonList = (List<Map<String, Object>>) map.get("widgets");
				int index = 0;
				for (Map<String, Object> scrollWidgetMap : widgetsJsonList) {

					/**
					 * 插入滚动屏内每一个屏幕信息
					 */
					Map<String, Object> bottomMap = new HashMap<String, Object>();
					bottomMap.put("id", (Long) scrollWidgetMap.get("Uid"));
					bottomMap.put("name", "page_" + index++);
					bottomMap.put("columnCount", columnCount);
					bottomMap.put("rowCount", rowCount);
					bottomMap.put("Uid", System.currentTimeMillis());
					Map<String, Object> widgetBottom2Json2 = ThemeWidgetDetail
							.widgetBottom2Json(JsonUtils.ObjectToJson(bottomMap));
					LaunThemeConfig pageOne = ThemeWidgetDetail.getThemeConfigObj(widgetBottom2Json2, viewpagerConfigId,
							themeId);
					// 持久化数据
					Long pageOneId = configService.save(pageOne);

					// 每个面板的wideget在data里存放
					List<Map<String, Object>> widgetList = (List<Map<String, Object>>) scrollWidgetMap.get("data");
					for (Map<String, Object> map2 : widgetList) {
						Map<String, Object> widget = ThemeWidgetDetail.parserWidget(map2, true);
						Map<String, String> fileMap = (Map<String, String>) widget.get("fileMap");
						fileMaps.putAll(fileMap);
						LaunThemeConfig widgetOnde = ThemeWidgetDetail.getThemeConfigObj(widget, pageOneId, themeId);
						// 持久化数据
						configService.save(widgetOnde);
					}
				}
				continue;
			} else {
				Map<String, Object> widget = ThemeWidgetDetail.parserWidget(map, true);
				Map<String, String> fileMap = (Map<String, String>) widget.get("fileMap");
				if (fileMap != null) {
					fileMaps.putAll(fileMap);
				}
				LaunThemeConfig widgetOnde = ThemeWidgetDetail.getThemeConfigObj(widget, parentConfigId, themeId);
				// 持久化数据
				configService.save(widgetOnde);
			}
		}
	
	}

	public void test() {

		Long themeId = 180523764457554L;
		List<LaunThemeConfig> themeConfigList = configService.findByThemeId(themeId);

		Map<String, List<LaunThemeConfig>> map = new HashMap<String, List<LaunThemeConfig>>();
		// 封装layout.json
		for (LaunThemeConfig launThemeConfig : themeConfigList) {
			Long parentId = launThemeConfig.getParentId();
			List<LaunThemeConfig> valueList = map.get(parentId.toString());
			if (valueList == null) {
				List<LaunThemeConfig> list = new ArrayList<>();
				list.add(launThemeConfig);
				map.put(parentId.toString(), list);
			} else {
				valueList.add(launThemeConfig);
			}
		}

		// 获取根面板json
		List<Map<String, Object>> packageMap = packageMap("0", map);

		String objectToJson = JsonUtils.ObjectToJson(packageMap.get(0));
		System.out.println(objectToJson);
	}

	public List<Map<String, Object>> packageMap(String widgetId, Map<String, List<LaunThemeConfig>> map) {
		List<Map<String, Object>> returnLit = new ArrayList<>();
		List<LaunThemeConfig> list = map.get(widgetId.toString());
		if (list != null) {
			for (LaunThemeConfig launThemeConfig : list) {
				String id = launThemeConfig.getId().toString();
				String property = launThemeConfig.getProperty();
				Map<String, Object> toMap = (Map<String, Object>) JsonUtils.JsonToMap(property);
				if (map.get(id) != null) {
					List<Map<String, Object>> packageMap = packageMap(id, map);
					if (packageMap.size() > 0) {
						toMap.put("children", packageMap);
					}
				}
				returnLit.add(toMap);
			}
		}
		return returnLit;
	}

	public static void main(String[] args) {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(0L, "sss");
		System.out.println(map.get(0L));
	}
}
