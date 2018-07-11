package com.pactera.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.business.dao.LaunAttributeMapper;
import com.pactera.business.dao.LaunWidgetChannelMapper;
import com.pactera.business.dao.LaunWidgetFileMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetTypeMapper;
import com.pactera.config.security.UserUtlis;
import com.pactera.config.spring.SpringUtil;
import com.pactera.domain.LaunAttribute;
import com.pactera.domain.LaunUser;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetChannel;
import com.pactera.domain.LaunWidgetFile;
import com.pactera.domain.LaunWidgetType;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;

/**
 * @description:widget、json互转相关
 * @author:lizhipeng
 * @since:2018年5月25日10:59:12
 */
@SuppressWarnings("all")
public class widgetToJson {

	/**
	 * 解析自定义widget的json，转为实体
	 * 
	 * @param gjson
	 * @return
	 */
	public static LaunWidget json2Widget(String widgetjson) {
		LaunWidgetChannelMapper launWidgetChannelMapper = SpringUtil.getBean(LaunWidgetChannelMapper.class);
		LaunWidget widget = new LaunWidget();
		Map<?, ?> map = JsonUtils.JsonToMap(widgetjson);
		Long id = map.get("id").equals("") ? IdUtlis.Id() : Long.parseLong(map.get("id").toString());
		String name = map.get("newName").toString();
		String description = map.get("description") + "";
		String widthSize = map.get("widthSize").toString();
		String heightSize = map.get("heightSize").toString();
		Long category = Long.parseLong(map.get("widgetCategory").toString());
		Integer version = Integer.parseInt(map.get("version").toString());
		String channels = map.get("channels") == null ? "" : map.get("channels").toString();
		String prewimage = map.get("fengmian").toString();
		widget.setId(id);
		widget.setCategory(category);
		widget.setDescription(description);
		widget.setName(name);
		widget.setPrewImage(prewimage);
		widget.setDefaultSize(widthSize+"*"+heightSize);
		widget.setType(2);
		widget.setVersion(version);
		widget.setCodeId("ConstraintLayout");
		widget.setGproperty(widgetjson);
		widget.setParentId(0L);
		if ("".equals(channels)) {
			widget.setChannelway(1);
		} else {//关联渠道
			widget.setChannelway(0);
			LaunWidgetChannel widgetchannel = new LaunWidgetChannel();
			widgetchannel.setWidgetId(widget.getId());
			launWidgetChannelMapper.delete(widgetchannel);
			String[] channel = channels.split(",");
			for (String c : channel) {
				widgetchannel.setChannelId(Long.parseLong(c));
				launWidgetChannelMapper.insertSelective(widgetchannel);
			}
		}
		
		//关联封面
		LaunWidgetFileMapper launWidgetFileMapper = SpringUtil.getBean(LaunWidgetFileMapper.class);
		LaunWidgetManagerMapper launWidgetManagerMapper = SpringUtil.getBean(LaunWidgetManagerMapper.class);
		//先删除封面
		launWidgetManagerMapper.deletewidgetcover(widget.getId());
		LaunWidgetFile widgetfile = new LaunWidgetFile();
		widgetfile.setId(IdUtlis.Id());
		widgetfile.setWidgetId(widget.getId());
		widgetfile.setCreateDate(new Date());
		widgetfile.setType(1);
		widgetfile.setFileName(map.get("fengmian").toString());
		widgetfile.setPath(map.get("path").toString());
		//添加封面
		launWidgetFileMapper.insertSelective(widgetfile);
		// TODO
		//LaunUser user = UserUtlis.launUser();
		//widget.setCreator(user.getId());
		return widget;
	}

	
	/**
	 * 自定义widget的子集解析
	 * @param groupjson
	 * @return
	 */
	public static List<LaunWidget> json2gwidget(String groupjson,Long parentId) {
		LaunWidgetManagerMapper launWidgetManagerMapper = SpringUtil.getBean(LaunWidgetManagerMapper.class);
		LaunWidgetFileMapper launWidgetFileMapper = SpringUtil.getBean(LaunWidgetFileMapper.class);
		//删除自定义下widget的所有图片
		launWidgetManagerMapper.deletewidgetfile(parentId);
		List<LaunWidget> widgetlist = new ArrayList<LaunWidget>();
		ObjectMapper mapper = new ObjectMapper();
		LaunWidgetFile widgetfile = null;
		Long belongId = null;
		try {
			List<Map> jlist = mapper.readValue(groupjson, List.class);
			if (jlist != null) {
				for (Map map : jlist) {
					belongId = Long.parseLong(map.get("id").toString());
					LaunWidget widget = null;
					LaunWidget widgetvo = new LaunWidget();
					widget = launWidgetManagerMapper.selectByPrimaryKey(belongId);
					BeanUtils.copyProperties(widget, widgetvo);
					widgetvo.setLattice(map.get("x")+","+map.get("y"));
					widgetvo.setDefaultSize(map.get("width")+"*"+map.get("height"));
					widgetvo.setCreateDate(new Date());
					widgetvo.setId(IdUtlis.Id());
					widgetvo.setParentId(parentId);
					//指向原widget
					widgetvo.setBelongId(belongId);
					widgetvo.setCreateDate(new Date());
					widgetvo.setProperty("");
					
					if(map.get("type").equals("0") || map.get("type").equals("1")) {
						widgetvo.setType(3);
						String file = map.get("file").toString();
						//封面map
						Map<?, ?> filemap = JsonUtils.JsonToMap(file);
						for(Object key : filemap.keySet()) {
							widgetfile = new LaunWidgetFile();
							widgetfile.setId(IdUtlis.Id());
							widgetfile.setWidgetId(widgetvo.getId());
							widgetfile.setCreateDate(new Date());
							widgetfile.setType(3);
							widgetfile.setFileName(key.toString());
							widgetfile.setPath(filemap.get(key).toString());
							//添加封面
							launWidgetFileMapper.insertSelective(widgetfile);
						}
					}else {
						widgetvo.setType(2);
					}
					
					
					widgetlist.add(widgetvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return widgetlist;
	}

	/**
	 * widget、widget文件信息组装json
	 * 
	 * @param json
	 * @return
	 */
	public static String widget2json(LaunWidget launwidget) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LaunAttributeMapper launAttributeMapper = SpringUtil.getBean(LaunAttributeMapper.class);
		LaunWidgetTypeMapper launWidgetTypeMapper = SpringUtil.getBean(LaunWidgetTypeMapper.class);
		LaunWidgetFileMapper launWidgetFileMapper = SpringUtil.getBean(LaunWidgetFileMapper.class);
		List<LaunWidgetFile> filelist = launWidgetFileMapper.findwidgetfile(launwidget.getId());
		Map<String, String> filemap = new HashMap<String, String>();
		if (filelist != null) {
			for (LaunWidgetFile file : filelist) {
				filemap.put(file.getFileName(), file.getPath());
			}
		}
		// 外层map
		Map outmap = new HashMap();
		String[] x = launwidget.getDefaultSize().split("\\*");
		String property = launwidget.getProperty();
		outmap.put("x", 0);
		outmap.put("y", 0);
		if (x.length != 0) {
			outmap.put("width", x[0]);
			outmap.put("height", x[1]);
		}
		outmap.put("name", launwidget.getName());
		outmap.put("id", launwidget.getId());
		outmap.put("description", launwidget.getDescription());
		outmap.put("codeId", launwidget.getCodeId());
		// 查询widget版本号
		/*LaunAttribute attr = new LaunAttribute();
		attr.setAttributeKey("version");
		attr.setAttributeKeyIndex(launwidget.getVersion());
		attr = launAttributeMapper.selectOne(attr);*/
		outmap.put("version", launwidget.getVersion());
		// 查询widget分类
		LaunWidgetType wt = new LaunWidgetType();
		wt.setId(launwidget.getCategory());
		wt = launWidgetTypeMapper.selectOne(wt);
		outmap.put("category", wt.getTypeName());
		outmap.put("backgroud", launwidget.getPrewImage());
		// setting中的list
		List slist = new LinkedList();
		Map map = new HashMap();
		List list = mapper.readValue(property, List.class);
		Iterator iterator = list.iterator();
		// 循环单个list
		while (iterator.hasNext()) {
			map = viewJsonTree(iterator.next());
			slist.add(map);
		}
		if(launwidget.getType() == 0 || launwidget.getType() == 1) {
			outmap.put("setting", slist);
		}
		outmap.put("file", filemap);
		String json = JSON.toJSON(outmap).toString();
		return json;
	}

	/**
	 * widget转成上传压缩包中的json
	 * 
	 * @param json
	 * @return
	 */
	public static Object singlewidget2json(LaunWidget widget) {
		Object json = "";
		String codeId = widget.getCodeId();
		Integer version = widget.getVersion();
		String size = widget.getDefaultSize();
		String previewimg = widget.getPrewImage();
		String name = widget.getName();
		LaunWidgetTypeMapper launWidgetTypeMapper = SpringUtil.getBean(LaunWidgetTypeMapper.class);
		LaunWidgetType widgettype = new LaunWidgetType();
		widgettype.setId(widget.getCategory());
		widgettype = launWidgetTypeMapper.selectOne(widgettype);
		String category = widgettype.getTypeName();
		String tag = widget.getTag();
		String description = widget.getDescription();
		String properties = widget.getProperty();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeId", codeId);
		map.put("widgetCodeVersion", version);
		map.put("defaultSize", size);
		map.put("previewImage", previewimg);
		map.put("name", name);
		map.put("category", category);
		map.put("tags", tag);
		map.put("description", description);
		map.put("properties", properties);
		json = JSON.toJSON(map).toString();
		return json;
	}

	/**
	 * json转为widget实体
	 * 
	 * @param json
	 * @throws Exception
	 */
	public static List<LaunWidget> json2widget(String json) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(json, List.class);
		List<LaunWidget> widgetlist = null;
		LaunWidget widget = null;
		String obj = null;
		JSONObject jsonObject = null;
		if (list != null) {
			for (Map map : list) {
				widgetlist = new ArrayList<LaunWidget>();
				obj = JSON.toJSON(map).toString();
				jsonObject = JSON.parseObject(obj);
				widget = new LaunWidget();
				widget.setId(jsonObject.getLong("id"));
				widget.setCategory(jsonObject.getLong("category"));
				widget.setDescription(jsonObject.getString("description"));
				widget.setPrewImage(jsonObject.getString("backgroud"));
				widget.setName(jsonObject.getString("name"));
				widget.setDefaultSize(jsonObject.getString("width") + "*" + jsonObject.getString("height"));
				widget.setProperty(jsonObject.getString("setting"));
				widget.setVersion(jsonObject.getInteger("version"));
				//widget.setCreateDate(new Date());
				// TODO 人员未对接，先统一为1
				//LaunUser user = UserUtlis.launUser();
				//widget.setCreator(user.getId());
				//widget.setCreateway(user.getChannelId()==null?1:2);
				widgetlist.add(widget);
			}
		}
		return widgetlist;
	}

	/**
	 * 基础widget回显组装json
	 * 
	 * @param launwidgets
	 * @return
	 * @throws Exception
	 */
	public static String widget2Jsonnew(List<LaunWidget> launwidgets) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LaunAttributeMapper launAttributeMapper = SpringUtil.getBean(LaunAttributeMapper.class);
		LaunWidgetTypeMapper launWidgetTypeMapper = SpringUtil.getBean(LaunWidgetTypeMapper.class);
		List outlist = new ArrayList();
		// 外层map
		Map outmap = null;
		if (launwidgets.size() != 0) {
			for (LaunWidget launwidget : launwidgets) {
				outmap = new HashMap();
				String[] x = launwidget.getDefaultSize().split("\\*");
				String property = launwidget.getProperty();
				List list = null;
				list = mapper.readValue(property, List.class);
				Iterator iterator = list.iterator();
				outmap.put("x", 0);
				outmap.put("y", 0);
				if (x.length != 0) {
					outmap.put("width", x[0]);
					outmap.put("height", x[1]);
				}
				outmap.put("name", launwidget.getName());
				outmap.put("id", launwidget.getId());
				outmap.put("description", launwidget.getDescription());
				// 查询widget版本号
				/*LaunAttribute attr = new LaunAttribute();
				attr.setAttributeKey("version");
				attr.setAttributeKeyIndex(launwidget.getVersion());
				attr = launAttributeMapper.selectOne(attr);*/
				outmap.put("version", launwidget.getVersion());
				// 查询widget分类
				LaunWidgetType wt = new LaunWidgetType();
				wt.setId(launwidget.getCategory());
				wt = launWidgetTypeMapper.selectOne(wt);
				outmap.put("category", wt.getTypeName());
				outmap.put("backgroud", launwidget.getPrewImage());
				// setting中的list
				List slist = new LinkedList();
				Map map = new HashMap();
				// 循环单个list
				while (iterator.hasNext()) {
					map = viewJsonTree(iterator.next());
					slist.add(map);
				}
				outmap.put("setting", slist);
				outlist.add(outmap);
			}
		}
		String json = JSON.toJSON(outlist).toString();
		return json;
	}

	public static String widget2Json(List<LaunWidget> launwidgets) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LaunAttributeMapper launAttributeMapper = SpringUtil.getBean(LaunAttributeMapper.class);
		LaunWidgetTypeMapper launWidgetTypeMapper = SpringUtil.getBean(LaunWidgetTypeMapper.class);
		List outlist = new ArrayList();
		if (launwidgets.size() != 0) {
			for (LaunWidget launwidget : launwidgets) {
				String[] x = launwidget.getDefaultSize().split("\\*");
				String property = launwidget.getProperty();
				List list = null;
				list = mapper.readValue(property, List.class);
				Iterator iterator = list.iterator();
				// 外层map
				Map outmap = new HashMap();
				outmap.put("x", 0);
				outmap.put("y", 0);
				outmap.put("width", x[0]);
				outmap.put("height", x[1]);
				outmap.put("name", launwidget.getName());
				outmap.put("id", launwidget.getId());
				// 查询widget版本号
			/*	LaunAttribute attr = new LaunAttribute();
				attr.setAttributeKey("version");
				attr.setAttributeKeyIndex(launwidget.getVersion());
				attr = launAttributeMapper.selectOne(attr);*/
				outmap.put("version", launwidget.getVersion());
				// 查询widget分类
				LaunWidgetType wt = new LaunWidgetType();
				wt.setId(launwidget.getCategory());
				wt = launWidgetTypeMapper.selectOne(wt);
				outmap.put("category", wt.getTypeName());
				outmap.put("backgroud", launwidget.getPrewImage());
				// 内层map
				Map jmap = new HashMap();
				Map map = new HashMap();
				List textlist = new ArrayList();
				List numberlist = new ArrayList();
				List singleset = new ArrayList();
				List multiset = new ArrayList();
				List imgset = new ArrayList();
				List color = new ArrayList();
				// 循环单个list
				while (iterator.hasNext()) {
					map = viewJsonTree(iterator.next());
					for (Iterator ite = map.entrySet().iterator(); ite.hasNext();) {
						Map.Entry e = (Map.Entry) ite.next();
						if ("ImageSet".equals(e.getValue())) {
							map.remove(e.getKey());
							imgset.add(map);
							jmap.put(e.getValue(), imgset);
							break;
						} else if ("multiSelect".equals(e.getValue())) {
							map.remove(e.getKey());
							multiset.add(map);
							jmap.put(e.getValue(), multiset);
							break;
						} else if ("singleSelect".equals(e.getValue())) {
							map.remove(e.getKey());
							singleset.add(map);
							jmap.put(e.getValue(), singleset);
							break;
						} else if ("TextSet".equals(e.getValue())) {
							map.remove(e.getKey());
							textlist.add(map);
							jmap.put(e.getValue(), textlist);
							break;
						} else if ("number".equals(e.getValue())) {
							map.remove(e.getKey());
							numberlist.add(map);
							jmap.put(e.getValue(), numberlist);
							break;
						} else if ("color".equals(e.getValue())) {
							map.remove(e.getKey());
							color.add(map);
							jmap.put(e.getValue(), color);
							break;
						}

					}
				}
				outmap.put("setting", jmap);
				outlist.add(outmap);

			}
		}
		String json = JSON.toJSON(outlist).toString();
		return json;
	}

	public static Map viewJsonTree(Object m) {
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
}
