package com.pactera.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.dao.LaunApplicationMapper;
import com.pactera.business.service.LaunApplicationService;
import com.pactera.business.service.LaunChannelService;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.LaunApplication;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.domain.LaunChannel;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IconUtil;
import com.pactera.utlis.IdUtlis;
import com.pactera.vo.LaunApplicationVo;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import tk.mybatis.mapper.entity.Example;

/**
 * @description:应用实现类
 * @author:Scott
 * @since:2018年4月26日 下午2:16:00
 */
@Service
public class LaunApplicationServiceImpl implements LaunApplicationService {

	@Autowired
	private LaunApplicationMapper launApplicationMapper;

	@Autowired
	public FastFileStorageClient fastFileStorageClient;

	@Autowired
	private LaunChannelService launChannelService;

	// @Value("${file.path}")
	// private String filePath;

	@Value("${system.conf.apkTempPath}")
	private String apkTempPath;

	@Transactional
	@Override
	public Integer add(LaunApplication app, String channelIds) {
		String[] ids = channelIds.split(",");
		Arrays.asList(ids).stream().forEach(channelId -> {
			app.setId(IdUtlis.Id());
			app.setChannelId(channelId);
			launApplicationMapper.insertSelective(app);

		});
		return ConstantUtlis.SUCCESS;
	}

	@Override
	public LaunApplicationVo upload(MultipartFile file) {
		String tempFilePath = apkTempPath + File.separator + System.currentTimeMillis();
		String tempFileIconPath = apkTempPath + File.separator + System.currentTimeMillis() + "_ICON.png";
		String fileName = file.getOriginalFilename();
		System.out.println("上传的文件名为:"+fileName);
		try {
			String test = new String(fileName.getBytes(), "UTF-8");
			System.out.println("上传的文件名为:"+test);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String type = fileName.substring(fileName.indexOf("."), fileName.length());
		File tempFile = new File(tempFilePath + type);
		LaunApplicationVo app = new LaunApplicationVo();
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
			@SuppressWarnings("resource")
			ApkFile apkFile = new ApkFile(tempFile);
			ApkMeta apkMeta = apkFile.getApkMeta();
			app.setName(apkMeta.getLabel());
			app.setPackageName(apkMeta.getPackageName());
			Long id = IdUtlis.Id();
			app.setPackageId(id.toString());
			// 获取应用图标
			String iconPath = apkMeta.getIcon();
			// 下载到temp目录
			IconUtil.extractFileFromApk(tempFilePath + type, iconPath, tempFileIconPath);
			File iconFile = new File(tempFileIconPath);
			FileInputStream inputStream = new FileInputStream(tempFileIconPath);
			StorePath storPath = fastFileStorageClient.uploadFile(inputStream, iconFile.length(), "png", null);
			app.setLogoPath(storPath.getFullPath());
			app.setFileName(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return app;
	}

	@Override
	public PageInfo<LaunApplicationVo> findByCondition(Integer pageNum, Integer pageSize, String channelId,
			String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		if (HStringUtlis.isNotBlank(keyword)) {
			keyword = "%" + keyword + "%";
		}
		List<LaunApplicationVo> retList = launApplicationMapper.findByCondition(channelId, keyword);
		Map<String, Integer> temp = new HashMap<>();
		for (LaunApplicationVo launApplication : retList) {
			String key = launApplication.getChannelId();
			if (temp.containsKey(key)) {
				// 如果包含 则修改count值
				temp.put(key, temp.get(key) + 1);
			} else {
				temp.put(key, 1);
			}
		}
		// 回填mergecount
		for (LaunApplicationVo launApplication : retList) {
			String key = launApplication.getChannelId();
			if (temp.containsKey(key)) {
				launApplication.setMegerRow(temp.get(key));
				// 删除已经合并的
				temp.remove(key);
			} else {
				// 默认不合并
				launApplication.setMegerRow(1);
			}
		}
		return new PageInfo<>(retList);
	}

	@Override
	public Map<String, Object> findPosterByAppId(Long id) {
		Map<String, Object> retMap = new HashMap<>();
		List<LaunApplicationPoster> ret = new ArrayList<>();
		List<LaunApplicationPoster> customRet = new ArrayList<>();
		// 查询默认logo
		LaunApplication app = launApplicationMapper.selectByPrimaryKey(id);
		Date startTime = app.getStartTime();
		Date endTime = app.getEndTime();

		String defaultLogoPath = app.getLogoPath();
		// 查询默认海报
		ret = launApplicationMapper.findPosterByAppIdAndType(id, 0);
		// 如果没有已经配置的则返回默认图片
		if (ret.size() == 0) {
			// 查询默认应用logo图片
			LaunApplicationPoster posterTemp1 = new LaunApplicationPoster();
			posterTemp1.setWidth(4);
			posterTemp1.setHeight(2);
			// posterTemp1.setPosterPath(defaultLogoPath);
			posterTemp1.setType(0);
			ret.add(posterTemp1);
			LaunApplicationPoster posterTemp2 = new LaunApplicationPoster();
			posterTemp2.setWidth(2);
			posterTemp2.setHeight(2);
			// posterTemp2.setPosterPath(defaultLogoPath);
			posterTemp2.setType(0);
			ret.add(posterTemp2);
			LaunApplicationPoster posterTemp3 = new LaunApplicationPoster();
			posterTemp3.setWidth(2);
			posterTemp3.setHeight(1);
			// posterTemp3.setPosterPath(defaultLogoPath);
			posterTemp3.setType(0);
			ret.add(posterTemp3);
		}
		// 查询自定义海报
		customRet = launApplicationMapper.findCustomPosterByAppId(id);
		// 组装在一起
		ret.addAll(customRet);

		retMap.put("defaultLogoPath", defaultLogoPath);
		retMap.put("posterList", ret);
		retMap.put("startTime", startTime);
		retMap.put("endTime", endTime);
		return retMap;
	}

	@Override
	public String InsertPoster(MultipartFile file) {
		String name = file.getOriginalFilename();
		String type = name.substring(name.lastIndexOf(".") + 1, name.length());
		String path = "";
		try {
			StorePath sp = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), type, null);
			path = sp.getFullPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	@Transactional
	@Override
	public Integer savePoster(Date startTime, Date endTime, Long applicationId,
			List<LaunApplicationPoster> posterList) {
		// 清空poster
		Integer order = 0;
		launApplicationMapper.deletePosterByAppId(applicationId);
		for (LaunApplicationPoster launApplicationPoster : posterList) {
			launApplicationPoster.setId(IdUtlis.Id());
			launApplicationPoster.setApplicationId(applicationId);
			launApplicationPoster.setOrders(order++);
			launApplicationMapper.savePoster(launApplicationPoster);
		}
		// 修改有效期
		LaunApplication app = new LaunApplication();
		app.setStartTime(startTime);
		app.setEndTime(endTime);
		Example example = new Example(LaunApplication.class);
		example.or().andEqualTo("id", applicationId);
		Integer ret = launApplicationMapper.updateByExampleSelective(app, example);
		return ret;
	}

	@Override
	public LaunApplicationVo findById(Long id) {
		LaunApplicationVo appVo = new  LaunApplicationVo();
		LaunApplication app = launApplicationMapper.selectByPrimaryKey(id);
		LaunChannel channel = launChannelService.findById(app.getChannelId());
		if (channel != null) {
			app.setChannelName(channel.getName());
		}
		BeanUtils.copyProperties(app, appVo);
		return appVo;
	}

	@Transactional
	@Override
	public Integer update(LaunApplication application, String channelIds) {
		application.setChannelId(channelIds);
		application.setUpdateDate(new Date());
		return launApplicationMapper.updateByPrimaryKeySelective(application);
	}

}
