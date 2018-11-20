package com.techwells.applicationMarket.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techwells.applicationMarket.util.ApplicationMarketConstants;

@RestController
public class UploadController {
	
	
	@RequestMapping("/file/upload1")
	public Object upload1(HttpServletRequest request,@RequestParam(value="file")MultipartFile file){
		
		Long s=System.currentTimeMillis();
		
		String fileName=System.currentTimeMillis()+file.getOriginalFilename();
		String path=ApplicationMarketConstants.UPLOAD_PATH+"test/";
		File targetFile=new File(path,fileName);
		FileChannel inchannel=null;
		FileChannel outChannel=null;
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		
		try {
			FileInputStream inputStream=(FileInputStream) file.getInputStream();
			inchannel=inputStream.getChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream outputStream=new FileOutputStream(targetFile);
			outChannel=outputStream.getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			inchannel.transferTo(0, inchannel.size(), outChannel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (System.currentTimeMillis()-s);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
