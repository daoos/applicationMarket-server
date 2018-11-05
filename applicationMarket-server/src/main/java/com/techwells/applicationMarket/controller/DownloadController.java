package com.techwells.applicationMarket.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件下载的controller
 * 
 * @author 陈加兵
 *
 */
@RestController
public class DownloadController {
	
	@RequestMapping("/download/downloadFile")
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws IOException {
		String fileName = "公司管理系统.rar";
		// 获取下载文件的路径，在文件中的真实路径
		String path = "C:\\images";
		// 下载文件的全路径
		File file = new File(path + File.separator + fileName);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(fileName.getBytes("UTF-8"),
				"iso-8859-1");
		// 通知浏览器以attachment（下载方式）
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
