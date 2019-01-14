package com.pactera.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.*;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.IORuntimeException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.*;
import com.pactera.util.ThemeWidgetDetail;
import com.pactera.utlis.*;
import com.pactera.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @description: 主题相关的实现类
 * @author:woqu
 * @since:2018年4月26日 上午11:27:03
 */
@Service
@Slf4j
public class LaunThemeServiceImpl implements LaunThemeService {

	@Value("${system.conf.themeTemp}")
	private String themeConfigUrl;

	@Value("${file.path}")
	private String fastDfsPath;

	@Value("${file.path.yu}")
	private String filePath;

	@Value("${system.conf.themeTemp.v2}")
    private String tempPath;

    @Value("${upload.zip.prop}")
    private String upThemeProp;

    @Value("${upload.theme.long}")
    private String upThemeLong;

    @Value("${upload.theme.width}")
    private String upThemeWidth;

    @Value("${upload.theme.img.path}")
    private String upThemeImgPath;

    @Value("${upload.theme.img}")
    private String upThemeImgMain;

    @Value("${upload.theme.preix}")
    private String upThemePreix;

    @Autowired
    public FastFileStorageClient fastFileStorageClient;


    @Autowired
	private LaunThemeMapper launThemeMapper;

	@Autowired
	private LaunThemeFileService launThemeFileService;

	@Autowired
	private LaunFileCrudService launFileCrudService;

	@Autowired
	private LaunThemeConfigService configService;

	@Autowired
	private LaunApplicationPostersService applicationPostersService;

	@Autowired
	private LaunRedisService launRedisService;

	@Autowired
	private LaunFontService launFontService;

	@Override
	public LaunPage<LaunThemeVo> query(Long tenantId, String typeId, String title, Integer status, int pageNum, int pageSize) {

        PageInfo<LaunThemeAdministration> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> launThemeMapper.selectAll());
        BeanCopier beanCopier = BeanCopier.create(LaunThemeAdministration.class, LaunThemeVo.class,true);
        List<LaunThemeVo> value = pageInfo.getList().stream().map(theme->{
            LaunThemeVo themeVo = new LaunThemeVo();
            beanCopier.copy(theme, themeVo, (Object v, Class t, Object c)->v);
            return themeVo;
        }).collect(Collectors.toList());

        return new LaunPage<LaunThemeVo>()
                .setPageNum(pageNum)
                .setPageSize(pageSize)
                .setPages(pageInfo.getPages())
                .setSize(pageInfo.getSize())
                .setTotal(pageInfo.getTotal())
                .setList(value);
	}


    @Override
    public int changeStatus(String id, Integer status) {
		return launThemeMapper.updateByPrimaryKeySelective(
				new LaunThemeAdministration().setStatus(status).setId(id));
    }

    /**
     * @description 根据条件去插叙年主题的实现类
     * @author liudawei
     * @since 2018年4月26日 上午11:31:16
     * @param
     */
    @Override
    public PageInfo<LaunThemeVo> selectByCoundy(Long type, String version, Long channle, String title, Integer status,
                                                int pageNum, int pageSize) {

		// 更新过期状态
		updateOverdueTheme();

		PageHelper.startPage(pageNum, pageSize);
		if (HStringUtlis.isNotEmpty(title)) {
			title = "%" + title + "%";
		}

		// 判断是否为渠道管理员 0否，1是
		Integer isChannleManager = 1;
		//LaunUser launUser = UserUtlis.launUser();
		//Integer integer = launUser.getUserType();
		//if (integer == null || integer == 1) {
		//	Long channleId = launUser.getChannelId();
		//	if (channleId != null && (status == null || status == 1 || status == 2 || status == 3)) {
		//		channle = channleId;
		//		isChannleManager = 1;
		//	}
		//}
		List<LaunThemeVo> launList = launThemeMapper.selectByCound(type, version, channle, title, status,
				isChannleManager);
		return new PageInfo<LaunThemeVo>(launList);
	}

	/**
	 * 批量更新过期主题状态
	 *
	 * @author LL
	 * @date 2018年7月6日 上午10:30:23
	 * @param
	 * @return void
	 */
	void updateOverdueTheme() {

		// 查询上架状态的主题
		Example example = new Example(LaunThemeAdministration.class);
		example.or().andEqualTo("status", 2);
		List<LaunThemeAdministration> list = launThemeMapper.selectByExample(example);

		List<String> ids = new LinkedList<>();
		for (LaunThemeAdministration theme : list) {
			Date endTime = theme.getEndTime();
			Date now = new Date();
			int compareDate = TimeUtils.compareDate(endTime, now);
			if (compareDate == -1) {
				ids.add(theme.getId());
			}
		}
		if (ids.size() > 0) {
			Example updExample = new Example(LaunThemeAdministration.class);
			updExample.or().andIn("id", ids);
			LaunThemeAdministration record = new LaunThemeAdministration();
			record.setStatus(3);
			launThemeMapper.updateByExampleSelective(record, updExample);
			launRedisService.initThemeShop();

		}
	}

	/**
	 * @description 根据id去预览主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:27:59
	 * @param
	 */
	@Override
	public Map<String, Object> selectById(String id) {
		List<LaunThemeFile> list = launThemeFileService.selectByThemeId(id);
		List<LaunThemeFileVo> voList = new ArrayList<>();

		LaunThemeFileVo launThemeFileVo = null;
		for (LaunThemeFile launThemeFile : list) {
			launThemeFileVo = new LaunThemeFileVo();
			BeanUtils.copyProperties(launThemeFile, launThemeFileVo);
			voList.add(launThemeFileVo);
		}
		Map<String, Object> map = new HashMap<>();
		LaunThemeAdministration themeAdministration = launThemeMapper.selectByTheme(id);
		if(null == themeAdministration) { return map; }
		LaunThemeVo themeVo = new LaunThemeVo();
		BeanUtils.copyProperties(themeAdministration, themeVo);
		map.put("theme", themeVo);
		map.put("file", voList);
		return map;
	}

	/**
	 * @description 根据id去删除主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:34:15
	 * @param
	 */
	@Override
	public int delectById(String id) {
		LaunThemeAdministration launThemeAdministration = new LaunThemeAdministration();
		int i = 0;
		if (id != null) {
			launThemeAdministration.setId(id);
			launThemeAdministration.setStatus(4);
			i = launThemeMapper.updateByPrimaryKeySelective(launThemeAdministration);
		}
		return i;
	}

	/**
	 * @description 修改上下架状态
	 * @author liudawei
	 * @since 2018年4月26日 下午2:56:05
	 * @param
	 */
	@Override
	public void modifyStatus(String ids, Integer status) {
		String[] splits = ids.split(",");
		List<Long> idList = new ArrayList<>();
		if (splits.length > 0) {
			LaunThemeAdministration launThemeAdministration = new LaunThemeAdministration();
			try {
				if (ConstantUtlis.UP_SHELF == status) {
					launThemeAdministration.setStatus(2);
				}
				if (ConstantUtlis.DOWN_SHELF == status) {
					launThemeAdministration.setStatus(3);
				}
				for (String id : splits) {
					idList.add(Long.parseLong(id));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new DataStoreException(ErrorStatus.PARAMETER_ERROR);
			}
			Example example = new Example(LaunThemeAdministration.class);
			example.createCriteria().andIn("id", idList).andNotEqualTo("status", 0);
			launThemeMapper.updateByExampleSelective(launThemeAdministration, example);

			launRedisService.initThemeShop();
		}
	}

	@Override
	public int sort(String id, Integer sort) {
		return launThemeMapper.updateByPrimaryKeySelective(
				new LaunThemeAdministration().setSort(sort).setId(id));
	}

	@Override
	public int recommendSort(String id, Integer sort) {
		return launThemeMapper.updateByPrimaryKeySelective(
				new LaunThemeAdministration().setRecommendSort(sort).setId(id));
	}


	/**
	 * @description 保存主题
	 * @author liudawei
	 * @since 2018年4月29日 下午2:56:55
	 * @param
	 */
	@Override
	@Transactional
	public String saveTheme(String baseJson, String widgetJson, String themeJson, Integer saveType) {

		LaunThemeAdministration administration = JsonUtils.jsonToClass(themeJson, LaunThemeAdministration.class);
        //Long adminId = 0L;
        administration.setTenantId(String.valueOf(SaasHeaderContextV1.getTenantId()));
        administration.setCreator(SaasHeaderContextV1.getUserName());
        String themeId = null;
		/**
		 * 当save为0时，只是保存widget。只保存对应json数据 当save为1是，保存整个主题，执行后续结构化数据及打包过程
		 */
		if (saveType == 0) {

			log.info("主题管理----------暂存主题----------id:{}----------", themeId);

			administration.setWidgetJson(widgetJson);
			administration.setBasicJson(baseJson);
			administration.setThemeJson(themeJson);
			if (HStringUtlis.isNotBlank(administration.getSTime())) {
				administration.setStartTime(TimeUtils.millis2Date(Long.parseLong(administration.getSTime())));
			}
			if (HStringUtlis.isNotBlank(administration.getETime())) {
				administration.setEndTime(TimeUtils.millis2Date(Long.parseLong(administration.getETime())));
			}

			//2019/1/4 xukj add start
            if(StringUtils.isNotBlank(administration.getId())) {
				themeId = administration.getId();
				launThemeFileService.deleteById(themeId);
				administration.setPreviewPath(saveThemeFile(administration.getFilesJson(), themeId).get("previewPath"));
				launThemeMapper.updateByPrimaryKeySelective(administration);
				return themeId;
			}

			themeId = this.id();
			administration.setPreviewPath(saveThemeFile(administration.getFilesJson(), themeId).get("previewPath"))
					.setId(themeId).setCreateDate(TimeUtils.nowTimeStamp())
					.setRecommend(false).setRecommendSort(0).setSort(0)
					.setDownloadCount(0).setUsedCount(0)
					.setPrice(null == administration.getPrice()?new BigDecimal(0):administration.getPrice())
					.setStatus(ConstantUtlis.themeStatus.DOWN_SHELF);
			launThemeMapper.insertSelective(administration);

			//2019/1/4 xukj del start
			//if (administration.getId() != null) {
			//	launThemeFileService.deleteById(administration.getId());
			//	launThemeMapper.updateByPrimaryKeySelective(administration);
			//	return administration.getId();
			//} else {
			//	administration.setId(themeId);
				// 判断如果是渠道管理员
				//administration.setCreatorChannelId(adminId);
				//administration.setCreateId(adminId);
				// administration.setStatus(1);// 默认未上架状态
			//	launThemeMapper.insertSelective(administration);
			//}

			// 保存主题浏览图
			//Map<String, String> filesJson = administration.getFilesJson();
			//saveThemeFile(filesJson, themeId);


            //2019/1/4 xukj del end
			return themeId;
		}

		// 删除临时主题
		if (administration.getId() != null) {
			launThemeMapper.deleteByPrimaryKey(administration.getId());
			launThemeFileService.deleteById(administration.getId());
		}

		// 首先保存主题的主题，并将其对应的id返回，然后在将主键保存到各个配置文件对应的主键中，再去保存配置文件
		String creator = administration.getCreator();

		List<Long> channles = new ArrayList<Long>();

		/**
		 * 管理员创建时，不选则渠道则为给所有渠道都添加; 非管理员创建时，只添加自已所在渠道;
		 */
		if (HStringUtlis.isNotBlank(creator)) {
			String[] creatorId = creator.split(",");
			for (String string : creatorId) {
				channles.add(Long.parseLong(string));
			}
		} else {
			//channles.add(adminId);
		}

		// 定义下载文件map
		Map<String, String> map = new HashMap<String, String>();

		List<String> themeIdList = new ArrayList<>();
		Map<String, String> themeConfig = null;
		for (Long channleId : channles) {
			themeId = IdUtlis.Id(ConstantUtlis.PRIVATE_THEME, "");
			log.info("主题管理----------保存主题----------id:{}----------", themeId);
			themeIdList.add(themeId);
			administration.setId(themeId);
			administration.setBasicJson(baseJson);
			administration.setStartTime(TimeUtils.millis2Date(Long.parseLong(administration.getSTime())));
			administration.setEndTime(TimeUtils.millis2Date(Long.parseLong(administration.getETime())));
			administration.setWidgetJson(widgetJson);
			administration.setCreatorChannelId(channleId);
			administration.setThemeJson(themeJson);
			//administration.setCreateId(adminId);
			administration.setStatus(1);// 默认未上架状态

			// 保存主题浏览图
			Map<String, String> filesJson = administration.getFilesJson();
			Map<String, String> themeFile = saveThemeFile(filesJson, themeId);
			String previewPath = themeFile.get("previewPath");
			String urls = themeFile.get("urls");
			administration.setPreviewPath(previewPath);
			administration.setUrls(urls);

			launThemeMapper.insertSelective(administration);

			map.putAll(map);

			// 保存主题配置
			themeConfig = saveThemeConfig(themeId, baseJson, widgetJson);
		}
		Map<String, Object> assembleConfigJson = saveAssembleConfigJson(administration, themeConfig);

		Long fileSize = (Long) assembleConfigJson.get("length");

		String zipUrl = (String) assembleConfigJson.get("upload");
		Example example = new Example(LaunThemeAdministration.class);
		example.createCriteria().andIn("id", themeIdList);
		LaunThemeAdministration record = new LaunThemeAdministration();
		record.setFileSize(fileSize);
		record.setZipUrl(zipUrl);
		launThemeMapper.updateByExampleSelective(record, example);

		log.info("主题管理----------主题保存完成----------ids:{}----------", Arrays.asList(themeIdList));
		return themeId;
	}

    /**
     * 数据库主键排重
     * @return
     */
	private String id() {
		String tenantName = SaasHeaderContextV1.getTenantName();
        String themeId = IdUtlis.Id(ConstantUtlis.PRIVATE_THEME,  tenantName);
        LaunThemeAdministration th = launThemeMapper.selectByTheme(themeId);
        while(null != th) {
            themeId = IdUtlis.Id(ConstantUtlis.PRIVATE_THEME,  tenantName);
            th = launThemeMapper.selectById(themeId);
        }
        return themeId;
    }

	/**
	 * 解析持久化主题json
	 * 
	 * @author LL
	 * @date 2018年5月24日 上午11:37:00
	 * @param themeId主题id
	 * @param baseJson
	 * @param widgetJson
	 * @return
	 * @return Long
	 */
	@Async
	@SuppressWarnings("all")
	@Transactional
	public Map<String, String> saveThemeConfig(String themeId, String baseJson, String widgetJson) {

		log.info("主题管理----------保存主题相关配置----------id:{}----------", themeId);

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
					index += 1;
					Map<String, Object> bottomMap = new HashMap<String, Object>();
					bottomMap.put("id", (Long) scrollWidgetMap.get("Uid"));
					bottomMap.put("name", "page_" + index);
					bottomMap.put("index", index);
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

		return fileMaps;
	}

	/**
	 * 保存主图预览图
	 * 
	 * @author LL
	 * @date 2018年5月23日 下午1:58:35
	 * @param themeId 主题主键id
	 * @param map 文件
	 *            key:图片名,value:文件存储路径
	 * @return Long
	 */
	@Transactional
	public Map<String, String> saveThemeFile(Map<String, String> map, String themeId) {
		log.info("主题管理----------保存主题相关图片----------id:{}----------", themeId);

		Map<String, String> returnMap = new HashMap<String, String>();

		LaunThemeFile themeFile = null;
		int i = 0;
		if (map == null) {
			return null;
		}

		StringBuffer urls = new StringBuffer();
		int index = 0;
		for (Entry<String, String> map2 : map.entrySet()) {
			themeFile = new LaunThemeFile();
			Long id = IdUtlis.Id();
			themeFile.setFileName(map2.getKey());
			themeFile.setFilePath(map2.getValue());
			themeFile.setThemeId(themeId);
			themeFile.setFileIndex(index++);
			themeFile.setId(id);

			if (i == 0) {
				themeFile.setType(1);
				//xukj change start 2019/1/7
				//returnMap.put("previewPath", filePath + map2.getValue());
				returnMap.put("previewPath", map2.getValue());
				//xukj change end 2019/1/7
			} else {
				urls.append(filePath + map2.getValue()).append(",");
			}
			i++;
			launThemeFileService.insert(themeFile);
		}
		returnMap.put("urls", urls.toString());
		return returnMap;
	}

	/**
	 * 从http获取文件流下载到服务器
	 * 
	 * @author LL
	 * @date 2018年5月26日 下午4:23:32
	 * @param strUrl 文件路径
	 * @param goalUrl 目标地址
	 * @return void
	 */
	public void getFileToLocal(String strUrl, String goalUrl) {

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream in = conn.getInputStream();// 通过输入流获取数据

			File file = new File(goalUrl);
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				FileTool.copyStream(in, out);
			}
		} catch (Exception e) {
			System.out.println(strUrl + "-------------" + goalUrl);
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PACK_THEME_DOWNLOAD);
		}
	}

	@Override
	public Map<String, Object> saveAssembleConfigJson(LaunThemeAdministration theme, Map<String, String> fileMap) {
		String themeId = theme.getId();
		log.info("主题管理----------打包主题zip----------id:{}----------", theme.getId());

		long currentTimeMillis = System.currentTimeMillis();
		String themeConfigUrlReal = themeConfigUrl + currentTimeMillis + File.separator;

		String configImageUrl = themeConfigUrlReal + "image" + File.separator;
		String configLibUrl = themeConfigUrlReal + "lib" + File.separator;
		String configFontUrl = themeConfigUrlReal + "font" + File.separator;
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String filePath = "";
		String fileType = "";
		String fileName = "";
		String strPath = "";
		String goalPath = "";
		try {

			File configImgFile = new File(configImageUrl);
			if (!configImgFile.exists()) {
				configImgFile.mkdirs();
			}
			File configLibFile = new File(configLibUrl);
			if (!configLibFile.exists()) {
				configLibFile.mkdirs();
			}
			File fontUrlFile = new File(configFontUrl);
			if (!fontUrlFile.exists()) {
				fontUrlFile.mkdirs();
			}

			List<LaunThemeConfig> themeConfigList = configService.findByThemeId(themeId);

			// 下载文字信息
			String themeJson = theme.getThemeJson();
			Map<?, ?> jsonToMap = JsonUtils.JsonToMap(themeJson);
			Object fonts = jsonToMap.get("fonts");

			if (fonts != null) { // 页面操作选择了字体
				String fontsUrl = fonts.toString();
				String fontName = FileTool.getFileName(fontsUrl);
				goalPath = configFontUrl + fontName;
				strPath = fastDfsPath + fontsUrl;
				getFileToLocal(strPath, goalPath);
			} else {// 页面操作没有选择字体，默认拿库中第一条
				LaunFont selectFont = launFontService.selectFont();
				if (selectFont != null) {
					String fontsUrl = selectFont.getFilePath();
					String fontName = FileTool.getFileName(fontsUrl);
					goalPath = configFontUrl + fontName;
					strPath = fastDfsPath + fontsUrl;
					getFileToLocal(strPath, goalPath);
				}
			}

			// 下载图片,组件信息
			for (Entry<String, String> file : fileMap.entrySet()) {
				filePath = file.getValue();
				fileName = file.getKey();
				strPath = fastDfsPath + filePath;
				fileType = filePath.substring(filePath.lastIndexOf("."), filePath.length());

				// 下载图片文件
				if (".png".equals(fileType) || ".jpg".equals(fileType) || ".PNG".equals(fileType)
						|| ".JPG".equals(fileType) || ".jpeg".equals(fileType) || ".JEPG".equals(fileType)) {
					goalPath = configImageUrl + fileName;
					getFileToLocal(strPath, goalPath);
				} else {// 下载配置文件
					goalPath = configLibUrl + fileName;
					getFileToLocal(strPath, goalPath);
				}
			}

			// 封装config.json文件
			Map<String, Object> configJson = getStaticConfigJson(theme, themeId, currentTimeMillis);
			String objectToJson = JsonUtils.ObjectToJson(configJson);
			// 写入静态资源文件
			String configUrl = themeConfigUrlReal + "config.json";
			File configFile = new File(configUrl);
			if (configFile.exists()) {
				configFile.delete();
			} else {
				try {
					configFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileTool.writeFile(configFile, objectToJson);

			// 封装layout.json
			Map<String, List<LaunThemeConfig>> map = new HashMap<String, List<LaunThemeConfig>>();
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

			List<Map<String, Object>> packageMap = packageMap("0", map);

			Map<String, Object> orderViewPager = ThemeWidgetDetail.orderViewPager(packageMap.get(0));

			String layoutJson = JsonUtils.ObjectToJson(orderViewPager);
			String layoutJsonUrl = themeConfigUrlReal + "layout.json";
			File layoutFile = new File(layoutJsonUrl);
			if (layoutFile.exists()) {
				layoutFile.delete();
			} else {
				layoutFile.createNewFile();

			}
			FileTool.writeFile(layoutFile, layoutJson);

			// 将主题打包
			String zipFileName = System.currentTimeMillis() + ".zip";
			String zipFilePath = themeConfigUrlReal + zipFileName;
			FileTool.zipFile(themeConfigUrlReal, zipFilePath);

			File zip = new File(zipFilePath);
			Long length = zip.length();
			returnMap.put("length", length);
			try {
				String upload = launFileCrudService.upload(new FileInputStream(zip), length, "zip", null);
				returnMap.put("upload", upload);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			FileTool.deleteDir(themeConfigUrlReal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataStoreException(ErrorStatus.PACK_THEME_ZIP);
		}
		return returnMap;
	}

	/**
	 * 递归解析主题widget子父级关系并封装
	 * 
	 * @author LL
	 * @date 2018年5月14日 下午2:25:56
	 * @param
	 * @return List<Map<String,Object>>
	 */
	@SuppressWarnings("unchecked")
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

	/**
	 * 封装主题静态资源文件
	 * 
	 * @author LL
	 * @param currentTimeMillis
	 * @date 2018年5月10日 下午3:56:47
	 * @param theme 主题对象
	 * @return void
	 */
	public Map<String, Object> getStaticConfigJson(LaunThemeAdministration theme, String themeId,
			long currentTimeMillis) {
		//
		String font = "d616244961d242a799807ff680a29f2d.ttf";
		String goalPath = themeConfigUrl + currentTimeMillis + File.separator + "image/";

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("id", theme.getId());
		returnMap.put("name", theme.getTitle());
		returnMap.put("font", font);
		returnMap.put("category", theme.getTypeName());

		Map<String, String> musicMap = new HashMap<>();
		String musicAppImgUrl = theme.getMusicAppImgUrl();
		String musicAppImgName = musicAppImgUrl.substring(musicAppImgUrl.lastIndexOf("/") + 1, musicAppImgUrl.length());
		musicMap.put("background", musicAppImgName);
		returnMap.put("musicApp", musicMap);
		// 下载文件
		getFileToLocal(fastDfsPath + musicAppImgUrl, goalPath + musicAppImgName);

		Map<String, String> phoneMap = new HashMap<>();
		String phoneAppImgUrl = theme.getPhoneAppImgUrl();
		String phoneAppImgName = phoneAppImgUrl.substring(phoneAppImgUrl.lastIndexOf("/") + 1, phoneAppImgUrl.length());
		phoneMap.put("background", phoneAppImgName);
		returnMap.put("phoneApp", phoneMap);
		// 下载文件
		getFileToLocal(fastDfsPath + phoneAppImgUrl, goalPath + phoneAppImgName);

		Map<String, String> weatherMap = new HashMap<>();
		String weatherAppImgUrl = theme.getWeatherAppImgUrl();
		String weatherAppImgName = weatherAppImgUrl.substring(weatherAppImgUrl.lastIndexOf("/") + 1,
				weatherAppImgUrl.length());
		weatherMap.put("background", weatherAppImgName);
		returnMap.put("themeStore", weatherMap);
		// 下载文件
		getFileToLocal(fastDfsPath + weatherAppImgUrl, goalPath + weatherAppImgName);

		Map<String, String> fmAppMap = new HashMap<>();
		String fmAppImgUrl = theme.getFmAppImgUrl();
		String fmAppImgName = fmAppImgUrl.substring(fmAppImgUrl.lastIndexOf("/") + 1, fmAppImgUrl.length());
		fmAppMap.put("background", musicAppImgName);
		returnMap.put("fmApp", fmAppMap);
		// 下载文件
		getFileToLocal(fastDfsPath + fmAppImgUrl, goalPath + fmAppImgName);

		// 封装posters
		List<Map<String, String>> postersValue = getPosterMap(themeId);
		returnMap.put("posters", postersValue);

		return returnMap;
	}

	public List<Map<String, String>> getPosterMap(String themeId) {
		List<LaunThemeConfig> themeConfigList = configService.findByThemeId(themeId);
		int posterType = 2;
		String posterIdList = "";
		for (LaunThemeConfig launThemeConfig : themeConfigList) {
			if (launThemeConfig.getType() == posterType) {
				posterIdList += launThemeConfig.getWidgetId() + ",";
			}
		}
		List<LaunApplicationPoster> posterList = applicationPostersService.selectByIds(posterIdList);

		// 封装posters
		List<Map<String, String>> postersValue = new ArrayList<Map<String, String>>();

		for (LaunApplicationPoster poster : posterList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", poster.getId().toString());
			map.put("path", FileTool.getFileName(poster.getPosterPath()));
			map.put("startTime", TimeUtils.date2Millis(poster.getStartTime()) + "");
			map.put("endTime", TimeUtils.date2Millis(poster.getEndTime()) + "");
			postersValue.add(map);
		}
		return postersValue;
	}

	@Override
	public String updateTheme(String baseJson, String widgetJson, String themeJson, Integer saveType) {

		LaunThemeAdministration administration = JsonUtils.jsonToClass(themeJson, LaunThemeAdministration.class);

		// 如果修改的是暂存的主题，将状态修改为 未上架，执行保存操作
        if (administration.getStatus() != null && administration.getStatus() == 0 && saveType == 1) {
            administration.setStatus(1);
            String saveTheme = saveTheme(baseJson, widgetJson, themeJson, saveType);
            return saveTheme;
        }

		if (HStringUtlis.isNotBlank(administration.getCreator())) {
			delectById(administration.getId());
			saveTheme(baseJson, widgetJson, themeJson, saveType);
		}

		String themeId = administration.getId();
		launThemeFileService.deleteById(themeId);
		// 删除之前配置文件
		configService.deleteByThemeId(themeId);
		// 保存主题浏览图
		Map<String, String> filesJson = administration.getFilesJson();
		if (filesJson != null) {
			Map<String, String> themeFile = saveThemeFile(filesJson, themeId);
			String previewPath = themeFile.get("previewPath");
			String urls = themeFile.get("urls");
			administration.setPreviewPath(previewPath);
			administration.setUrls(urls);
		}
		/**
		 * 当save为0时，只是保存widget。只保存对应json数据 当save为1是，保存整个主题，执行后续结构化数据及打包过程
		 */
		if (saveType == 0) {
			if (HStringUtlis.isNotBlank(administration.getSTime())) {
				administration.setStartTime(TimeUtils.millis2Date(Long.parseLong(administration.getSTime())));
			}
			if (HStringUtlis.isNotBlank(administration.getETime())) {
				administration.setEndTime(TimeUtils.millis2Date(Long.parseLong(administration.getETime())));
			}

			administration.setWidgetJson(widgetJson);
			administration.setBasicJson(baseJson);
			administration.setThemeJson(themeJson);
			launThemeMapper.updateByPrimaryKeySelective(administration);
			return themeId;
		}

		/**
		 * 修改内容
		 */
		administration.setId(themeId);
		administration.setBasicJson(baseJson);
		administration.setStartTime(TimeUtils.millis2Date(Long.parseLong(administration.getSTime())));
		administration.setEndTime(TimeUtils.millis2Date(Long.parseLong(administration.getETime())));
		administration.setWidgetJson(widgetJson);
		administration.setThemeJson(themeJson);

		// administration.setStatus(1);// 默认未上架状态
		// launThemeMapper.insertSelective(administration);

		// 保存主题配置
		Map<String, String> saveThemeConfig = saveThemeConfig(themeId, baseJson, widgetJson);

		Map<String, Object> assembleConfigJson = saveAssembleConfigJson(administration, saveThemeConfig);

		Long fileSize = (Long) assembleConfigJson.get("length");

		String zipUrl = (String) assembleConfigJson.get("upload");
		/**
		 * 修改包的大小
		 */
		Example example = new Example(LaunThemeAdministration.class);
		example.createCriteria().andEqualTo("id", themeId);
		administration.setFileSize(fileSize);
		administration.setZipUrl(zipUrl);
		launThemeMapper.updateByExampleSelective(administration, example);
		return null;
	}

	@Override
	public List<Map<String, String>> getEffeCount() {

		return launThemeMapper.getEffeCount();
	}



    @Override
    public LaunThemeUploadFileVo upload(MultipartFile upload) {

        LaunThemeUploadFileVo launThemeUploadFileVo = new LaunThemeUploadFileVo();

        Path path = FileTool.createTempFile(upThemePreix , ConstantUtlis.file.ZIP,
                FileTool.getBytes(upload));
        FileTool.unZipFile(path.toFile().getAbsolutePath(), tempPath);
        FileTool.delTempFile(path);

        this.checkZip(tempPath);
        //主题主预览图
        String imgMain = null;

        for(File file:FileTool.listFiles(tempPath)) {
            if(file.getName().equals(upThemeProp)) {
                imgMain = this.parseProp(file,launThemeUploadFileVo);
                continue;
            }
            launThemeUploadFileVo.setFileSize(file.length());
            String zipPath = this.upload2fastFDS(file, ConstantUtlis.file.ZIP);
            launThemeUploadFileVo.setZipUrl(zipPath);
        }

        List<File> imgFiles = FileTool.listFiles(tempPath + upThemeImgPath);
        List<LaunThemeFileVo> imgs = this.uploadImgs(imgFiles, imgMain);
        launThemeUploadFileVo.setThemeImgsList(imgs);

        FileTool.del(new File(tempPath));
        log.info("删除本地临时文件夹...");
        return launThemeUploadFileVo;
    }

	@Override
	public int recommend(String id, boolean value) {
        return launThemeMapper.updateByPrimaryKeySelective(
        		new LaunThemeAdministration().setRecommend(value).setId(id));
	}

	private String parseProp(File file, LaunThemeUploadFileVo launThemeUploadFileVo) {
        Properties prop = FileTool.file2Prop(file);
        log.info("解析properties完成...prop:{}", prop.toString());
        launThemeUploadFileVo.setLongResolution(prop.getProperty(upThemeLong));
        launThemeUploadFileVo.setWideResolution(prop.getProperty(upThemeWidth));
        return prop.getProperty(upThemeImgMain);
    }

    private List<LaunThemeFileVo> uploadImgs(List<File> imgFiles, String imgMain) {
        List<LaunThemeFileVo> imgs = new ArrayList<>();
        for(File file:imgFiles) {
            String ExName = FileTool.getExtentionWithoutPoint(file.getName());
            String imgPath = this.upload2fastFDS(file, ExName);
            imgs.add(new LaunThemeFileVo()
                    .setFileName(file.getName())
                    .setFilePath(imgPath)
                    .setType(file.getName().equals(imgMain)?1:2));
        }
        log.info("上传图片完成...");
        return imgs;
    }

    private String upload2fastFDS(File file, String exName) {
        try (InputStream fileInputStream = new FileInputStream(file)) {
            String path = launFileCrudService.upload(fileInputStream,
                    file.length(), exName, null);
            log.info("上传{}完成...路径：{}", file.getName(), path);
            return path;
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private boolean checkZip(String tempPath) {

	    boolean imgFlag = false;
        boolean propFlag = false;
        boolean zipFlag = false;
        boolean imgCountFlag = false;

	    for(String fileName:FileTool.listFilename(tempPath)) {
            if(fileName.equals(upThemeImgPath)){imgFlag = !imgFlag;}
            if(fileName.equals(upThemeProp)){propFlag = !propFlag;}
            if(fileName.contains(ConstantUtlis.file.DOT_ZIP)){zipFlag = !zipFlag;}
        }

        if(imgFlag) {
            int imgCount = FileTool.listFiles(tempPath + upThemeImgPath).size();
            if(imgCount < 5 && imgCount > 0) {
                imgCountFlag = !imgCountFlag;
            }
        }

        if(!imgFlag || !propFlag || !zipFlag || !imgCountFlag) {
            FileTool.del(new File(tempPath));
            log.info("删除本地临时文件夹...");
        }

        if(!imgFlag){ throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_IMGS); }
        if(!propFlag){throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_CONFIG);}
        if(!zipFlag){throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_ZIP);}
        if(!imgCountFlag){throw new DataStoreException(ErrorStatus.UPLOAD_THEME_TOO_MANY_IMG);}




	    return imgFlag && propFlag && zipFlag && imgCountFlag;
    }

}
