package com.techwells.applicationMarket.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class QuartRun {
	
	@Resource
	private QuartzService quartzService;
	
	private Logger logger=Logger.getLogger(QuartRun.class);
	
	/**
	 * 每24小时执行的任务
	 */
	public void run24(){
		logger.info("每24小时定时调度开始");
		quartzService.resetDownloadCountAdd(); 
//		//设置区块的高度
//		quartzService.setBlock();
	}
	
	/**
	 * 每秒执行的任务
	 */
	public void run(){
//		logger.info("每秒执行");
//		quartzService.setBlock();
	}
}
