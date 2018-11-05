package com.techwells.applicationMarket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class QuartRun {
	
	@Resource
	private QuartzService quartzService;
	
	/**
	 * 每秒执行的任务
	 */
	public void run(){
//		System.out.println("定时调度执行");
//		//设置区块的高度
//		quartzService.setBlock();
	}
}
