package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.pactera.base.Tester;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.service.LaunRedisService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunWidgetMinProperty;
import com.pactera.utlis.JsonUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * @description:应用相关测试
 * @author:Scott
 * @since:2018年4月26日 下午4:10:37
 */
public class LaunWidgetMinProptyT extends Tester {

	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;

	@Autowired
	private LaunRedisService launRedisService;

	@Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@SuppressWarnings("unchecked")

	public void testRedis() {
		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		Example example = new Example(LaunThemeAdministration.class);
		example.createCriteria().andEqualTo("status", 2);
		List<LaunThemeAdministration> byExample = launThemeMapper.selectByExample(example);

		// 按照渠道进行分类
		Map<String, List<LaunThemeAdministration>> map = new HashMap<String, List<LaunThemeAdministration>>();

		for (LaunThemeAdministration launThemeAdministration : byExample) {
			String channleId = launThemeAdministration.getCreatorChannelId().toString();
			if (map.get(channleId) != null) {
				map.get(channleId).add(launThemeAdministration);
			} else {
				List<LaunThemeAdministration> value = new ArrayList<LaunThemeAdministration>();
				value.add(launThemeAdministration);
				map.put(channleId, value);
			}
		}

		List<LaunThemeAdministration> list = map.get("0");
		if (map.size() > 0) {
			for (Entry<String, List<LaunThemeAdministration>> launThemeAdministration : map.entrySet()) {
				String key = launThemeAdministration.getKey();
				List<LaunThemeAdministration> value = launThemeAdministration.getValue();
				if (!key.equals("0")) {
					if (list != null) {
						value.addAll(list);
					}
				}
			}
		}

		opsForHash.putAll("themeList", map);

		test2();
	}

	public void test1() {

		LaunThemeAdministration theme = new LaunThemeAdministration();
		redisTemplate.delete("180602870123221");
		redisTemplate.delete("180605305052736");
	}

	@Test
	public void test2() {
		String key = "测试A";
		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		List<LaunThemeAdministration> list = (List<LaunThemeAdministration>) opsForHash
				.get(ConstantUtlis.THEME_REDIS_FLAG, key);
		System.out.println(list.size());
	}

	public void test() {
		String selectByWidgetId = selectByWidgetId(180501932709209L);
		System.out.println(selectByWidgetId);
	}

	public String selectByWidgetId(Long widgetId) {
		// 定义返回List
		List<Map<String, Object>> returnList = new LinkedList<>();
		// 查询持久化数据
		List<LaunWidgetMinProperty> list = launWidgetMinPropertyMapper.selectByWidgetId(widgetId);

		// 通过widgetId进行归类
		Map<Long, List<LaunWidgetMinProperty>> map = new HashMap<>();

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

		return JsonUtils.ObjectToJson(returnList);
	}

	public Map<String, Object> detailMap(List<LaunWidgetMinProperty> list) {

		Map<String, Object> map = new HashMap<>();

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
				} else if ("2".equals(launWidgetMinProperty.getDataType().toString())) {// 该子集为数组
					String[] strings = getArrays(id, list2);
					map.put(launWidgetMinProperty.getName(), strings);
				} else if ("3".equals(launWidgetMinProperty.getDataType().toString())) {// 该子集为集合
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
	 * @param childId父属性id
	 * @param childId父属性id
	 * @return String[]
	 */
	public Map<String, Object> getObject(Long childId, List<LaunWidgetMinProperty> list) {

		Map<String, Object> map = new HashMap<>();

		for (LaunWidgetMinProperty launWidgetMinProperty : list) {
			if (childId.equals(launWidgetMinProperty.getParantId())) {
				// 判断基础属性的value是否为空
				Long id = launWidgetMinProperty.getId();
				if (!"0".equals(launWidgetMinProperty.getDataType().toString())) {

					// 该子集为对象
					if ("1".equals(launWidgetMinProperty.getDataType().toString())) {
						Map<String, Object> childMap = getObject(id, list);
						map.put(launWidgetMinProperty.getName(), childMap);
					} else if ("2".equals(launWidgetMinProperty.getDataType().toString())) {// 该子集为数组
						String[] strings = getArrays(id, list);
						map.put(launWidgetMinProperty.getName(), strings);
					} else if ("3".equals(launWidgetMinProperty.getDataType().toString())) {// 该子集为集合
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
	 * @param
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
		Map<String, List<LaunWidgetMinProperty>> map = new HashMap<>();

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
			Map<String, String> valueMap = new HashMap<String, String>();
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
	 * @param
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
