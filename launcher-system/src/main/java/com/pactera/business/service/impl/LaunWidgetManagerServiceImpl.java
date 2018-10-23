package com.pactera.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.dao.LaunAttributeMapper;
import com.pactera.business.dao.LaunWidgetChannelMapper;
import com.pactera.business.dao.LaunWidgetFileMapper;
import com.pactera.business.dao.LaunWidgetManagerMapper;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.dao.LaunWidgetTypeMapper;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.config.security.UserUtlis;
import com.pactera.domain.LaunAttribute;
import com.pactera.domain.LaunUser;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetChannel;
import com.pactera.domain.LaunWidgetFile;
import com.pactera.domain.LaunWidgetMinProperty;
import com.pactera.domain.LaunWidgetProperty;
import com.pactera.domain.LaunWidgetType;
import com.pactera.util.CheckWidgetImg;
import com.pactera.util.ThemeWidgetDetail;
import com.pactera.util.widgetToJson;
import com.pactera.utlis.FileTool;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;
import com.pactera.utlis.JsonUtils;
import com.pactera.vo.LaunAttributeVo;
import com.pactera.vo.LaunWidgetTypeVo;
import com.pactera.vo.LaunWidgetVo;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @description:Widget管理
 * @author:wangyaqun
 * @since:2018年4月26日 上午10:52:56
 */
@Service
@Slf4j
public class LaunWidgetManagerServiceImpl implements LaunWidgetManagerService {
	@Autowired
	public LaunWidgetManagerMapper launWidgetManagerMapper;
	@Autowired
	public FastFileStorageClient fastFileStorageClient;

	@Autowired
	private LaunWidgetFileMapper fileMapper;

	@Autowired
	private LaunWidgetMinPropertyMapper launWidgetMinPropertyMapper;

	@Autowired
	private LaunWidgetTypeMapper launWidgetTypeMapper;

	@Autowired
	private LaunAttributeMapper launAttributeMapper;

	@Autowired
	private LaunWidgetFileMapper launWidgetFileMapper;

	@Autowired
	private LaunWidgetChannelMapper launWidgetChannelMapper;

	@Autowired
	private Environment env;

	@Override
	@Transactional
	public String saveGroupWidget(String widgetjon, String groupjson) {
		String message = "";
		String customWidgetJson = ThemeWidgetDetail.packageCustomWidget(groupjson, widgetjon);
		try {
			LaunWidget widget = widgetToJson.json2Widget(widgetjon);
			widget.setCustomWidgetJson(customWidgetJson);
			widget.setProperty(groupjson);
			LaunWidget checkwidget = launWidgetManagerMapper.selectByPrimaryKey(widget.getId());
			// 新增
			if (checkwidget == null) {
				LaunUser user = UserUtlis.launUser();
				widget.setCreator(user.getId());
				widget.setCreateway(user.getChannelId() == null ? 1 : 2);
				widget.setCreateDate(new Date());
				launWidgetManagerMapper.insertSelective(widget);
				// 修改的
			} else {
				LaunWidget widgetdel = new LaunWidget();
				widgetdel.setParentId(widget.getId());
				// 删除全部子widget
				launWidgetManagerMapper.delete(widgetdel);
				launWidgetManagerMapper.updateByPrimaryKeySelective(widget);
			}

			List<LaunWidget> widgetlist = widgetToJson.json2gwidget(groupjson, widget.getId());
			if (widgetlist != null) {
				for (LaunWidget gwidget : widgetlist) {
					gwidget.setParentId(widget.getId());
					launWidgetManagerMapper.insertSelective(gwidget);
				}
			}
			message = widget.getId() + "";
		} catch (Exception e) {
			log.error("【保存widget】异常:----{}", e);
			throw new DataStoreException(ErrorStatus.WIDGETPARSE_ERROR);
		}

		return message;
	}

	@Override
	@Transactional
	public String getGoupWidgetById(Long widgetId) {
		String json = "";
		LaunWidget widget = launWidgetManagerMapper.selectByPrimaryKey(widgetId);
		try {
			json = widgetToJson.widget2json(widget);
		} catch (Exception e) {
			json = "系统异常";
			log.error("【获取widget】异常----{}", e);
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		return json;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@Transactional
	public int updateSingleWidget(String widgetjson, String filepath, String channels) {
		Map filemap = JsonUtils.JsonToMap(filepath);

		// String message = "变体修改成功";
		int status = 1;

		try {
			List<LaunWidget> widgetlist = widgetToJson.json2widget(widgetjson);
			LaunWidgetChannel widgetchannel = null;
			LaunWidgetFile widgetfile = null;
			for (LaunWidget widget : widgetlist) {
				widgetchannel = new LaunWidgetChannel();
				widgetchannel.setWidgetId(widget.getId());
				// 删除关联重新添加
				launWidgetChannelMapper.delete(widgetchannel);
				widgetfile = new LaunWidgetFile();
				widgetfile.setWidgetId(widget.getId());
				launWidgetFileMapper.delete(widgetfile);
				// 全部渠道
				if (channels == null || "".equals(channels)) {
					Integer channelway = 1;
					widget.setChannelway(channelway);
					// 所选渠道
				} else {
					widget.setChannelway(0);
					String[] channel = channels.split(",");
					for (String channelId : channel) {
						widgetchannel.setChannelId(Long.parseLong(channelId));
						launWidgetChannelMapper.insertSelective(widgetchannel);
					}
				}
				// 修改widget信息
				launWidgetManagerMapper.updateByPrimaryKeySelective(widget);

				// 添加widget图片信息
				widgetfile = new LaunWidgetFile();
				widgetfile.setWidgetId(widget.getId());
				launWidgetFileMapper.delete(widgetfile);
				for (Iterator ite = filemap.entrySet().iterator(); ite.hasNext();) {
					Map.Entry e = (Map.Entry) ite.next();
					widgetfile = new LaunWidgetFile();
					if (e.getKey().toString().contains("fengmian")) {
						widgetfile.setType(1);
					} else if (e.getKey().toString().contains("zip")) {
						continue;
					} else {
						widgetfile.setType(3);
					}
					widgetfile.setWidgetId(widget.getId());
					widgetfile.setId(IdUtlis.Id());
					widgetfile.setCreateDate(new Date());
					widgetfile.setPath(e.getValue().toString());
					widgetfile.setFileName(e.getKey().toString());
					launWidgetFileMapper.insertSelective(widgetfile);
				}

			}
		} catch (Exception e) {
			// message = "系统错误！";
			status = 0;
			log.error("【修改widget】异常----{}", e);
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		return status;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@Transactional
	public int saveSingleWidget(String widgetjson, String filepath, String channels) {
		Map filemap = JsonUtils.JsonToMap(filepath);
		// String message = "创建变体成功";
		int status = 1;
		LaunUser user = UserUtlis.launUser();
		try {
			List<LaunWidget> widgetlist = widgetToJson.json2widget(widgetjson);
			LaunWidgetChannel widgetchannel = null;
			LaunWidgetFile widgetfile = null;
			for (LaunWidget widget : widgetlist) {
				Long widgetOldId = widget.getId();
				LaunWidget oldWidget = launWidgetManagerMapper.selectByPrimaryKey(widgetOldId);
				// 基础变体
				widget.setType(1);
				widget.setId(IdUtlis.Id());
				widget.setCodeId(oldWidget.getCodeId());
				widget.setParentId(0L);
				widget.setCreateDate(new Date());
				widget.setCreator(user.getId());
				widget.setCreateway(user.getChannelId() == null ? 1 : 2);
				// 全部渠道
				if (channels == null || "".equals(channels)) {
					Integer channelway = 1;
					widget.setChannelway(channelway);
					// 所选渠道
				} else {
					widget.setChannelway(0);
					String[] channel = channels.split(",");
					for (String channelId : channel) {
						widgetchannel = new LaunWidgetChannel();
						widgetchannel.setWidgetId(widget.getId());
						widgetchannel.setChannelId(Long.parseLong(channelId));
						// widget关联渠道
						launWidgetChannelMapper.insertSelective(widgetchannel);
					}
				}
				// 添加变体
				launWidgetManagerMapper.insertSelective(widget);

				// 添加widget图片信息
				widgetfile = new LaunWidgetFile();
				for (Iterator ite = filemap.entrySet().iterator(); ite.hasNext();) {
					Map.Entry e = (Map.Entry) ite.next();
					widgetfile = new LaunWidgetFile();
					if (e.getKey().toString().contains("fengmian")) {
						widgetfile.setType(1);
					} else if (e.getKey().toString().contains("zip")) {
						continue;
					} else {
						widgetfile.setType(3);
					}
					widgetfile.setWidgetId(widget.getId());
					widgetfile.setId(IdUtlis.Id());
					widgetfile.setCreateDate(new Date());
					widgetfile.setPath(e.getValue().toString());
					widgetfile.setFileName(e.getKey().toString());
					launWidgetFileMapper.insertSelective(widgetfile);
				}
			}
		} catch (Exception e) {
			// message = "系统错误！";
			log.error("【保存widget】异常----{}", e);
			status = 0;
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		return status;
	}

	@Override
	public void insertWidgetChannel(LaunWidgetChannel widgetchannel) {
		launWidgetChannelMapper.insert(widgetchannel);
	}

	@Override
	public void insertWidget(LaunWidget widget) {
		launWidgetManagerMapper.insert(widget);
	}

	@Override
	public LaunWidgetFile findWidgetFileById(Long widgetId) {
		LaunWidgetFile widgetfile = new LaunWidgetFile();
		widgetfile.setWidgetId(widgetId);
		widgetfile.setType(2);
		widgetfile = launWidgetFileMapper.selectOne(widgetfile);
		return widgetfile;
	}

	@Override
	public String widgetPreview(Long widgetId) {
		String path = launWidgetManagerMapper.widgetPreview(widgetId);
		return path;
	}

	@Override
	public LaunWidget findWidgetById(Long widgetId) {
		LaunWidget launwidget = launWidgetManagerMapper.selectByPrimaryKey(widgetId);
		return launwidget;
	}

	/**
	 * 添加Widget类型
	 */
	@Override
	@Transactional
	public String insertMidgetType(String typeName) {
		String message = "";
		if (typeName != null && typeName != "") {
			String[] split = typeName.split(",");
			if (split.length > 0) {
				int i = 0;
				for (String string : split) {
					Long id = IdUtlis.Id();
					Date createDate = new Date();
					i = launWidgetManagerMapper.insertMidgetType(id, string, createDate);
				}
				if (i == 1) {
					message = "类型添加成功";
				} else {
					message = "类型添加失败";
					throw new DataStoreException(ErrorStatus.SYS_ERROR);
				}
			}
		} else {
			log.error("【保存widget类型】异常");
			message = "类型不能为空";
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		return message;
	}

	@Override
	@Transactional
	public String fileReplace(MultipartFile file, Long widgetId) {
		String message = SuccessStatus.WIDGETUPLOAD_SUCCESS.message();
		StorePath storePath = null;
		String originalFilename = file.getOriginalFilename();
		Long id = IdUtlis.Id();
		try {
			storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
					FilenameUtils.getExtension(file.getOriginalFilename()), null);
		} catch (Exception e) {
			log.error("【fastdfs服务】异常--{}", e);
			message = ErrorStatus.FASTDFS_ERROR.message();
			throw new RuntimeException();
		}
		LaunWidgetFile launWidgetFile = new LaunWidgetFile();
		launWidgetFile.setWidgetId(widgetId);
		// 根据widget主键删除关联的文件
		launWidgetFileMapper.delete(launWidgetFile);
		launWidgetFile.setId(id);
		launWidgetFile.setCreateDate(new Date());
		launWidgetFile.setUpdateDate(new Date());
		launWidgetFile.setFileName(originalFilename);
		launWidgetFile.setPath(storePath.getFullPath());
		launWidgetFile.setType(2);
		launWidgetFile.setWidgetId(widgetId);
		launWidgetFileMapper.insertSelective(launWidgetFile);
		// 从fastdfs下载的压缩包的路径
		String downloadPath = downFile(storePath.getGroup(), storePath.getPath());
		// 解压包
		String destDir = env.getProperty("system.conf.widgetTempPath") + File.separator + id;
		FileTool.unZipFile(downloadPath, destDir);

		// 解析widget.json
		replaceunJsonParsing(destDir + File.separator + "widget.json", destDir + File.separator + "image",
				destDir + File.separator + "lib", widgetId);

		return message;
	}

	/**
	 * 上传基础Widget包
	 */
	@Override
	@Transactional
	public String fileUpload(MultipartFile file) {
		String message = "";
		Long id = IdUtlis.Id();
		log.error("--------------uploda file--------------id:【{}】:{}", id);
		try {
			StorePath storePath = null;
			String originalFilename = file.getOriginalFilename();
			// String fileName =
			// originalFilename.substring(originalFilename.lastIndexOf(".") +
			// 1);
			try {
				storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
						FilenameUtils.getExtension(file.getOriginalFilename()), null);
			} catch (Exception e) {
				log.error("【fastdfs服务】异常----{}", e);
				message = ErrorStatus.FASTDFS_ERROR.message();
				throw new DataStoreException(ErrorStatus.FASTDFS_ERROR);
			}
			// String filePath = env.getProperty("file.path");
			LaunWidgetFile launWidgetFile = new LaunWidgetFile();
			launWidgetFile.setId(id);
			launWidgetFile.setCreateDate(new Date());
			launWidgetFile.setFileName(originalFilename);
			launWidgetFile.setPath(storePath.getFullPath());
			launWidgetFile.setType(2);
			// launWidgetFile.setUserId(null); 现在没有人
			// 解析路径
			// Map<String, Object> mapPath = analyzeUrl(storePath.getPath());
			// 下载(包括重命名)
			// String downloadPath = "";

			// 从fastdfs下载的压缩包的路径
			String downloadPath = downFile(storePath.getGroup(), storePath.getPath());
			// 解压包
			String destDir = env.getProperty("system.conf.widgetTempPath") + File.separator + id;
			FileTool.unZipFile(downloadPath, destDir);
			// 解析widget.json
			LaunWidget lanuwidget = unJsonParsing(destDir + File.separator + "widget.json",
					destDir + File.separator + "image", destDir + File.separator + "lib");
			launWidgetFile.setWidgetId(lanuwidget.getId());
			int i = fileMapper.insertSelective(launWidgetFile);
			if (i == 1) {
				message = SuccessStatus.WIDGETUPLOAD_SUCCESS.message();
			} else {
				message = ErrorStatus.WIDGETUPLOAD_ERROR.message();
			}
		} catch (Exception e) {
			log.error("--------------uploda file--------------id:【{}】:{}", id, e);
		}

		return message;
	}

	/**
	 * 解析url
	 */
	public Map<String, Object> analyzeUrl(String sumPath) {
		Map<String, Object> map = new HashMap<>();
		// 解析url
		int indexOf = sumPath.indexOf("/");
		String modifyUrl = sumPath.substring(indexOf + 1);
		int modifyIndex = modifyUrl.indexOf("/");
		String group = modifyUrl.substring(0, modifyIndex);
		String path = modifyUrl.substring(modifyIndex + 1);
		map.put("group", group);
		map.put("path", path);
		return map;
	}

	/**
	 * 从fastdfs下载文件
	 * 
	 * @return 返回下载文件的路径
	 */
	@SuppressWarnings("resource")
	public String downFile(String groupName, String path) {
		String filename = path.substring(path.lastIndexOf("/") + 1);
		String descDir = env.getProperty("system.conf.widgetTempPath");
		File file = new File(descDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			byte[] bytes = fastFileStorageClient.downloadFile(groupName, path, new DownloadByteArray());
			FileOutputStream stream = new FileOutputStream(descDir + File.separator + filename);
			descDir = descDir + File.separator + filename;
			stream.write(bytes);
		} catch (Exception e) {
			log.error("【fastdfs服务】异常----{}", e);
			throw new DataStoreException(ErrorStatus.FASTDFS_ERROR);
		}
		return descDir;
	}

	/**
	 * 解压基础包到本地
	 */
	// public void unZipFiles(String downloadPath, String originalFilename) {
	// File unFile = new File(downloadPath + originalFilename);
	// String descDir = env.getProperty("system.conf.widgetTempPath");
	// try {
	// ZipFile zip = new ZipFile(unFile, Charset.forName("GBK"));// 解决中文文件夹乱码
	// String name = zip.getName().substring(zip.getName().lastIndexOf('\\') +
	// 1,
	// zip.getName().lastIndexOf('.'));
	// File pathFile = new File(descDir + name);
	// if (!pathFile.exists()) {
	// pathFile.mkdirs();
	// }
	// for (Enumeration<? extends ZipEntry> entries = zip.entries();
	// entries.hasMoreElements();) {
	// ZipEntry entry = (ZipEntry) entries.nextElement();
	// String zipEntryName = entry.getName();
	// InputStream in = zip.getInputStream(entry);
	// String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*",
	// "/");
	// // 判断路径是否存在,不存在则创建文件路径
	// File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	// // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
	// if (new File(outPath).isDirectory()) {
	// continue;
	// }
	// // 输出文件路径信息
	// System.out.println(outPath);
	// FileOutputStream out = new FileOutputStream(outPath);
	// byte[] buf1 = new byte[1024];
	// int len;
	// while ((len = in.read(buf1)) > 0) {
	// out.write(buf1, 0, len);
	// }
	// in.close();
	// out.close();
	// }
	// } catch (Exception e) {
	// }
	// }

	public void uploadImgpack(String imagepath, LaunWidget launWidgetmmm, String otherpath) {
		StorePath storePath = null;

		List<File> list = FileTool.listFiles(imagepath);
		List<File> otherlist = FileTool.listFiles(otherpath);
		if (list != null) {
			FileInputStream inputStream;
			for (File imgfile : list) {
				try {
					inputStream = new FileInputStream(imgfile);
					storePath = fastFileStorageClient.uploadFile(inputStream, imgfile.length(),
							FilenameUtils.getExtension(imgfile.getName()), null);
					LaunWidgetFile launWidgetFile = new LaunWidgetFile();
					launWidgetFile.setId(IdUtlis.Id());
					launWidgetFile.setCreateDate(new Date());
					launWidgetFile.setFileName(imgfile.getName());
					launWidgetFile.setPath(storePath.getFullPath());
					launWidgetFile.setWidgetId(launWidgetmmm.getId());
					launWidgetFile.setType(3);
					// launWidgetFile.setUserId(null); 现在没有人
					fileMapper.insertSelective(launWidgetFile);
				} catch (FileNotFoundException e) {
					throw new DataStoreException(ErrorStatus.WIDGETPARSE_ERROR);
				}

			}
		}

		if (otherlist != null) {
			FileInputStream inputStream;
			for (File otherfile : otherlist) {
				try {
					inputStream = new FileInputStream(otherfile);
					storePath = fastFileStorageClient.uploadFile(inputStream, otherfile.length(),
							FilenameUtils.getExtension(otherfile.getName()), null);
					LaunWidgetFile launWidgetFile = new LaunWidgetFile();
					launWidgetFile.setId(IdUtlis.Id());
					launWidgetFile.setCreateDate(new Date());
					launWidgetFile.setFileName(otherfile.getName());
					launWidgetFile.setPath(storePath.getFullPath());
					launWidgetFile.setWidgetId(launWidgetmmm.getId());
					launWidgetFile.setType(4);
					// launWidgetFile.setUserId(null); 现在没有人
					fileMapper.insertSelective(launWidgetFile);
				} catch (FileNotFoundException e) {
					throw new DataStoreException(ErrorStatus.FASTDFS_ERROR);
				}

			}
		}
	}

	public void replaceunJsonParsing(String jsons, String imagepath, String otherpath, Long widgetId) {
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
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		// 得到json字符串，解析
		String json = sb.toString();
		// String json = jsons;
		// json的主题
		JSONObject jsonObject = JSON.parseObject(json);
		// 开始校验图片文件的合法性 start
		Map map = JsonUtils.JsonToMap(json);
		List list = new ArrayList();
		CheckWidgetImg.viewJsonTree(map, list);
		List imglist = FileTool.listFilename(imagepath);
		// List<String> intersection = (List<String>) list.stream().filter(item
		// -> imglist.contains(item)).collect(toList());
		boolean isInclud = CheckWidgetImg.isIncludeimg(list, imglist);
		if (!isInclud) {
			throw new DataStoreException(ErrorStatus.WIDGETIMG_ERROR);
		}
		// end
		String codeId = jsonObject.getString("codeId");
		Integer widgetCodeVersion = jsonObject.getInteger("widgetCodeVersion");
		String defaultSize = jsonObject.getString("defaultSize");
		boolean check = checkWidgetSize(defaultSize);
		if (!check) {
			throw new RuntimeException();
		}
		String previewImage = jsonObject.getString("previewImage");
		if ("".equals(previewImage)) {
			throw new DataStoreException(ErrorStatus.WIDGETPRIVEW_ERROR);
		}
		String name = jsonObject.getString("name");
		// 限制name不能大于20
		int checkSize = 20;
		if (name.length() > checkSize) {
			name = null;
		}
		String category = jsonObject.getString("category");
		String tags = jsonObject.getJSONArray("tags").toString();
		String description = jsonObject.getString("description");
		String properties = jsonObject.getJSONArray("properties").toString();
		if ("".equals(properties)) {
			throw new DataStoreException(ErrorStatus.WIDGETSIZE_ERROR);
		}
		// 更新widget类型信息，操作表（laun_widget_type） ，存在则直接返回主键ID，否则添加该类型并返回主键ID
		Long launWidgetTypeId = IdUtlis.Id();
		LaunWidgetType launWidgetType = new LaunWidgetType();
		launWidgetType.setTypeName(category);
		launWidgetType = launWidgetTypeMapper.selectOne(launWidgetType);
		if (launWidgetType == null) {
			launWidgetType = new LaunWidgetType();
			launWidgetType.setId(launWidgetTypeId);
			launWidgetType.setCreateDate(new Date());
			launWidgetType.setTypeName(category);
			launWidgetTypeMapper.insertSelective(launWidgetType);
		} else {
			launWidgetTypeId = launWidgetType.getId();
		}

		// 根据version更新laun_attribute表存在不做修改，否则添加
		LaunAttribute attr = new LaunAttribute();
		attr.setAttributeKey("version");
		Example example = new Example(LaunAttribute.class);
		example.createCriteria().andEqualTo("attributeKey", "version");
		example.setOrderByClause("attribute_key_index desc");
		List<LaunAttribute> attrversionlist = launAttributeMapper.selectByExample(example);
		// Integer maxvalue =
		// launAttributeMapper.getMaxValueByType(attr)==null?0:launAttributeMapper.getMaxValueByType(attr);
		attr.setAttributeValue(widgetCodeVersion.toString());
		List<LaunAttribute> attrlist = launAttributeMapper.select(attr);
		if (attrlist.size() == 0) {
			attr.setId(IdUtlis.Id());
			attr.setCreateDate(new Date());
			if (attrversionlist.size() == 0) {
				attr.setAttributeKeyIndex(1);
			} else {
				attr.setAttributeKeyIndex(attrversionlist.get(0).getAttributeKeyIndex() + 1);
			}
			attr.setAttributeStatus(1);
			launAttributeMapper.insertSelective(attr);
		}

		// 保存widget的主体
		LaunWidget launWidgetmmm = new LaunWidget();
		launWidgetmmm.setId(widgetId);
		// launWidgetmmm.setCreateDate(new Date());
		launWidgetmmm.setUpdateDate(new Date());
		launWidgetmmm.setVersion(widgetCodeVersion);
		launWidgetmmm.setDefaultSize(defaultSize);
		launWidgetmmm.setPrewImage(previewImage);
		launWidgetmmm.setName(name);
		launWidgetmmm.setDescription(description);
		launWidgetmmm.setCategory(launWidgetTypeId);
		launWidgetmmm.setCodeId(codeId);
		launWidgetmmm.setProperty(properties);
		launWidgetmmm.setTag(tags);
		launWidgetmmm.setType(0);
		launWidgetmmm.setParentId(0L);
		// TODO
		LaunUser user = UserUtlis.launUser();
		launWidgetmmm.setCreator(user.getId());
		launWidgetmmm.setCreateway(user.getChannelId() == null ? 1 : 2);
		// 基础widget默认全部
		launWidgetmmm.setChannelway(1);
		launWidgetManagerMapper.updateByPrimaryKeySelective(launWidgetmmm);

		// 上传widget压缩包中的图片，并保存到laun_widget_file表
		uploadImgpack(imagepath, launWidgetmmm, otherpath);

	}

	/**
	 * 解压json文件 jsons文件的路径
	 */
	@Override
	public LaunWidget unJsonParsing(String jsons, String imagepath, String otherpath) throws IOException {
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
			throw new DataStoreException(ErrorStatus.SYS_ERROR);
		}
		// 得到json字符串，解析
		String json = sb.toString();
		// 开始校验图片文件的合法性 start
		Map map = JsonUtils.JsonToMap(json);
		List<String> list = new ArrayList<String>();
		CheckWidgetImg.viewJsonTree(map, list);
		List<String> imglist = FileTool.listFilename(imagepath);
		// List<String> intersection = (List<String>) list.stream().filter(item
		// -> imglist.contains(item)).collect(toList());
		boolean isInclud = CheckWidgetImg.isIncludeimg(list, imglist);
		if (!isInclud) {
			throw new DataStoreException(ErrorStatus.WIDGETIMG_ERROR);
		}
		// end
		// String json = jsons;
		// json的主题
		JSONObject jsonObject = JSON.parseObject(json);
		String codeId = jsonObject.getString("codeId");
		Integer widgetCodeVersion = jsonObject.getInteger("widgetCodeVersion");
		String defaultSize = jsonObject.getString("defaultSize");
		boolean check = checkWidgetSize(defaultSize);
		if (!check) {
			throw new DataStoreException(ErrorStatus.WIDGETSIZE_ERROR);
		}
		String previewImage = jsonObject.getString("previewImage");
		if ("".equals(previewImage)) {
			throw new DataStoreException(ErrorStatus.WIDGETPRIVEW_ERROR);
		}
		String name = jsonObject.getString("name");
		// 限制name不能大于20
		int checkSize = 20;
		if (name.length() > checkSize) {
			name = null;
			throw new DataStoreException(ErrorStatus.WIDGETNAMEISNULL_ERROR);
		}
		String category = jsonObject.getString("category");
		String tags = jsonObject.getString("tags");
		String description = jsonObject.getString("description");
		String properties = jsonObject.getString("properties").toString();
		if ("".equals(properties)) {
			throw new DataStoreException(ErrorStatus.WIDGETSIZE_ERROR);
		}
		// 更新widget类型信息，操作表（laun_widget_type） ，存在则直接返回主键ID，否则添加该类型并返回主键ID
		Long launWidgetTypeId = IdUtlis.Id();
		LaunWidgetType launWidgetType = new LaunWidgetType();
		launWidgetType.setTypeName(category);
		launWidgetType = launWidgetTypeMapper.selectOne(launWidgetType);
		if (launWidgetType == null) {
			launWidgetType = new LaunWidgetType();
			launWidgetType.setId(launWidgetTypeId);
			launWidgetType.setCreateDate(new Date());
			launWidgetType.setTypeName(category);
			launWidgetTypeMapper.insertSelective(launWidgetType);
		} else {
			launWidgetTypeId = launWidgetType.getId();
		}

		// 根据version更新laun_attribute表存在不做修改，否则添加
		LaunAttribute attr = new LaunAttribute();
		attr.setAttributeKey("version");
		Example example = new Example(LaunAttribute.class);
		example.createCriteria().andEqualTo("attributeKey", "version");
		example.setOrderByClause("attribute_key_index desc");
		List<LaunAttribute> attrversionlist = launAttributeMapper.selectByExample(example);
		// Integer maxvalue = launAttributeMapper.getMaxValueByType(attr) ==
		// null ? 0
		// : launAttributeMapper.getMaxValueByType(attr);
		attr.setAttributeValue(widgetCodeVersion.toString());
		List<LaunAttribute> attrlist = launAttributeMapper.select(attr);
		if (attrlist.size() == 0) {
			attr.setId(IdUtlis.Id());
			attr.setCreateDate(new Date());
			if (attrversionlist.size() == 0) {
				attr.setAttributeKeyIndex(1);
			} else {
				attr.setAttributeKeyIndex(attrversionlist.get(0).getAttributeKeyIndex() + 1);
			}
			attr.setAttributeStatus(1);
			launAttributeMapper.insertSelective(attr);
		}

		// 保存widget的主体
		LaunWidget launWidgetmmm = new LaunWidget();
		launWidgetmmm.setId(IdUtlis.Id());
		launWidgetmmm.setCreateDate(new Date());
		launWidgetmmm.setVersion(widgetCodeVersion);
		launWidgetmmm.setDefaultSize(defaultSize);
		launWidgetmmm.setPrewImage(previewImage);
		launWidgetmmm.setName(name);
		launWidgetmmm.setDescription(description);
		launWidgetmmm.setCategory(launWidgetTypeId);
		launWidgetmmm.setCodeId(codeId);
		launWidgetmmm.setProperty(properties);
		launWidgetmmm.setTag(tags);
		// launWidgetmmm.setCodeId();
		launWidgetmmm.setType(0);
		launWidgetmmm.setParentId(0L);
		// TODO
		LaunUser user = UserUtlis.launUser();
		launWidgetmmm.setCreator(user.getId());
		launWidgetmmm.setCreateway(user.getChannelId() == null ? 1 : 2);
		launWidgetmmm.setChannelway(1);

		launWidgetManagerMapper.updateByPrimaryKey(launWidgetmmm);
		launWidgetManagerMapper.insertSelective(launWidgetmmm);

		// 上传widget压缩包中的图片，并保存到laun_widget_file表
		uploadImgpack(imagepath, launWidgetmmm, otherpath);

		return launWidgetmmm;
	}

	/**
	 * @description 判断values
	 * @author liudawei
	 * @since 2018年5月1日 下午4:57:36
	 * @param
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void addText(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entryt : obc.entrySet()) {
			if ("values".equals(entryt.getKey())) {
				Long preAddId = preAdd(entryt.getKey(), null, null, launWidget, 3, null);
				List<Map<String, String>> eList = (List<Map<String, String>>) entryt.getValue();
				for (Map<String, String> map : eList) {
					// Long childPreAddId = preAdd(null, null, preAddId,
					// launWidget, 1);
					Long dateId = IdUtlis.DateId();
					for (Map.Entry<String, String> vaMap : map.entrySet()) {
						preAdd(vaMap.getKey(), vaMap.getValue(), preAddId, launWidget, 0, dateId);
					}
				}
			} else {
				if ("default".equals((entryt.getKey()))) {
					String[] split = entryt.getValue().toString().split(",");
					if (split.length == 1) {
						preAdd(entryt.getKey(), entryt.getValue().toString(), null, launWidget, 0, null);
					} else {
						Long preAdd = preAdd(entryt.getKey(), null, null, launWidget, 1, null);
						List<String> deList = (List<String>) entryt.getValue();
						for (String string : deList) {
							preAdd(entryt.getKey(), string, preAdd, launWidget, 0, null);
						}
					}
				} else {
					preAdd(entryt.getKey(), entryt.getValue().toString(), null, launWidget, 0, null);
				}

			}
		}
	}

	/**
	 * @description default 类型
	 * @author liudawei
	 * @since 2018年5月1日 下午4:57:59
	 * @param
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void addValueList(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entryl : obc.entrySet()) {
			if ("default".equals(entryl.getKey())) {
				Long preAdd = preAdd(entryl.getKey(), null, null, launWidget, 1, null);
				Map<String, Object> deMap = (Map<String, Object>) entryl.getValue();
				for (Map.Entry<String, Object> vaMap : deMap.entrySet()) {
					if ("font_style".equals(vaMap.getKey())) {
						Long preAdd2 = preAdd(vaMap.getKey(), null, preAdd, launWidget, 2, null);
						List<String> fList = new ArrayList<>();
						fList = (List<String>) vaMap.getValue();
						for (String undfineSi : fList) {
							preAdd(null, undfineSi, preAdd2, launWidget, 0, null);
						}
					} else {
						String[] split = vaMap.getValue().toString().split(":");
						if (split.length > 1) {
							Long preAdd2 = preAdd(vaMap.getKey(), null, preAdd, launWidget, 1, null);
							Map<String, Object> smap = (Map<String, Object>) vaMap.getValue();
							for (Map.Entry<String, Object> vvaMap : smap.entrySet()) {
								preAdd(vvaMap.getKey(), vvaMap.getValue().toString(), preAdd2, launWidget, 0, null);
							}
						} else {
							preAdd(vaMap.getKey(), vaMap.getValue().toString(), preAdd, launWidget, 0, null);
						}
					}
				}
			} else {
				String[] split = entryl.getValue().toString().split(":");
				if (split.length == 1) {
					preAdd(entryl.getKey(), entryl.getValue().toString(), 0L, launWidget, 0, null);
				} else {
					Map<String, String> bgMap = (Map<String, String>) entryl.getValue();
					for (@SuppressWarnings("unused")
					Map.Entry<String, String> vvaMap : bgMap.entrySet()) {
						preAdd(entryl.getKey(), entryl.getValue().toString(), 0L, launWidget, 0, null);
					}
				}
			}
		}
	}

	/**
	 * @description 基本类型的保存
	 * @author liudawei
	 * @since 2018年5月1日 下午4:58:16
	 * @param
	 * @return void
	 */
	public void add(Map<String, Object> obc, LaunWidgetProperty launWidget) {
		for (Map.Entry<String, Object> entry1 : obc.entrySet()) {
			preAdd(entry1.getKey(), entry1.getValue().toString(), null, launWidget, 0, null);
		}
	}

	/**
	 * @description 保存方法
	 * @author liudawei
	 * @param dateId
	 * @since 2018年5月1日 下午4:58:35
	 * @param
	 * @return Long
	 */
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

	/**
	 * 查询widget列表
	 */
	@Override
	public PageInfo<LaunWidgetVo> findWidgetsList(Integer pageNum, Integer pageSize, String defaultSize, Long category,
			Integer version, String keyWord, Integer type) {
		PageHelper.startPage(pageNum, pageSize);
		if (type == 0) {
			List<LaunWidgetVo> widgetList = launWidgetManagerMapper.findWidgetsListByAd(defaultSize, category, version,
					keyWord);
			return new PageInfo<>(widgetList);
		} else {
			LaunUser user = UserUtlis.launUser();
			List<LaunWidgetVo> widgetList = launWidgetManagerMapper.findWidgetsListByCh(defaultSize, category, version,
					keyWord, user.getId(), user.getChannelId());
			return new PageInfo<>(widgetList);
		}
	}

	/**
	 * 查询widget列表
	 */
	@Override
	public PageInfo<LaunWidgetVo> findWidgetsPullList(Integer pageNum, Integer pageSize, String defaultSize,
			Long category, Integer version, String keyWord, Integer type, String channels, Integer channelnum) {
		PageHelper.startPage(pageNum, pageSize);
		if (type == 0) {
			List<LaunWidgetVo> widgetList = launWidgetManagerMapper.findWidgetsPullListByAd(defaultSize, category,
					version, keyWord, channels, channelnum);
			return new PageInfo<>(widgetList);
		} else {
			LaunUser user = UserUtlis.launUser();
			List<LaunWidgetVo> widgetList = launWidgetManagerMapper.findWidgetsPullListByCh(defaultSize, category,
					version, keyWord, user.getId(), user.getChannelId());
			return new PageInfo<>(widgetList);
		}
	}

	/**
	 * 查询widget类型列表
	 */
	@Override
	public PageInfo<LaunWidgetTypeVo> findWidgetTypeList() {
		List<LaunWidgetTypeVo> widgetTypeList = launWidgetManagerMapper.findWidgetTypeList();
		return new PageInfo<>(widgetTypeList);
	}

	/**
	 * 根据主键删除widget类型
	 */
	@Override
	public String deleteWidgetType(Long id) {
		String message = "";
		int i = launWidgetManagerMapper.deleteWidgetType(id);
		if (i == 1) {
			message = "widget类型删除成功";
		} else {
			message = "widget类型删除失败";
		}
		return message;
	}

	/**
	 * 根据主键删除widget
	 */
	@Override
	public String deleteWidgetById(Long id) {
		String message = "";
		int i = launWidgetManagerMapper.deleteWidgetById(id);
		if (i == 1) {
			message = "widget删除成功";
		} else {
			message = "widget删除失败";
		}
		return message;
	}

	/**
	 * 查询widget尺寸列表
	 */
	@Override
	public PageInfo<String> findWidgetDefaultSize() {
		List<String> widgetSizeList = launWidgetManagerMapper.findWidgetDefaultSize();
		return new PageInfo<>(widgetSizeList);
	}

	/**
	 * 查询widget类型列表
	 */
	@Override
	public PageInfo<LaunWidgetType> findWidgetCategory() {
		List<LaunWidgetType> widgetCategoryList = launWidgetManagerMapper.findWidgetCategory();
		return new PageInfo<>(widgetCategoryList);
	}

	@Override
	public List<LaunAttributeVo> findWidgetVersion() {
		List<LaunAttributeVo> widgetVersionList = launWidgetManagerMapper.findWidgetVersion();
		return widgetVersionList;
	}

	/**
	 * 在上传之前先保存到本地一份
	 */
	// @Override
	// public void saveToLocal(MultipartFile unFile) {
	// String originalFilename = unFile.getOriginalFilename();
	// CommonsMultipartFile cf = (CommonsMultipartFile) unFile;
	// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
	// File file = fi.getStoreLocation();
	// String basePath = "D:/widgetLoad/" + originalFilename;
	// File localFile = new File(basePath);
	// if (localFile.exists()) {
	// localFile.delete();
	// }
	// try {
	// InputStream is = new FileInputStream(file);
	// OutputStream os = new FileOutputStream(localFile);
	// byte buffer[] = new byte[1024];
	// int cnt = 0;
	// while ((cnt = is.read(buffer)) > 0) {
	// os.write(buffer, 0, cnt);
	// }
	// os.close();
	// is.close();
	// } catch (Exception e) {
	// }
	// }

	@Override
	public String getWidgetDetail(Long widgetId) throws Exception {
		LaunWidget widget = new LaunWidget();
		widget.setId(widgetId);
		List<LaunWidget> widgetlist = launWidgetManagerMapper.select(widget);
		String json = "";
		json = widgetToJson.widget2Jsonnew(widgetlist);
		return json;
	}

	@Override
	public List<LaunWidgetFile> findWidgetfileById(Long widgetId) {
		LaunWidgetFile widgetfile = new LaunWidgetFile();
		widgetfile.setWidgetId(widgetId);
		List<LaunWidgetFile> filelist = launWidgetFileMapper.select(widgetfile);
		return filelist;
	}

	/**
	 * 打包widget，用于创建变体widget时
	 * 
	 * @param widget
	 * @return 所在文件服务器的路径
	 */
	public String widgetpackage(LaunWidget widget) {
		// 生成随机的文件夹名
		String descDir = env.getProperty("system.conf.widgetTempPath") + IdUtlis.Id();
		File file = new File(descDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		StorePath storePath = null;
		byte[] bytes = null;
		FileOutputStream stream = null;
		LaunWidgetFile widgetfile = new LaunWidgetFile();
		widgetfile.setWidgetId(widget.getId());
		List<LaunWidgetFile> filelist = launWidgetFileMapper.select(widgetfile);
		// 处理文件
		if (filelist != null) {
			try {
				for (LaunWidgetFile wf : filelist) {
					// 图片
					if (wf.getType() == 3) {
						storePath = StorePath.praseFromUrl(widgetfile.getPath());
						bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
								new DownloadByteArray());
						stream = new FileOutputStream(descDir + "/image/" + widgetfile.getFileName());
						stream.write(bytes);
						// other
					} else if (wf.getType() == 4) {
						storePath = StorePath.praseFromUrl(widgetfile.getPath());
						bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
								new DownloadByteArray());
						stream = new FileOutputStream(descDir + "/lib/" + widgetfile.getFileName());
						stream.write(bytes);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 处理json所在的文件

		return "";
	}

	@Override
	public List<LaunWidgetChannel> findWidgetChannelById(Long widgetId) {
		LaunWidgetChannel widgetchannel = new LaunWidgetChannel();
		widgetchannel.setWidgetId(widgetId);
		List<LaunWidgetChannel> list = launWidgetChannelMapper.select(widgetchannel);
		return list;
	}

	@Override
	public String findWidgetUseNum(Long widgetId) {
		String returnStr = "";
		List<String> findWidgetUseNum = launWidgetManagerMapper.findWidgetUseNum(widgetId);
		for (String themeTitle : findWidgetUseNum) {
			returnStr += (themeTitle + ",");
		}
		if (findWidgetUseNum.size() > 0) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	public boolean checkWidgetSize(String size) {
		String rex = "\\d{1,10}\\*\\d{1,10}";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(size);
		boolean check = m.matches();
		return check;
	}
}
