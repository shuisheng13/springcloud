package com.pactera.business.service;

import java.io.InputStream;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.MateData;

/**
 * fastDFS文件处理
 * 
 * @since:2018年5月9日 上午10:20:33
 */
public interface LaunFileCrudService {

	/**
	 * 文件上传
	 * 
	 * @author LL
	 * @date 2018年5月9日 上午10:46:02
	 * @param MultipartFile
	 * @return String
	 */
	String fileUpload(MultipartFile file);

	/**
	 * 文件上传
	 * 
	 * @author LL
	 * @date 2018年5月9日 上午10:45:35
	 * @param filePath文件路径
	 * @return String
	 */
	String fileUpload(String filePath, String fileName);

	/**
	 * 文件下载
	 * 
	 * @author LL
	 * @date 2018年5月9日 上午10:09:04
	 * @param groupName组名
	 * @param path下载文件路径
	 * @param goalFilePath下载地址
	 * @return 文件存放路径
	 */
	String downLoadFile(String path, String goalFilePath);

	/**
	 * 多文件下载
	 * 
	 * @author LL
	 * @param groupName组名
	 * @param path下载文件路径
	 * @param goalFilePath下载地址
	 * @return String
	 */
	void downLoadFiles(String[] path, String goalFilePath);

	/**
	 * 文件上传
	 * @author LL
	 * @date 2018年5月30日 下午6:47:02
	 * @param 
	 * @return String
	 */
	String upload(InputStream inputStream, Long fileSize, String fileName, Set<MateData> metaDataSet);
}
