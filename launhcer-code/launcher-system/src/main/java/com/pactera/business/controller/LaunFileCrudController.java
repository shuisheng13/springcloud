package com.pactera.business.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.business.service.LaunFileCrudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description ="文件管理")
public class LaunFileCrudController {

	@Autowired
	private LaunFileCrudService storeFileCrudService;

	@PostMapping("fileUpload")
	@ApiOperation("文件上传")
	@ApiImplicitParam(name = "file", value = "接受文件的参数名")
	public ResponseEntity<String> fileUpload(MultipartFile file) {
		String fileUploadName = storeFileCrudService.fileUpload(file);
		return ResponseEntity.ok(fileUploadName);
	}

	@ApiOperation("文件下载")
	@GetMapping("dowloadFile")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "filePath", value = "文件路径完整路径"), 
		@ApiImplicitParam(name = "fileName", value = "文件名字") })
	public void dowloadFile(HttpServletResponse response, HttpServletRequest request, String filePath, String fileName) {
		InputStream inputStream = null;
		OutputStream out = null;
		try {
			fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachement;filename=" + fileName);
			response.setBufferSize(1024);
			URL url = new URL(filePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 得到输入流
			inputStream = conn.getInputStream();
			out = response.getOutputStream();
			// 写文件
			int b;
			while ((b = inputStream.read()) != -1) {
				out.write(b);
			}
		} catch (Exception e) {
			throw new RuntimeException("文件下载异常");
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				throw new RuntimeException("IO流关闭异常");
			}

		}

	}
}
