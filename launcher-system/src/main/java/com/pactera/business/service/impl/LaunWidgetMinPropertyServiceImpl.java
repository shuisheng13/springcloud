package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.service.LaunWidgetMinPropertyService;
import com.pactera.domain.LaunWidgetMinProperty;

/**
 * @description:widget属性相关
 * @author:lizhipeng
 * @since:2018年5月25日10:35:18
 */
@Service
public class LaunWidgetMinPropertyServiceImpl implements LaunWidgetMinPropertyService {

	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;

	@Override
	public List<Map<String, Object>> selectByWidgetId(Long widgetId) {
		// 查询持久化数据
		List<LaunWidgetMinProperty> list = launWidgetMinPropertyMapper.selectByWidgetId(widgetId);

		List<Map<String, Object>> packageData = packageData(list);

		return packageData;
	}

	/**
	 * 打包widget属性
	 * 
	 * @author LL
	 * @date 2018年5月2日 下午5:31:13
	 * @param list从属于一个widget的所有属性
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> packageData(List<LaunWidgetMinProperty> list) {
		// 定义返回List
		List<Map<String, Object>> returnList = new LinkedList<>();
		// 通过widgetId进行归类
		Map<Long, List<LaunWidgetMinProperty>> map = new HashMap<>(16);

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			Long widgetPropertyId = launWidgetMinProperty.getWidgetPropertyId();
			List<LaunWidgetMinProperty> groupList = map.get(widgetPropertyId);
			if (groupList != null) {
				groupList.add(launWidgetMinProperty);
			} else {
				List<LaunWidgetMinProperty> newList = new ArrayList<>();
				newList.add(launWidgetMinProperty);
				map.put(widgetPropertyId, newList);
			}
		}

		for (Map.Entry<Long, List<LaunWidgetMinProperty>> entry : map.entrySet()) {
			List<LaunWidgetMinProperty> minPropertyList = entry.getValue();
			Map<String, Object> detailMap = detailMap(minPropertyList);
			returnList.add(detailMap);
		}

		return returnList;
	}

	/**
	 * 处理同一属性list
	 * 
	 * @author LL
	 * @date 2018年5月2日 下午5:32:05
	 * @param
	 * @return Map<String,Object>
	 */
	public Map<String, Object> detailMap(List<LaunWidgetMinProperty> list) {

		Map<String, Object> map = new HashMap<>(16);

		// 基础属性list(parentId为0)
		List<LaunWidgetMinProperty> list1 = new ArrayList<>();
		// 含有子属性
		List<LaunWidgetMinProperty> list2 = new ArrayList<>();

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			if ("0".equals(launWidgetMinProperty.getParantId().toString())) {
				list1.add(launWidgetMinProperty);
			} else {
				list2.add(launWidgetMinProperty);
			}
		}

		for (LaunWidgetMinProperty launWidgetMinProperty : list1) {
			// 判断基础属性的value是否为空
			Long id = launWidgetMinProperty.getId();

			if (!"0".equals(launWidgetMinProperty.getDataType().toString())) {
				// 该子集为对象
				if ("1".equals(launWidgetMinProperty.getDataType().toString())) {
					Map<String, Object> object = getObject(id, list);
					map.put(launWidgetMinProperty.getName(), object);
				// 该子集为数组
				} else if ("2".equals(launWidgetMinProperty.getDataType().toString())) {
					String[] strings = getArrays(id, list2);
					map.put(launWidgetMinProperty.getName(), strings);
				// 该子集为集合	
				} else if ("3".equals(launWidgetMinProperty.getDataType().toString())) {
					List<Map<String, String>> object = geObjectArrays(id, list2);
					map.put(launWidgetMinProperty.getName(), object);
				}
				// map.put(launWidgetMinProperty.getName(), childMap);
			} else {
				// 直接放入key-vlaue属性
				map.put(launWidgetMinProperty.getName(), launWidgetMinProperty.getValueData());
			}
		}
		return map;
	}

	/**
	 * 转数组
	 * 
	 * @author LL
	 * @date 2018年5月1日 下午8:45:18
	 * @param childId
	 *            父属性id
	 * @param list
	 *            widget属性列表
	 * @return String[]
	 */
	public Map<String, Object> getObject(Long childId, List<LaunWidgetMinProperty> list) {

		Map<String, Object> map = new HashMap<>(16);

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			if (childId.equals(launWidgetMinProperty.getParantId())) {
				// 判断基础属性的value是否为空
				Long id = launWidgetMinProperty.getId();
				if (!"0".equals(launWidgetMinProperty.getDataType().toString())) {

					// 该子集为对象
					if ("1".equals(launWidgetMinProperty.getDataType().toString())) {
						Map<String, Object> childMap = getObject(id, list);
						map.put(launWidgetMinProperty.getName(), childMap);
					// 该子集为数组	
					} else if ("2".equals(launWidgetMinProperty.getDataType().toString())) {
						String[] strings = getArrays(id, list);
						map.put(launWidgetMinProperty.getName(), strings);
					// 该子集为集合
					} else if ("3".equals(launWidgetMinProperty.getDataType().toString())) {
						List<Map<String, String>> object = geObjectArrays(id, list);
						map.put(launWidgetMinProperty.getName(), object);
					}
					// map.put(launWidgetMinProperty.getName(), childMap);
				} else {
					// 直接放入key-vlaue属性
					map.put(launWidgetMinProperty.getName(), launWidgetMinProperty.getValueData());
				}
			}
		}
		return map;
	}

	/**
	 * 转对象数组
	 * 
	 * @author LL
	 * @date 2018年5月1日 下午8:45:18
	 * @param childId
	 *            父属性id
	 * @param list
	 *            widget属性列表
	 * @return String[]
	 */
	public List<Map<String, String>> geObjectArrays(Long childId, List<LaunWidgetMinProperty> list) {

		// 定义返回对象
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

		List<LaunWidgetMinProperty> valueList = new ArrayList<LaunWidgetMinProperty>();

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			if (childId.equals(launWidgetMinProperty.getParantId())) {
				valueList.add(launWidgetMinProperty);
			}
		}

		// 通过completeType进行归类
		Map<String, List<LaunWidgetMinProperty>> map = new HashMap<>(16);

		for (LaunWidgetMinProperty launWidgetMinProperty : valueList) {
			String completeType = launWidgetMinProperty.getCompleteType();
			List<LaunWidgetMinProperty> groupList = map.get(completeType);
			if (groupList != null) {
				groupList.add(launWidgetMinProperty);
			} else {
				List<LaunWidgetMinProperty> newList = new ArrayList<>();
				newList.add(launWidgetMinProperty);
				map.put(completeType, newList);
			}
		}

		for (Map.Entry<String, List<LaunWidgetMinProperty>> entry : map.entrySet()) {
			Map<String, String> valueMap = new HashMap<String, String>(16);
			List<LaunWidgetMinProperty> minPropertyList = entry.getValue();
			for (LaunWidgetMinProperty launWidgetMinProperty : minPropertyList) {
				valueMap.put(launWidgetMinProperty.getName(), launWidgetMinProperty.getValueData());
			}
			returnList.add(valueMap);
		}
		return returnList;
	}

	/**
	 * 转数组
	 * 
	 * @author LL
	 * @date 2018年5月1日 下午8:45:18
	 * @param childId
	 *            父属性id
	 * @param list
	 *            widget属性列表
	 * @return String[]
	 */
	public String[] getArrays(Long childId, List<LaunWidgetMinProperty> list) {

		List<String> valueList = new ArrayList<String>();

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			if (childId.equals(launWidgetMinProperty.getParantId())) {
				valueList.add(launWidgetMinProperty.getValueData());
			}
		}
		String[] strings = new String[valueList.size()];
		return valueList.toArray(strings);
	}

}
