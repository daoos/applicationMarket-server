package com.techwells.applicationMarket.service.impl;

import java.math.BigDecimal;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.text.ChangedCharSetException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.dao.SystemConfigMapper;
import com.techwells.applicationMarket.dao.WalletDetailMapper;
import com.techwells.applicationMarket.dao.WalletMapper;
import com.techwells.applicationMarket.domain.SystemConfig;
import com.techwells.applicationMarket.domain.Wallet;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.domain.rs.WalletDetailVos;
import com.techwells.applicationMarket.service.WalletService;
import com.techwells.applicationMarket.util.DateUtil;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.swtc.Amount;
import com.techwells.applicationMarket.util.swtc.PayNeedData;
import com.techwells.applicationMarket.util.swtc.PayObject;
import com.techwells.applicationMarket.util.swtc.SwtcUtils;

/**
 * 钱包的业务层的实现类
 * @author 陈加兵
 */
@Service
public class WalletServiceImpl implements WalletService{
	
	@Resource
	private WalletMapper walletMapper;
	
	@Resource
	private WalletDetailMapper detailMapper;
	
	@Resource
	private SystemConfigMapper configMapper;
	
	@Override
	public Object addWallet(Wallet wallet) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//查询指定类型的钱包是否存在，只能导入一个
		Wallet wallet2=walletMapper.selectWallet(wallet.getUserId(), wallet.getType());
		
		if (wallet2!=null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("只能导入一个同种类型的钱包");
			return resultInfo;
		}
		
		
		//根据指定的钱包类型查询余额
		
		//如果是墨客
		if (wallet.getType().equals(1)) {
  			
		}else if (wallet.getType().equals(2)) {  //井通钱包
			//根据钱包的地址查询余额
			Map<String, Object> map=SwtcUtils.getBalance(wallet.getAddress());
			
			//如果查询失败，直接返回错误信息
			if ((Boolean)map.get("success")==false) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("查询失败，请检查钱包地址是否正确");
				return resultInfo;
			}
			
			
			List<Map<String, Object>> balances=(List<Map<String, Object>>) map.get("balances");  //获取余额数组，其中包含多种类型的余额
			String balanceSwt=null;   //井通的余额
			//遍历数组
			for (Map<String, Object> m : balances) {
				if (m.get("currency").equals("SWT")) {   //如果其中的类型为SWT，那么就是井通的
					balanceSwt=(String) m.get("value");
					break;  //跳出即可，不必循环了
				}
			}
			//查询成功，设置信息即可
			wallet.setBalance(balanceSwt);
		}
		
		//插入钱包
		int count=walletMapper.insertSelective(wallet);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	//1、由于转账不只是在这里转账，因此每次查询的时候需要同步更新钱包的余额
	@Override
	public Object getWalletById(Integer walletId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Wallet wallet=walletMapper.selectByPrimaryKey(walletId);
		
		if (wallet==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该钱包不存在");
			return resultInfo;
		}
		
		
		//获取此时的钱包余额
		//如果是墨客
		if (wallet.getType().equals(1)) {

		} else if (wallet.getType().equals(2)) { // 井通钱包
			// 根据钱包的地址查询余额
			Map<String, Object> map = SwtcUtils.getBalance(wallet.getAddress());

			// 如果查询失败，直接返回错误信息
			if ((Boolean) map.get("success") == false) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("查询失败，请检查钱包地址是否正确");
				return resultInfo;
			}

			List<Map<String, Object>> balances = (List<Map<String, Object>>) map
					.get("balances"); // 获取余额数组，其中包含多种类型的余额
			String balanceSwt = null; // 井通的余额
			// 遍历数组
			for (Map<String, Object> m : balances) {
				if (m.get("currency").equals("SWT")) { // 如果其中的类型为SWT，那么就是井通的
					balanceSwt = (String) m.get("value");
					break; // 跳出即可，不必循环了
				}
			}
			// 查询成功，设置信息即可
			wallet.setBalance(balanceSwt);
		}
		
		//更新钱包余额
		int count=walletMapper.updateByPrimaryKeySelective(wallet);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
		
		//获取明细，最新的四条
		List<WalletDetail> details=detailMapper.selectListLimit(walletId, 4);
		wallet.setDetails(details);
		wallet.setSecret("***********");
		wallet.setPassword("***********");
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(wallet);
		return resultInfo;
	}

	@Override
	public Object modifyWallet(Wallet wallet) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=walletMapper.updateByPrimaryKeySelective(wallet);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object deleteWallet(Integer walletId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=walletMapper.deleteByPrimaryKey(walletId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getWalletList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Wallet getWallet(Integer walletId) throws Exception {
		return walletMapper.selectByPrimaryKey(walletId);
	}

	/**
	 * 1、需要判断当前重置的钱包是否只有一个
	 */
	@Override
	public Object resetWallet(Wallet wallet) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		Wallet wallet2=walletMapper.selectByPrimaryKey(wallet.getWalletId());  //根据Id查询钱包信息
		
		//如果重置的钱包类型和当前类型不同，那么需要查询这个类型的钱包是否存在了，不能重复
//		if (!wallet2.getType().equals(wallet2.getType())) {
//			Wallet wallet3=walletMapper.selectWallet(wallet2.getUserId(), wallet2.getType());
//			if (wallet3!=null) {
//				resultInfo.setCode("-1");
//				resultInfo.setMessage("需要重置的钱包类型已经存在，请先移除");
//				return resultInfo;
//			}
//		}
		
		//查询账户余额
		//如果是墨客
		if (wallet.getType().equals(1)) {

		} else if (wallet.getType().equals(2)) { // 井通钱包
			// 根据钱包的地址查询余额
			Map<String, Object> map = SwtcUtils.getBalance(wallet.getAddress());

			// 如果查询失败，直接返回错误信息
			if ((Boolean) map.get("success") == false) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("查询失败，请检查钱包地址是否正确");
				return resultInfo;
			}

			List<Map<String, Object>> balances = (List<Map<String, Object>>) map
					.get("balances"); // 获取余额数组，其中包含多种类型的余额
			String balanceSwt = null; // 井通的余额
			// 遍历数组
			for (Map<String, Object> m : balances) {
				if (m.get("currency").equals("SWT")) { // 如果其中的类型为SWT，那么就是井通的
					balanceSwt = (String) m.get("value");
					break; // 跳出即可，不必循环了
				}
			}
			// 查询成功，设置信息即可
			wallet.setBalance(balanceSwt);
		}
		
		
		//修改钱包信息
		int count=walletMapper.updateByPrimaryKeySelective(wallet);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("重置失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("重置成功");
		return resultInfo;
	}

	@Override
	public Object transfer(WalletDetail detail, String pwd) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//检测是否允许转账
		SystemConfig config=configMapper.selectByPrimaryKey(1);
		if (config.getIsRansfer().equals(2)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("系统不允许转账");
			return resultInfo;
		}
		
		//检查密码是否正确
		Wallet wallet=walletMapper.selectByPrimaryKey(detail.getWalletId());
		if (wallet==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该钱包不存在");
			return resultInfo;
		}
		
		//密码不正确
		if (!pwd.equals(wallet.getPassword())) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不正确");
			return resultInfo;
		}
		
		//转账
		if (wallet.getType().equals(1)) {   //Moac钱包转账
			
		}else if (wallet.getType().equals(2)) {   //井通钱包
			PayNeedData data=new PayNeedData();
			String orderNum=System.currentTimeMillis()+"";
			data.setClient_id(orderNum);  //订单号
			data.setSecret(wallet.getSecret());  //秘钥
			
			PayObject payObject=new PayObject();
			payObject.setDestination(detail.getToAddress());   //目标账号
			payObject.setSource(wallet.getAddress());  //发起账号
			
			//转账的金额
			Amount amount=new Amount();
			amount.setValue(detail.getMoney());
			
			payObject.setAmount(amount);
			
			data.setPayment(payObject);
			
			
			//调用转账接口
			Map<String, Object> map=SwtcUtils.pay(data, wallet.getAddress());
			
			//转账失败
			if (map==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("转账失败");
				return resultInfo;
			}
			
			if (!(Boolean)map.get("success")==true||!(map.get("result").equals("tesSUCCESS"))) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("转账失败");
				return resultInfo;
			}
			
			//转账成功
			String hash=(String) map.get("hash");  //获取hash值，用于查询交易信息
			
			//执行查询信息的接口
			Map<String, Object> infoMap=SwtcUtils.getTransaction(hash); 
			
			if (infoMap==null||(Boolean)infoMap.get("success")==false) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("转账失败");
				return resultInfo;
			}
			
			Map<String, Object> map2=(Map<String, Object>) infoMap.get("transaction");  //获取交易记录
			System.err.println(infoMap);
//			
			//这里由于程序执行太快，没有不能及时返回区块的高度
//			String block=SwtcUtils.formatFloatNumber((Double)map2.get("ledger"));   //获取区块的高度
			
			//封装信息
			detail.setHash(hash);  //hash的值
			detail.setNumber(orderNum);
			detail.setCreateDate(new Date());  //设置创建日期
			detail.setBlock(null);   //设置区块信息
			detail.setFromAddress(wallet.getAddress());  //转账方的钱包地址
			detail.setFee((Double)map.get("fee"));   //旷工费用
			detail.setRemark("转账");   //设置备注为转账
			Date trsdate=new Date(Long.parseLong(SwtcUtils.formatFloatNumber((Double)map2.get("date"))));   //交易日期
			detail.setTransactionDate(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));  //交易时间
			detail.setUrl(SwtcUtils.HTTP+"v2/transactions/"+hash);   //设置公开查账的地址
			detail.setActivated(wallet.getType());  //设置钱包的类型
			detail.setMoney("-"+detail.getMoney());
		}
		
		//插入交易明细
		int count=detailMapper.insertSelective(detail);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("转账失败");
			return resultInfo;
		}
		
		
//		//计算钱包的余额
//		BigDecimal walletDecimal=new BigDecimal(Double.parseDouble(wallet.getBalance()));  //钱包的余额
//		BigDecimal traferMoney=new BigDecimal(Double.parseDouble(detail.getMoney()));   //转账的金额
//		String balance=walletDecimal.subtract(traferMoney).toPlainString();
//		wallet.setBalance(balance);
//		
//		
//		//修改钱包
//		int count1=walletMapper.updateByPrimaryKeySelective(wallet);
//		if (count1==0) {
//			throw new RuntimeException();
//		}
		
		resultInfo.setMessage("转账成功");
		return resultInfo;
	}

	@Override
	public Object getDetail(Integer detailId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		WalletDetail detail=detailMapper.selectByPrimaryKey(detailId);
		
		if (detail==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该明细不存在");
			return resultInfo;
		}
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(detail);
		return resultInfo;
	}
	
	@Override
	public Object getWalletDetail(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=detailMapper.countTotalWalletDetail(pagingTool);
		
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		//获取分页数据中的不重复的年月，现在这个列表中只有年月的信息
		List<WalletDetailVos> detailVoList=detailMapper.selectDetailVosList(pagingTool);
		
		//获取钱包明细列表                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		List<WalletDetail> details=detailMapper.selectWalletDetails(pagingTool);
		
		//获取分页数据的月份，不能重复
		
		int count=0;
		
		//遍历detailVoList，根据年月的信息存放数据
		for (WalletDetailVos detailVos : detailVoList) {
			List<WalletDetail> walletDetails=new ArrayList<WalletDetail>();  //创建钱包明细，用来存储同一个年月的信息
			BigDecimal pay=new BigDecimal(0);  //支出
			BigDecimal income=new BigDecimal(0); //收入
			//遍历分页获取的明细信息
			for (WalletDetail walletDetail : details) {
				String yearMonth=DateUtil.getDateForFormat(walletDetail.getCreateDate()).substring(0,7);  //将日期转化成字符串
				//如果年月相同，那么就是一个类别的
				if (yearMonth.equals(detailVos.getYearMonth())) {
					walletDetails.add(walletDetail);  //添加到一类中的
					
					//转账属于支出
					if ("转账".equals(walletDetail.getRemark())) {
						pay=pay.add(new BigDecimal(walletDetail.getMoney()));  //金额相加
					}else if ("任务奖励".equals(walletDetail.getRemark())) {
						income=income.add(new BigDecimal(walletDetail.getMoney()));
					}
				}
			}
			//此时的walletDetails中存放的就是分页返回的同一个年月的全部明细了
			detailVos.setDetails(walletDetails);
			//获取收入和支出，收入是任务奖励的，支出是转账的
			detailVos.setPay(pay);
			detailVos.setIncome(income);
			
			//验证是否需要显示日期和收入支出,需要查询这里的walletDetails中的第一条明细是否是这个月的第一条
			//pagingTool.getStartNum()等于0 表示第一页，第一页肯定是要显示的
			if (count==0&&pagingTool.getStartNum()>0) {
				//根据年月获取第一条记录
				WalletDetail detail=detailMapper.selectWalletDetail(detailVos.getYearMonth(), 1);
				//如果id相同，表示此时分页查询的第一条数据就是本月分类中的第一条数据，那么就不需要显示了
				if (detail.getDetailId().equals(walletDetails.get(0).getDetailId())) {
					detailVos.setIsShow(0);  //此时不显示日期
				}
			}
			count++;  //+1
		}
		
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(detailVoList);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	/**
	 * 1、每个钱包需要重新获取余额，这样才能和外界的余额同步，否则将会余额不符
	 */
	@Override
	public Object getWalletManage(Integer userId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取钱包列表
		List<Wallet> wallets=walletMapper.selectWallets(userId);
		
		//遍历钱包查询余额
		for (Wallet wallet : wallets) {
			//根据不同的钱包查询余额
			if (wallet.getType().equals(1)) {  //Moac钱包
				
			
			
			
			
			}else if (wallet.getType().equals(2)) {   //井通钱包
				
				// 根据钱包的地址查询余额
				Map<String, Object> map = SwtcUtils.getBalance(wallet.getAddress());

				// 如果查询失败，直接返回错误信息
				if ((Boolean) map.get("success") == false) {
					resultInfo.setCode("-1");
					resultInfo.setMessage("查询失败，请检查钱包地址是否正确");
					return resultInfo;
				}

				List<Map<String, Object>> balances = (List<Map<String, Object>>) map
						.get("balances"); // 获取余额数组，其中包含多种类型的余额
				String balanceSwt = null; // 井通的余额
				// 遍历数组
				for (Map<String, Object> m : balances) {
					if (m.get("currency").equals("SWT")) { // 如果其中的类型为SWT，那么就是井通的
						balanceSwt = (String) m.get("value");
						break; // 跳出即可，不必循环了
					}
				}
				// 查询成功，设置信息即可
				wallet.setBalance(balanceSwt);
			}
			
			//更新钱包余额
			int count=walletMapper.updateByPrimaryKeySelective(wallet); 
			
			//更新失败直接抛出异常,否则事务不能回滚
			if (count==0) {
				throw new RuntimeException();
			}
			
		}
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(wallets);
		return resultInfo;
	}

	@Override
	public Object change(Integer userId, Integer walletId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//根据用户Id查询用户钱包信息
		List<Wallet> wallets=walletMapper.selectWallets(userId);
		
		if (walletId==null||wallets.size()==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户还没有导入钱包");
			return resultInfo;
		}
		
		//如果只有一个钱包，并且这个钱包就是当前的钱包
		if (wallets.get(0).getWalletId().equals(walletId)&&wallets.size()==1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前只有一个钱包");
			return resultInfo;
		}
		
		for (Wallet wallet : wallets) {
			if (!wallet.getWalletId().equals(wallet)) {
				resultInfo.setMessage("切换成功");
				resultInfo.setResult(wallet);
				return resultInfo;
			}
		}
		resultInfo.setCode("-1");
		resultInfo.setMessage("切换失败");
		return resultInfo;
	}

	@Override
	public Object getWalletDetailBack(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=detailMapper.countTotalWalletDetailsBack(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<WalletDetail> details=detailMapper.selectWalletDetailsBack(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(details);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	@Override
	public Object getAddress(Integer userId, Integer walletType)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		Wallet wallet=walletMapper.selectWallet(userId, walletType);
		
		if (wallet==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("没有导入该钱包");
			return resultInfo;
		}
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(wallet);
		return resultInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
