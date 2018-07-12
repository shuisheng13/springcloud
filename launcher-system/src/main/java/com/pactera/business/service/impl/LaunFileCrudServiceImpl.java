package com.pactera.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.service.LaunFileCrudService;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LaunFileCrudServiceImpl implements LaunFileCrudService {

	@Autowired
	public FastFileStorageClient fastFileStorageClient;

	@Override
	public String fileUpload(MultipartFile file) {
		String returnPath = null;
		String originalFilename = file.getOriginalFilename();
		String fileName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		try {
			returnPath = upload(file.getInputStream(), file.getSize(), fileName, null);
		} catch (IOException e) {
			log.error("文件上传异常{}", e);
			throw new DataStoreException(ErrorStatus.FASTDFS_ERROR);
		}
		return returnPath;
	}

	@Override
	public String downLoadFile(String path, String goalFilePath) {
		// String filename = path.substring(path.lastIndexOf("/") + 1);
		// goalFilePath = goalFilePath + "/" + filename;

		StorePath storePath = StorePath.praseFromUrl(path);
		String group = storePath.getGroup();
		String pathUrl = storePath.getPath();
		try {
			byte[] bytes = fastFileStorageClient.downloadFile(group, pathUrl, new DownloadByteArray());
			@SuppressWarnings("resource")
			FileOutputStream stream = new FileOutputStream(goalFilePath);
			stream.write(bytes);
		} catch (Exception e) {
			log.error("文件下载异常{}", e);
			throw new DataStoreException(ErrorStatus.FASTDFS_ERROR);
		}
		return goalFilePath;
	}

	@Override
	public void downLoadFiles(String[] path, String goalFilePath) {
		for (String string : path) {
			downLoadFile(string, goalFilePath);
		}
	}

	@Override
	public String fileUpload(String filePath, String fileName) {
		File file = new File(filePath);
		InputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
			return upload(fileInputStream, file.length(), fileName, null);
		} catch (FileNotFoundException e) {
			log.error("流转换异常---{}", e);
		}
		return null;
	}

	/**
	 * 文件上传
	 * 
	 * @author LL
	 * @date 2018年5月9日 上午10:55:50
	 * @param InputStream
	 * @param fileSize
	 * @param fileName
	 * @return String
	 */
	@Override
	public String upload(InputStream inputStream, Long fileSize, String fileName, Set<MateData> metaDataSet) {
		StorePath storePath = fastFileStorageClient.uploadFile(inputStream, fileSize, fileName, metaDataSet);
		return storePath.getFullPath();
	}
}
