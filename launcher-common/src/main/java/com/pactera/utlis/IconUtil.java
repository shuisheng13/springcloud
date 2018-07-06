package com.pactera.utlis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @description:解析apk工具类
 * @author:Scott
 * @since:2018年4月28日 下午3:53:19
 */
public class IconUtil {
	
	/**
	 *  从指定的apk文件里获取指定file的流
	 * @param apkpath
	 * @param fileName
	 * @return
	 */
	public static InputStream extractFileFromApk(String apkpath, String fileName) {
		try {
			@SuppressWarnings("resource")
			ZipFile zFile = new ZipFile(apkpath);
			ZipEntry entry = zFile.getEntry(fileName);
			entry.getComment();
			entry.getCompressedSize();
			entry.getCrc();
			entry.isDirectory();
			entry.getSize();
			entry.getMethod();
			InputStream stream = zFile.getInputStream(entry);
			return stream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void extractFileFromApk(String apkpath, String fileName, String outputPath) throws Exception {
		InputStream is = extractFileFromApk(apkpath, fileName);

		File file = new File(outputPath);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 1024);
		byte[] b = new byte[1024];
		BufferedInputStream bis = new BufferedInputStream(is, 1024);
		while (bis.read(b) != -1) {
			bos.write(b);
		}
		bos.flush();
		is.close();
		bis.close();
		bos.close();
	}
}
