package com.pactera.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.dao.LaunApplicationPosterMapper;
import com.pactera.business.dao.LaunChannelMapper;
import com.pactera.business.dao.LaunClientThemeMapper;
import com.pactera.business.dao.LaunThemeConfigMapper;
import com.pactera.business.dao.LaunThemeFileMapper;
import com.pactera.business.service.LaunClientThemeService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.domain.LaunChannel;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeConfig;
import com.pactera.domain.LaunThemeFile;
import com.pactera.utlis.FileTool;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.utlis.TimeUtils;
import com.pactera.vo.LaunClientShopThemeVo;
import com.pactera.vo.LaunClientThemeVo;
import com.pactera.vo.LaunThemePosterVo;
import com.pactera.vo.LaunThemeShopVo;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @description: 主题相关的实现类
 * @author:woqu
 * @since:2018年4月26日 上午11:27:03
 */
@Service
@Transactional
@Slf4j
public class LaunClientThemeServiceImpl implements LaunClientThemeService {

	@Value("${file.path.yu}")
	private String filePath;

	@Autowired
	private LaunClientThemeMapper launClientThemeMapper;

	@Autowired
	private LaunChannelMapper launChannelMapper;

	@Autowired
	private LaunThemeConfigMapper launThemeConfigMapper;

	@Autowired
	private LaunThemeFileMapper launThemeFileMapper;

	@Autowired
	private LaunApplicationPosterMapper launApplicationPostersMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	/**
	 * 根据输入的条件获取key
	 * 
	 * @author LL
	 * @date 2018年11月1日 下午6:10:23
	 * @return String
	 */
	protected String getKeyBySel(String channle, String version, Long screenHeight, Long screenWidth, String userId,
			String city, Integer type) {

		StringBuffer key = new StringBuffer();

		key.append(channle).append("_").append(version).append("_").append(screenHeight).append("_").append(screenWidth)
				.append("_").append(type);
		return key.toString();
	}

	/**
	 * 判断当前key是否需要重新处理
	 * 
	 * @author LL
	 * @param redisRefresh2
	 * @date 2018年11月1日 下午7:02:28
	 * @param keyBySel失败
	 * @return String 为null的时候,需要重新获取;
	 */
	@SuppressWarnings("unchecked")
	protected String isRefresh(String keyBySel, long redisRefresh) {
		Object redisValue = valueOperations.get(keyBySel);
		if (redisValue != null) {
			// List<LaunThemeShopVo> jsonToList =
			// ;JsonUtils.jsonToList(redisValue, LaunThemeShopVo.class);
			Map<String, Object> jsonToMap = (Map<String, Object>) JsonUtils
					.JsonToMap(JsonUtils.ObjectToJson(redisValue));
			long mapRedis = Long.parseLong(jsonToMap.get(ConstantUtlis.THEME_REDIS_REFRESH).toString());
			if (redisRefresh == mapRedis) {
				return (String) jsonToMap.get("value");
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("1".compareTo("1"));
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunThemeShopVo> getThemeList(String channle, String version, Long screenHeight, Long screenWidth,
			String userId, String city, Integer type) {

		// 定义返回对象
		List<LaunThemeShopVo> returnList = new ArrayList<LaunThemeShopVo>();

		List<LaunThemeAdministration> themeList = new ArrayList<>();

		Date now = new Date();
		boolean flag = true;
		String keyBySel = getKeyBySel(channle, version, screenHeight, screenWidth, userId, city, type);

		Long redisRefresh = null;
		try {
			redisRefresh = (long) valueOperations.get(ConstantUtlis.THEME_REDIS_REFRESH);
		} catch (Exception e) {
			valueOperations.set(ConstantUtlis.THEME_REDIS_REFRESH, 1l);
			redisRefresh = 1l;
		}
		if (type != null && type == 2) {
			flag = false;
			try {
				/**
				 * 获取上次缓存的数据，没的话进行添加操作
				 */

				String refresh = isRefresh(keyBySel, redisRefresh);
				if (HStringUtlis.isNotBlank(refresh)) {
					return JsonUtils.jsonToList(refresh, LaunThemeShopVo.class);
				}
				// 从redis中根据渠道查询
				List<LaunThemeAdministration> list = (List<LaunThemeAdministration>) redisTemplate.opsForHash()
						.get(ConstantUtlis.THEME_REDIS_FLAG, channle);
				for (LaunThemeAdministration theme : list) {
					// 匹配分辨率
					if (screenWidth.equals(theme.getWideResolution())
							&& screenHeight.equals(theme.getLongResolution())) {
						// 匹配版本和过期时间
						if (version.compareTo(theme.getVersion()) >= 0
								&& TimeUtils.compareDate(theme.getEndTime(), now) >= 0) {
							//
							themeList.add(theme);
						}
					}

				}
			} catch (Exception e) {
				flag = true;
				e.printStackTrace();
				log.error("获取主题商店信息异常------------{}", e);
			}
		}

		if (flag) {
			// 通过条件查询所有能看的主题
			LaunChannel findByName = findByName(channle);
			if (findByName == null) {
				return returnList;
			}
			if (type != null) {
				if (type == 1) {
					type = null;
				} else if (type == 3) {
					type = 1;
				}
			}
			themeList = launClientThemeMapper.getThemeList(findByName.getId(), version, screenHeight, screenWidth, type,
					now);
		}

		if (themeList.size() == 0) {
			return null;
		}
		// 按照类型进行分类
		Map<Long, List<LaunThemeAdministration>> map = new HashMap<Long, List<LaunThemeAdministration>>();

		for (LaunThemeAdministration launThemeAdministration : themeList) {
			Long typeId = launThemeAdministration.getTypeId();
			if (map.get(typeId) != null) {
				map.get(typeId).add(launThemeAdministration);
			} else {
				List<LaunThemeAdministration> value = new ArrayList<LaunThemeAdministration>();
				value.add(launThemeAdministration);
				map.put(typeId, value);
			}
		}

		List<LaunClientShopThemeVo> returnObj = null;
		LaunThemeShopVo launThemeShop = null;

		// 封装每种类型的主题json
		for (Entry<Long, List<LaunThemeAdministration>> theme : map.entrySet()) {
			launThemeShop = new LaunThemeShopVo();
			List<LaunThemeAdministration> value = theme.getValue();

			returnObj = new ArrayList<LaunClientShopThemeVo>();
			launThemeShop.setCategoryID(theme.getKey());
			launThemeShop.setCategoryName(value.get(0).getTypeName());
			launThemeShop.setCategoryNum(value.size());

			LaunClientShopThemeVo clientShopTheme = null;
			String[] arrays = new String[0];
			for (LaunThemeAdministration laun : value) {
				clientShopTheme = new LaunClientShopThemeVo();
				clientShopTheme.setId(laun.getId());
				clientShopTheme.setName(laun.getTitle());
				clientShopTheme.setFileSize(laun.getFileSize());
				clientShopTheme.setUrl(filePath + laun.getZipUrl());
				clientShopTheme.setPreviewPath(laun.getPreviewPath());
				if (laun.getUrls() != null) {
					clientShopTheme.setImages(laun.getUrls().split(","));
				} else {
					clientShopTheme.setImages(arrays);
				}
				returnObj.add(clientShopTheme);
			}
			launThemeShop.setThemes(returnObj);
			returnList.add(launThemeShop);
		}

		Map<String, Object> redisMap = new HashMap<>();
		redisMap.put(ConstantUtlis.THEME_REDIS_REFRESH, redisRefresh);
		redisMap.put("value", JsonUtils.ObjectToJson(returnList));

		valueOperations.set(keyBySel, redisMap);
		return returnList;
	}

	@Override
	public Map<String, Object> getPosters(String channelName, String version, Long screenHeight, Long screenWidth,
			String userId, String city, String posterIds) {

		Map<String, Object> map = null;

		// 通过条件查询所有能看的主题
		LaunChannel findByName = findByName(channelName);
		if (findByName != null) {
			LaunThemeAdministration pushTheme = launClientThemeMapper.pushTheme(findByName.getId(), version,
					screenHeight, screenWidth);
			if (pushTheme != null) {
				map = new HashMap<String, Object>();
				List<LaunThemePosterVo> posterMap = getPosterMap(pushTheme.getId());
				LaunClientThemeVo launClientTheme = new LaunClientThemeVo();
				launClientTheme.setId(pushTheme.getId());
				launClientTheme.setName(pushTheme.getTitle());
				launClientTheme.setStartTime(TimeUtils.date2Millis(pushTheme.getStartTime()));
				launClientTheme.setFileSize(pushTheme.getFileSize());
				launClientTheme.setUrl(filePath + pushTheme.getZipUrl());
				launClientTheme.setVersion(pushTheme.getVersionName());

				map.put("theme", launClientTheme);
				map.put("posters", posterMap);
			}
		}

		return map;
	}

	/**
	 * 封装海报数据
	 * 
	 * @author LL
	 * @date 2018年6月3日 上午11:51:55
	 * @param themeId主题主键id
	 * @return List<Map<String,String>>
	 */
	public List<LaunThemePosterVo> getPosterMap(Long themeId) {
		List<LaunThemeConfig> themeConfigList = findByThemeId(themeId);
		int posterType = 2;
		String posterIdList = "";
		for (LaunThemeConfig launThemeConfig : themeConfigList) {
			if (launThemeConfig.getType() == posterType) {
				posterIdList += launThemeConfig.getWidgetId() + ",";
			}
		}
		List<LaunApplicationPoster> posterList = selectByIds(posterIdList);

		// 封装posters
		List<LaunThemePosterVo> postersValue = new ArrayList<LaunThemePosterVo>();

		for (LaunApplicationPoster poster : posterList) {
			LaunThemePosterVo posterVo = new LaunThemePosterVo();
			posterVo.setId(poster.getId());
			posterVo.setPath(FileTool.getFileName(poster.getPosterPath()));
			posterVo.setStartTime(TimeUtils.date2Millis(poster.getStartTime()));
			posterVo.setEndTime(TimeUtils.date2Millis(poster.getEndTime()));
			postersValue.add(posterVo);
		}
		return postersValue;
	}

	/**
	 * 根据渠道名称查询渠道
	 * 
	 * @author LL
	 * @date 2018年6月3日 上午11:40:33
	 * @param channleName渠道名称
	 * @return LaunChannel
	 */
	public LaunChannel findByName(String channleName) {
		Example example = new Example(LaunChannel.class);
		example.createCriteria().andEqualTo("channelId", channleName).andEqualTo("channelStatus", 0);
		List<LaunChannel> list = launChannelMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 根据主题主键id查询配置
	 * 
	 * @author LL
	 * @date 2018年6月3日 上午11:51:17
	 * @param 主题主键id
	 * @return List<LaunThemeConfig>
	 */
	private List<LaunThemeConfig> findByThemeId(Long themeId) {
		Example example = new Example(LaunThemeConfig.class);
		example.createCriteria().andNotEqualTo("launThemeId", themeId);
		return launThemeConfigMapper.selectByExample(example);
	}

	/**
	 * 根据主题查询预览图
	 * 
	 * @author LL
	 * @date 2018年6月3日 上午11:50:51
	 * @param themeId主题主键id
	 * @return List<LaunThemeFile>
	 */
	public List<LaunThemeFile> selectByThemeId(Long themeId) {
		Example example = new Example(LaunThemeFile.class);

		example.createCriteria().andEqualTo("themeId", themeId);
		return launThemeFileMapper.selectByExample(example);
	}

	/**
	 * 根据主键查询海报
	 * 
	 * @author LL
	 * @date 2018年6月3日 上午11:50:07
	 * @param 主键ids
	 * @return List<LaunApplicationPoster>
	 */
	public List<LaunApplicationPoster> selectByIds(String ids) {
		List<LaunApplicationPoster> selectByIds = new ArrayList<>();
		List<Long> list = new ArrayList<>();
		if (HStringUtlis.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for (String spl : split) {
				list.add(Long.parseLong(spl));
			}
		}
		if (list.size() > 0) {
			selectByIds = launApplicationPostersMapper.selectByIds(list);
		}
		return selectByIds;
	}

	@Override
	public Object test() {
		String channle = "ASDFGG";
		// 从redis中根据渠道查询
		Object object = redisTemplate.opsForHash().get(ConstantUtlis.THEME_REDIS_FLAG, channle);
		return object;

	}

}
