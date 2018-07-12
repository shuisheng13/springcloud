package com.pactera.serviceT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.pactera.base.Tester;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunWidgetFileMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.dao.LaunWidgetPropertyMapper;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetFile;
import com.pactera.domain.LaunWidgetMinProperty;
import com.pactera.domain.LaunWidgetProperty;
import com.pactera.util.widgetToJson;
import com.pactera.utlis.FileTool;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunWidgetVo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 测试widget信息转为前端可用json
 * 
 * @author lzp
 *
 */
@SuppressWarnings("all")
public class JsonConvert extends Tester {

	private static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;

	@Autowired
	private LaunWidgetManagerMapper launWidgetManagerMapper;
	
	@Autowired
	private LaunWidgetFileMapper launWidgetFileMapper;

	@Autowired
	private LaunWidgetPropertyMapper launWidgetPropertyMapper;
	
	@Test
	public void test16() {
		LaunWidget widget = launWidgetManagerMapper.selectByPrimaryKey(180602089839391L);
		LaunWidgetVo widgetvo = new LaunWidgetVo();
		try {
			BeanUtils.copyProperties(widgetvo,widget);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(widgetvo.toString());
	}

	
	@Test
	public void test4() throws Exception {
		List<LaunWidget> list = launWidgetManagerMapper.selectAll();
		String json = widgetToJson.widget2Json(list).toString();
		List outlist = new ArrayList();// 要转json的list
		try {
			List<Map> jlist = mapper.readValue(json, List.class);
			String setting = "";
			if (jlist != null) {
				for (Map map : jlist) {
					Map mm = viewJsonTree1(map);
					Map s = viewJsonTree1(mm.get("setting"));
					for (Iterator ite = s.entrySet().iterator(); ite.hasNext();) {
						Map.Entry e = (Map.Entry) ite.next();
						if (e.getKey().equals("ImageSet")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "ImageSet");
								outlist.add(m1);
							}
						}else if (e.getKey().equals("multiSelect")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "multiSelect");
								outlist.add(m1);
							}
						}if (e.getKey().equals("singleSelect")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "singleSelect");
								outlist.add(m1);
							}
						}if (e.getKey().equals("TextSet")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "TextSet");
								outlist.add(m1);
							}
						}if (e.getKey().equals("number")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "number");
								outlist.add(m1);
							}
						}if (e.getKey().equals("color")) {
							List<Map> list1 = viewJsonTree2(e.getValue());
							for(Map m1 : list1 ) {
								m1.put("type", "color");
								outlist.add(m1);
							}
						}
					}
				}
			}
			System.out.println(JSON.toJSON(outlist).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void test5() throws Exception {
		List<LaunWidget> list = launWidgetManagerMapper.selectAll();
		String o = widgetToJson.widget2Jsonnew(list);
		widgetToJson.json2widget(o);
	}

	@Test
	public void test3() throws Exception {
		List<LaunWidget> list = launWidgetManagerMapper.selectAll();
		String o = widgetToJson.widget2Jsonnew(list);
		System.out.println(o);
	}
	
	@Test
	public void test7() throws Exception{
		long widgetId = 180516091185616L;
		LaunWidget launwidget = launWidgetManagerMapper.selectByPrimaryKey(widgetId);
		String json = widgetToJson.widget2json(launwidget).toString();
		System.out.println(json);
		
	}
	

	@Test
	public void test() throws Exception {
		long widgetId = 180510720459877L;
		LaunWidget launwidget = launWidgetManagerMapper.selectByPrimaryKey(widgetId);
		String[] x = launwidget.getDefaultSize().split("x");
		String property = launwidget.getProperty();
		List list = null;
		list = mapper.readValue(property, List.class);
		Iterator iterator = list.iterator();
		Map outmap = new HashMap();// 外层map
		outmap.put("x", 0);
		outmap.put("y", 0);
		outmap.put("width", x[0]);
		outmap.put("height", x[1]);
		outmap.put("name", launwidget.getName());
		outmap.put("id", launwidget.getId());
		Map jmap = new HashMap();// 内层map
		jmap.put("backgroud", launwidget.getPrewImage());
		Map map = new HashMap();
		List textlist = new ArrayList();
		List numberlist = new ArrayList();
		List singleset = new ArrayList();
		List multiset = new ArrayList();
		List imgset = new ArrayList();
		List color = new ArrayList();
		while (iterator.hasNext()) {// 循环单个list
			map = viewJsonTree1(iterator.next());
			for (Iterator ite = map.entrySet().iterator(); ite.hasNext();) {
				Map.Entry e = (Map.Entry) ite.next();
				if (e.getValue().equals("ImageSet")) {
					map.remove(e.getKey());
					imgset.add(map);
					jmap.put("Bacgrounds", imgset);
					break;
				} else if (e.getValue().equals("multiSelect")) {
					map.remove(e.getKey());
					multiset.add(map);
					jmap.put("checkboxs", multiset);
					break;
				} else if (e.getValue().equals("singleSelect")) {
					map.remove(e.getKey());
					singleset.add(map);
					jmap.put("radios", singleset);
					break;
				} else if (e.getValue().equals("TextSet")) {
					map.remove(e.getKey());
					textlist.add(map);
					jmap.put("text", textlist);
					break;
				} else if (e.getValue().equals("number")) {
					map.remove(e.getKey());
					numberlist.add(map);
					jmap.put("number", numberlist);
					break;
				} else if (e.getValue().equals("color")) {
					map.remove(e.getKey());
					color.add(map);
					jmap.put("color", color);
					break;
				}

			}
		}
		// System.out.println(map);

		// System.out.println(jmap);
		outmap.put("setting", jmap);
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(outmap));
		System.out.println(json.toString());
	}

	public Map viewJsonTree1(Object m) {
		Map map = new HashMap();
		try {
			Map mp = null;
			List ls = null;
			if (m instanceof Map || m instanceof LinkedHashMap) {
				mp = (LinkedHashMap) m;
				for (Iterator ite = mp.entrySet().iterator(); ite.hasNext();) {
					Map.Entry e = (Map.Entry) ite.next();

					map.put(e.getKey(), e.getValue());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	public List viewJsonTree2(Object m) {
		List list = new ArrayList();
		try {
			Map mp = null;
			List ls = null;
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						list.add(ls.get(i));
					}else if(ls.get(i) instanceof ArrayList){
						list.add(ls.get(i));
					}	
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public Map unJsonParsing() {
		String jsons = "D:/Temp/180503826397277/widget.json";
		File file = new File(jsons);
		StringBuffer sb = null;
		try {
			FileReader fileReader = new FileReader(file);
			Reader reader = new InputStreamReader(new FileInputStream(file), "GBK");
			int ch = 0;
			sb = new StringBuffer();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			fileReader.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = sb.toString();
		Map map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public JSONArray unJsonParsing1() {
		String jsons = "D:/Temp/180503826397277/widget.json";
		File file = new File(jsons);
		StringBuffer sb = null;
		try {
			FileReader fileReader = new FileReader(file);
			Reader reader = new InputStreamReader(new FileInputStream(file), "GBK");
			int ch = 0;
			sb = new StringBuffer();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			fileReader.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = sb.toString();
		JSONObject jsonObject = JSON.parseObject(json);
		JSONArray properties = jsonObject.getJSONArray("properties");

		return properties;
	}

	@Test
	public void test1() {
		// Map map = unJsonParsing();
		Map map = null;
		JSONArray properties = unJsonParsing1();
		for (Object object : properties) {
			map = (Map<String, Object>) object;
			viewJsonTree(map);
		}

		// viewJsonTree(map);
	}

	public Object viewJsonTree(Object m) {

		if (m == null) {
			System.out.println("null over");
			return false;
		}
		try {
			Map mp = null;
			List ls = null;
			if (m instanceof Map || m instanceof LinkedHashMap) {
				mp = (LinkedHashMap) m;
				for (Iterator ite = mp.entrySet().iterator(); ite.hasNext();) {
					Map.Entry e = (Map.Entry) ite.next();

					if (e.getValue() instanceof String) {

					} else if (e.getValue() instanceof LinkedHashMap) {

						viewJsonTree((LinkedHashMap) e.getValue());
					} else if (e.getValue() instanceof ArrayList) {

						viewJsonTree((ArrayList) e.getValue());
					}
				}

			}

			if (m instanceof List || m instanceof ArrayList) {
				ls = (ArrayList) m;
				for (int i = 0; i < ls.size(); i++) {
					if (ls.get(i) instanceof LinkedHashMap) {
						viewJsonTree((LinkedHashMap) ls.get(i));
					} else if (ls.get(i) instanceof ArrayList) {
						viewJsonTree((ArrayList) ls.get(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Long preAdd(String name, String valueData, Long parentId, LaunWidgetProperty launWidget, Integer dataType,
			Long dateId) {
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
		if (dateId != null) {
			launWidgetMinProperty.setCompleteType(dateId.toString());
		}
		launWidgetMinProperty.setWidgetPropertyId(launWidget.getId());
		launWidgetMinPropertyMapper.insertSelective(launWidgetMinProperty);
		return id;
	}

	/*
	 * Json数据解析返回数据类型枚举
	 */
	public enum TypeEnum {
		/** 单纯的键值对，通过key获取valus */
		string,
		/** 通过key获取到Map对象 */
		map,
		/** 通过key获取到ArrayList数组 */
		arrayList,
		/** 通过key获取到ArrayMap数组对象 */
		arrayMap;
	}
	
	@Test
	public void test13() {
		List<LaunWidgetFile> filelist = launWidgetFileMapper.findwidgetfile(180527554957316L);
		System.out.println(filelist.toString());
	}
	
	@Autowired
	private LaunChannelMapper launChannelMapper;
	
	@Test
	public void test14() {
		String name = "";
		Example example = new Example(LaunChannel.class);
		example.setOrderByClause("create_date DESC");
		Criteria createCriteria = example.createCriteria();
		if (name != null) {
			createCriteria.andLike("name", "%" + name + "%");
		}
		System.out.println(launChannelMapper.selectByExample(example));
	}
}
