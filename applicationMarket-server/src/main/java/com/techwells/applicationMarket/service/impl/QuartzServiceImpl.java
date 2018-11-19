package com.techwells.applicationMarket.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.WalletDetailMapper;
import com.techwells.applicationMarket.domain.Wallet;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.service.QuartzService;
import com.techwells.applicationMarket.util.DateUtil;
import com.techwells.applicationMarket.util.moac.MoacUtils;
import com.techwells.applicationMarket.util.moac.TransactionDetail;
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
			logger.error("定时清空downloadCount_add这个字段异常");
			throw new RuntimeException();
		}
		
		logger.info("更新成功");
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
			System.out.println(detail.getDetailId()+"------");
			Map<String, Object> map=null;
			try {
				map=SwtcUtils.getTransaction(detail.getHash());//获取交易记录
			} catch (Exception e) {
				logger.error("设置区块高度异常：",e);
			} 
			
			if (map==null) {
				continue;
			}
			
			//如果交易失败
			if ((Boolean)map.get("success")==false) {
				continue;
			}
			
			Map<String, Object> map2=(Map<String, Object>) map.get("transaction");  //获取交易记录
			
			//这里由于程序执行太快，没有不能及时返回区块的高度
			String block=SwtcUtils.formatFloatNumber((Double)map2.get("ledger"));   //获取区块的高度
			
			detail.setBlock(block);
			detailMapper.updateByPrimaryKeySelective(detail);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getMoacTrasactionDetail() {
		List<WalletDetail> wallets=detailMapper.selectWalletDetailByNumber();
		
		//遍历
		for (WalletDetail detail : wallets) {
			String hash=detail.getHash();  //获取hash值
			try {
				//墨客转账是在前台完成的，这里只需要用hash值查询转账的信息即可
				TransactionDetail transactionDetail=MoacUtils.getTransactionDetail(hash);
				detail.setNumber(System.currentTimeMillis()+"");
				detail.setCreateDate(new Date());  //设置创建日期
				detail.setBlock(transactionDetail.getBlockNumber());   //设置区块信息
				detail.setFromAddress(transactionDetail.getFrom());  //转账方的钱包地址
				detail.setToAddress(transactionDetail.getTo());
				detail.setFee((Double)(Long.parseLong(transactionDetail.getGas())/1000000000000000000.0*Long.parseLong(transactionDetail.getGasPrice())));   //旷工费用
				detail.setRemark("任务奖励发放");   //设置备注为转账
				Date trsdate=new Date();   //交易日期
				detail.setTransactionDate(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));  //交易时间
//				detail.setActivated(1);  //设置钱包的类型
				detail.setMoney("-"+transactionDetail.getValue());
//				detail.setWalletId(wallet.getWalletId());
//				detail.setHash(hash);
				int count=detailMapper.updateByPrimaryKeySelective(detail);
				if (count==0) {
					throw new RuntimeException();
				}
			} catch (Throwable e) {
				logger.error("获取moac钱包信息异常",e);
			}
		}
		
		
	}
	
}
