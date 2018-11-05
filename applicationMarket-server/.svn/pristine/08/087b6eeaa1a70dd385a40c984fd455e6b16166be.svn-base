package com.techwells.applicationMarket.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.WalletDetailMapper;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.service.QuartzService;
import com.techwells.applicationMarket.util.swtc.SwtcUtils;

/**
 * 定时调度任务的实现类
 * @author Administrator
 */
@Service
public class QuartzServiceImpl implements QuartzService {
	@Resource
	private AppMapper appMapper;
	
	@Resource
	private WalletDetailMapper detailMapper;
	
	private Logger logger=Logger.getLogger(QuartzServiceImpl.class);
	
	/**
	 * 每24小时清空downloadCount_add这个字段的值
	 */
	public void resetDownloadCountAdd(){
		int total=appMapper.countAppTotal();
		int count=appMapper.updateDownloadCountAdd();
		//抛出异常
		if (count!=total) {
			throw new RuntimeException();
		}
		
		logger.debug("更新成功");
	}

	@Override
	public void ground() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void setBlock() {
		List<WalletDetail> details=detailMapper.selectListByBlock();
		
		//遍历
		for (WalletDetail detail : details) {
			Map<String, Object> map=new HashMap<String, Object>();
			try {
				map=SwtcUtils.getTransaction(detail.getHash());//获取交易记录
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			Map<String, Object> map2=(Map<String, Object>) map.get("transaction");  //获取交易记录
			//这里由于程序执行太快，没有不能及时返回区块的高度
			String block=SwtcUtils.formatFloatNumber((Double)map2.get("ledger"));   //获取区块的高度
			detail.setBlock(block);
			detailMapper.updateByPrimaryKeySelective(detail);
		}
	}
	
}
