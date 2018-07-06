package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONTokener;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pactera.base.Tester;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.business.service.LaunWidgetMinPropertyService;
import com.pactera.business.service.LaunWidgetPropertyService;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetMinProperty;
import com.pactera.domain.LaunWidgetProperty;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
@SuppressWarnings("all")
public class JsonJie extends Tester {

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	@Autowired
	private LaunWidgetMinPropertyService launWidgetMinPropertyService;

	@Autowired
	private LaunWidgetPropertyService launWidgetPropertyService;
	
	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;
	


	@Test
	public void test2() {
		String other = "D:/Temp/180503826397277/lib";
		//String json = "{\"codeId\":\"MusicView\",\"widgetCodeVersion\":1,\"defaultSize\":\"3x5\",\"previewImage\":\"2a26f1e625d801d5.png\",\"name\":\"music\",\"category\":\"音乐\",\"tags\":[\"音乐\",\"音乐播放\"],\"description\":\"这是一个音乐播放器控件\",\"properties\":[{\"label\":\"测试文字\",\"key\":\"testText\",\"type\":\"text\",\"default\":\"测试文字测试文字\",\"description\":\"测试文字描述\"},{\"label\":\"测试颜色\",\"key\":\"testColor\",\"type\":\"color\",\"default\":\"测试颜色测试颜色\",\"description\":\"测试颜色描述\"},{\"label\":\"背景\",\"key\":\"background\",\"default\":{\"nine-path\":\"c3e07ca596c88992.9.png\"},\"type\":\"imageSet\",\"description\":\"用来设置音乐播放控件背景\"},{\"label\":\"翻页动画\",\"key\":\"pageAnim\",\"type\":\"singleSelect\",\"default\":\"rotate\",\"values\":[{\"lable\":\"旋转\",\"value\":\"rotate\"},{\"lable\":\"放大\",\"value\":\"enlarge\"}],\"description\":\"用来表示翻页的动画\"},{\"label\":\"功能显示\",\"key\":\"contentSettings\",\"type\":\"multiSelect\",\"default\":[\"name\",\"src\"],\"values\":[{\"label\":\"歌曲名称\",\"value\":\"name\"},{\"label\":\"歌词\",\"value\":\"lyric\"},{\"label\":\"专辑名\",\"value\":\"album\"}],\"description\":\"选择要显示的功能项（多选）\"},{\"label\":\"歌曲名\",\"key\":\"musicName\",\"default\":{\"def_text\":\"\",\"size\":\"23\",\"color\":\"#FFFFFF\",\"font_style\":[\"italic\",\"bold\",\"underline\",\"deleteline\"],\"line_spacing\":\"12\"},\"type\":\"textSet\",\"description\":\"设置歌曲名文本显示属性\"},{\"label\":\"专辑名\",\"key\":\"albumName\",\"default\":{\"def_text\":\"\",\"size\":\"23\",\"color\":\"#FFFFFF\",\"font_style\":[\"italic\",\"bold\",\"underline\",\"deleteline\"],\"line_spacing\":\"12\",\"background\":{\"color\":\"#00000000\"}},\"type\":\"textSet\",\"description\":\"设置专辑名文本显示属性\"},{\"label\":\"专辑图片\",\"key\":\"albumPic\",\"default\":{\"image\":\"90f931027e95c8b6.png\",\"scaleType\":\"01\",\"alignType\":\"LT\"},\"type\":\"imageSet\",\"description\":\"设置默认的专辑图片\"},{\"label\":\"播放按钮\",\"key\":\"playBtn\",\"default\":{\"image\":\"bff0688b3f9433d9.png\"},\"type\":\"imageSet\",\"description\":\"播放按钮图标\"},{\"label\":\"暂停按钮\",\"key\":\"playBtn\",\"default\":{\"image\":\"1511b4f6020ec61d.png\"},\"type\":\"imageSet\",\"description\":\"暂停按钮图标\"}]}";
		String json = "D:/Temp/180503826397277/widget.json";
		String imgpath = "D:/Temp/180503826397277/image";
		launWidgetManagerService.unJsonParsing(json,imgpath,other);
	}

	@Test
	public void test() {
		String json = "{\"codeId\":\"MusicView\",\"widgetCodeVersion\":1,\"defaultSize\":\"3x5\",\"previewImage\":\"2a26f1e625d801d5.png\",\"name\":\"music\",\"category\":\"音乐\",\"tags\":[\"音乐\",\"音乐播放\"],\"description\":\"这是一个音乐播放器控件\",\"properties\":[{\"label\":\"测试文字\",\"key\":\"testText\",\"type\":\"text\",\"default\":\"测试文字测试文字\",\"description\":\"测试文字描述\"},{\"label\":\"测试颜色\",\"key\":\"testColor\",\"type\":\"color\",\"default\":\"测试颜色测试颜色\",\"description\":\"测试颜色描述\"},{\"label\":\"背景\",\"key\":\"background\",\"default\":{\"nine-path\":\"c3e07ca596c88992.9.png\"},\"type\":\"imageSet\",\"description\":\"用来设置音乐播放控件背景\"},{\"label\":\"翻页动画\",\"key\":\"pageAnim\",\"type\":\"singleSelect\",\"default\":\"rotate\",\"values\":[{\"lable\":\"旋转\",\"value\":\"rotate\"},{\"lable\":\"放大\",\"value\":\"enlarge\"}],\"description\":\"用来表示翻页的动画\"},{\"label\":\"功能显示\",\"key\":\"contentSettings\",\"type\":\"multiSelect\",\"default\":[\"name\",\"src\"],\"values\":[{\"label\":\"歌曲名称\",\"value\":\"name\"},{\"label\":\"歌词\",\"value\":\"lyric\"},{\"label\":\"专辑名\",\"value\":\"album\"}],\"description\":\"选择要显示的功能项（多选）\"},{\"label\":\"歌曲名\",\"key\":\"musicName\",\"default\":{\"def_text\":\"\",\"size\":\"23\",\"color\":\"#FFFFFF\",\"font_style\":[\"italic\",\"bold\",\"underline\",\"deleteline\"],\"line_spacing\":\"12\"},\"type\":\"textSet\",\"description\":\"设置歌曲名文本显示属性\"},{\"label\":\"专辑名\",\"key\":\"albumName\",\"default\":{\"def_text\":\"\",\"size\":\"23\",\"color\":\"#FFFFFF\",\"font_style\":[\"italic\",\"bold\",\"underline\",\"deleteline\"],\"line_spacing\":\"12\",\"background\":{\"color\":\"#00000000\"}},\"type\":\"textSet\",\"description\":\"设置专辑名文本显示属性\"},{\"label\":\"专辑图片\",\"key\":\"albumPic\",\"default\":{\"image\":\"90f931027e95c8b6.png\",\"scaleType\":\"01\",\"alignType\":\"LT\"},\"type\":\"imageSet\",\"description\":\"设置默认的专辑图片\"},{\"label\":\"播放按钮\",\"key\":\"playBtn\",\"default\":{\"image\":\"bff0688b3f9433d9.png\"},\"type\":\"imageSet\",\"description\":\"播放按钮图标\"},{\"label\":\"暂停按钮\",\"key\":\"playBtn\",\"default\":{\"image\":\"1511b4f6020ec61d.png\"},\"type\":\"imageSet\",\"description\":\"暂停按钮图标\"}]}";
		JSONObject jsonObject = JSON.parseObject(json);
		String codeId = jsonObject.getString("codeId");
		Integer widgetCodeVersion = jsonObject.getInteger("widgetCodeVersion");
		String defaultSize = jsonObject.getString("defaultSize");
		String previewImage = jsonObject.getString("previewImage");
		String name = jsonObject.getString("name");
		String category = jsonObject.getString("category");
		JSONArray tags = jsonObject.getJSONArray("tags");
		String description = jsonObject.getString("description");
		JSONArray properties = jsonObject.getJSONArray("properties");
		LaunWidget launWidgetmmm = new LaunWidget();
		launWidgetmmm.setId(IdUtlis.Id());
		//launWidgetmmm.setVersion(widgetCodeVersion);
		launWidgetmmm.setDefaultSize(defaultSize);
		launWidgetmmm.setPrewImage(previewImage);
		launWidgetmmm.setName(name);
		// launWidgetManagerService.add(launWidgetmmm);

		for (Object object : properties) {
			/*
			 * LaunWidgetMinProperty lwList = new LaunWidgetMinProperty();
			 * lwList.setCreateDate(new Date()); lwList.setParantId(0L);
			 */
			LaunWidgetProperty launWidget = new LaunWidgetProperty();
			launWidget.setId(IdUtlis.Id());
			launWidget.setWidgetId(launWidgetmmm.getId());
			// launWidgetPropertyService.add(launWidget);

			try {
				Map<String, Object> obc = new HashMap<>();
				obc = (Map<String, Object>) object;
				JSONObject jso = JSON.parseObject(object.toString());
				Object o2 = new JSONTokener(jso.getString("type")).nextValue();

				if ("text".equals(o2)) {
					add(obc, launWidget);
				} else if ("number".equals(o2)) {
					add(obc, launWidget);
				} else if ("color".equals(o2)) {
					add(obc, launWidget);
				} else if ("singleSelect".equals(o2)) {
					addText(obc, launWidget);
				} else if ("multiSelect".equals(o2)) {
					addText(obc, launWidget);
				} else if ("imageSet".equals(o2)) {
					addValueList(obc, launWidget);
				} else if ("textSet".equals(o2)) {
					addValueList(obc, launWidget);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void addText(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entryt : obc.entrySet()) {
			if ("values".equals(entryt.getKey())) {
				Long preAddId = preAdd(entryt.getKey(), null, null, launWidget, 3);
				List<Map<String, String>> eList = (List<Map<String, String>>) entryt.getValue();
				for (Map<String, String> map : eList) {
					Long childPreAddId = preAdd(null, null, preAddId, launWidget, null);
					for (Map.Entry<String, String> vaMap : map.entrySet()) {
						preAdd(vaMap.getKey(), vaMap.getValue(), childPreAddId, launWidget, 0);
					}
				}
			} else {

				if ("default".equals((entryt.getKey()))) {
					String[] split = entryt.getValue().toString().split(",");
					LaunWidgetMinProperty lwList1 = new LaunWidgetMinProperty();
					if (split.length == 1) {
						preAdd(entryt.getKey(), entryt.getValue().toString(), null, launWidget, 0);
					} else {
						Long preAdd = preAdd(entryt.getKey(), null, null, launWidget, 0);
						List<String> deList = (List<String>) entryt.getValue();
						for (String string : deList) {
							preAdd(entryt.getKey(), string, preAdd, launWidget, 0);
						}
					}
				} else {
					preAdd(entryt.getKey(), entryt.getValue().toString(), null, launWidget, 0);
				}

			}
		}
	}

	public void addValueList(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entryl : obc.entrySet()) {
			if ("default".equals(entryl.getKey())) {
				Long preAdd = preAdd(entryl.getKey(), null, null, launWidget, 0);
				Map<String, Object> deMap = (Map<String, Object>) entryl.getValue();
				for (Map.Entry<String, Object> vaMap : deMap.entrySet()) {
					LaunWidgetMinProperty lwDeList1 = new LaunWidgetMinProperty();
					if ("font_style".equals(vaMap.getKey())) {

						Long preAdd2 = preAdd(vaMap.getKey(), null, preAdd, launWidget, 2);

						List<String> fList = new ArrayList<>();
						fList = (List<String>) vaMap.getValue();
						for (String S : fList) {
							preAdd(null, S, preAdd2, launWidget, 0);
						}
					} else {
						String[] split = vaMap.getValue().toString().split(":");
						if (split.length > 1) {

							Long preAdd2 = preAdd(vaMap.getKey(), null, preAdd, launWidget, 1);

							Map<String, Object> smap = (Map<String, Object>) vaMap.getValue();
							for (Map.Entry<String, Object> vvaMap : smap.entrySet()) {

								preAdd(vvaMap.getKey(), vvaMap.getValue().toString(), preAdd2, launWidget, 0);
								System.out.println(lwDeList1);
							}
						} else {

							preAdd(vaMap.getKey(), vaMap.getValue().toString(), preAdd, launWidget, 0);
							System.out.println(lwDeList1);
						}

					}
				}

			} else {
				String[] split = entryl.getValue().toString().split(":");
				if (split.length == 1) {
					preAdd(entryl.getKey(), entryl.getValue().toString(), 0L, launWidget, 0);

				} else {
					Map<String, String> bgMap = (Map<String, String>) entryl.getValue();
					for (Map.Entry<String, String> vvaMap : bgMap.entrySet()) {
						preAdd(entryl.getKey(), entryl.getValue().toString(), 0L, launWidget, 0);
					}
				}

			}
		}
	}

	public void add(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entry1 : obc.entrySet()) {
			preAdd(entry1.getKey(), entry1.getValue().toString(), null, launWidget, 0);
		}
	}

	public Long preAdd(String name, String valueData, Long parentId, LaunWidgetProperty launWidget, Integer dataType) {
		// 该条记录主键id
		Long id = IdUtlis.Id();
		LaunWidgetMinProperty launWidgetMinProperty = new LaunWidgetMinProperty();
		launWidgetMinProperty.setCreateDate(new Date());
		launWidgetMinProperty.setParantId(0L);
		launWidgetMinProperty.setId(id);
		launWidgetMinProperty.setDataType(dataType);
		if (HStringUtlis.isNotBlank(name)) {
			launWidgetMinProperty.setName(name);
		}
		if (HStringUtlis.isNotBlank(valueData)) {
			launWidgetMinProperty.setValueData(valueData);
		}
		if (parentId != null) {
			launWidgetMinProperty.setParantId(parentId);
		}
		launWidgetMinProperty.setWidgetPropertyId(launWidget.getId());
		launWidgetMinPropertyMapper.insertSelective(launWidgetMinProperty);
		return id;
	}

}
