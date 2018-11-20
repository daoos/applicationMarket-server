package com.techwells.applicationMarket.service.impl;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.SystemConfigMapper;
import com.techwells.applicationMarket.dao.TaskMapper;
import com.techwells.applicationMarket.dao.TaskTypeMapper;
import com.techwells.applicationMarket.dao.UserMapper;
import com.techwells.applicationMarket.dao.UserTaskMapper;
import com.techwells.applicationMarket.dao.WalletDetailMapper;
import com.techwells.applicationMarket.dao.WalletMapper;
import com.techwells.applicationMarket.domain.SystemConfig;
import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.domain.TaskType;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.UserTask;
import com.techwells.applicationMarket.domain.Wallet;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.domain.rs.AppAdminVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImageVos;
import com.techwells.applicationMarket.domain.rs.TaskAdminVos;
import com.techwells.applicationMarket.domain.rs.TaskAppVersionVos;
import com.techwells.applicationMarket.service.TaskService;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.DateUtil;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.moac.MoacUtils;
import com.techwells.applicationMarket.util.moac.TransactionDetail;
import com.techwells.applicationMarket.util.swtc.Amount;
import com.techwells.applicationMarket.util.swtc.PayNeedData;
import com.techwells.applicationMarket.util.swtc.PayObject;
import com.techwells.applicationMarket.util.swtc.SwtcUtils;

@Service
public class TaskServiceImpl implements TaskService{
	
	private Logger logger=Logger.getLogger(TaskServiceImpl.class);  //日志记录
	
	
	@Resource
	private TaskMapper taskMapper;
	
	@Resource
	private UserTaskMapper userTaskMapper;
	
	@Resource
	private WalletDetailMapper detailMapper;
	
	@Resource
	private AppMapper appMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private WalletMapper walletMapper;
	
	@Resource
	private SystemConfigMapper configMapper;
	
	@Resource
	private TaskTypeMapper taskTypeMapper;
	
	@Override
	public Object addTask(Task task) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=taskMapper.insertSelective(task);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object getTask(Integer taskId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Task task=taskMapper.selectByPrimaryKey(taskId);
		if (task==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		resultInfo.setResult(task);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	
	@Override
	public Object deleteTask(Integer taskId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=taskMapper.deleteByPrimaryKey(taskId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object modifyTask(Task task) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=taskMapper.updateByPrimaryKeySelective(task);
		if (count==0) {
			resultInfo.setMessage("失败");
			resultInfo.setCode("-1");
			return resultInfo;
		}
		resultInfo.setMessage("成功");
		return resultInfo;
	}

	@Override
	public Object getTaskTypeList() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<TaskType> taskTypes=taskTypeMapper.selectTaskTypes();
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(taskTypes);
		return resultInfo;
	}

	@Override
	public Object getTaskList(PagingTool pagingTool) {
		ResultInfo resultInfo=new ResultInfo();
		int count=taskMapper.countTotal(pagingTool);
		List<TaskAdminVos> taskAdminVos=taskMapper.selectTaskList(pagingTool);
		resultInfo.setTotal(count);
		resultInfo.setResult(taskAdminVos);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object getDetailList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//分页获取任务
		int total=taskMapper.countTotalDetail(pagingTool);
		
		//分页获取的任务信息，有效的、已开始的，未结束的，                                                        ----当前用户未完成的或者未达到允许完成次数的，未完待续-----
		List<Task> tasks=taskMapper.selectTaskDetailList(pagingTool);
		
		List<TaskAppVersionVos> taskAppVersionVos=new ArrayList<TaskAppVersionVos>();   //封装任务信息
		
		//遍历获取详细信息
		for (Task task : tasks) {
			//根据用户Id和任务Id获取用户所有完成的任务
			List<UserTask> userTaskList=userTaskMapper.selectUserTasksListByUserIdAndTaskId((Integer)pagingTool.getParams().get("userId"),task.getTaskId());   //根据用户信息查找
//			
//			//如果userTask存在，并且已经达到了允许下载的次数了，那么就不能显示在任务中给用户
			if (userTaskList!=null&&userTaskList.size()>=task.getAllowNumber()) {
				continue;   //继续下一次的循环，不返回
			}
			
			TaskAppVersionVos taskApp=null;
			//如果是下载安装类，需要联合查询应用信息
			if (task.getTaskTypeId().equals(1)) {
				//查询应用的信息
				AppVersionImageVos appVersion=appMapper.selectAppVersionImageVos(task.getAppId(),(Integer)pagingTool.getParams().get("platform"));
				if (appVersion!=null) {
					taskApp=new TaskAppVersionVos();
					taskApp.setAppId(task.getAppId());  //应用Id
					taskApp.setTaskId(task.getTaskId());
					taskApp.setAppName(appVersion.getAppName());  //应用名称
					taskApp.setLogo(appVersion.getLogo());  //图标 
					taskApp.setVersionNum(appVersion.getVersionNum()); //版本号
					taskApp.setSize(appVersion.getSize());   //应用大小
					taskApp.setIntroduction(task.getIntroduction());//简介
					taskApp.setRewardMoney(task.getRewardMoney());  //奖励的钱
					taskApp.setActivated(task.getActivated());   //奖励的类型
					taskApp.setTaskTypeId(task.getTaskTypeId());  //任务类型的Id
					taskApp.setTaskName(task.getTaskName());
					taskApp.setPackageName(appVersion.getPackageName());
					taskApp.setDownloadUrl(appVersion.getDownloadUrl());
				}
			}else {   //如果是其他类型的，直接返回信息即可
				taskApp=new TaskAppVersionVos();
				taskApp.setIntroduction(task.getIntroduction());//简介
				taskApp.setRewardMoney(task.getRewardMoney());  //奖励的钱
				taskApp.setActivated(task.getActivated());   //奖励的类型
				taskApp.setLink(task.getLink());  //链接
				taskApp.setTaskTypeId(task.getTaskTypeId());  //任务类型的Id
				taskApp.setTaskName(task.getTaskName());
				taskApp.setTaskId(task.getTaskId());
				taskApp.setLogo("http://www.emoonbow.com/applicationMarket-upload/appType/dpplog.png");  //其他类型的都是使用默认的图片
			}
			if (taskApp!=null) {
				taskAppVersionVos.add(taskApp);  //添加到集合中
			}
		}
		
		
		//获取当前用户的任务完成情况
		List<UserTask> userTasks=userTaskMapper.selectUserTaskList((Integer)pagingTool.getParams().get("userId"));
		
		Map<String, Object> map=new HashMap<String, Object>();  //封装结果集
		map.put("waitComplete",taskAppVersionVos.size()!=0?taskAppVersionVos:null);   //待完成的任务
		map.put("alreadyComplete", userTasks.size()>0?userTasks:null);  //已经完成的任务
		resultInfo.setTotal(total);
		resultInfo.setResult(map);
		resultInfo.setMessage("获取完成");
		return resultInfo;
	}

	
	/**
	 * 领取任务奖励
	 * @throws Throwable 
	 */
	@Override
	public Object receiveReward(Integer taskDetailId,String hash) throws Throwable {
		ResultInfo resultInfo=new ResultInfo();
		
		//根据id查询信息
		UserTask userTask=userTaskMapper.selectByPrimaryKey(taskDetailId);
		
		if (userTask==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该任务明细不存在");
			return resultInfo;
		}
		
		if (userTask.getStatus().equals(1)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该任务奖励已发放");
			return resultInfo;
		}
		
		
		//获取配置信息，其中包含开发商的账号和秘钥
		SystemConfig config=configMapper.selectByPrimaryKey(1);  
		
		if (config==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("配置信息不存在");
			return resultInfo;
		}
		
		
		
		//未发放，存在，那么就需要进行转账了
		
		if (userTask.getActivated().equals(1)) {  //如果是MOAC，前台进行转账，这里改变下状态即可
//			Thread.sleep(4000);
			
			//开发商只有配置了账号才能进行转账
//			if (config.getSwtcAddress()==null||config.getSwtcSecret()==null) {
//				resultInfo.setCode("-1");
//				resultInfo.setMessage("开发商没有配置账号，暂时不能领取奖励");
//				return resultInfo;
//			}
//			
//			//根据用户Id和对应的钱包类型获取用户的钱包信息,2表示井通钱包 1 表示MOAC钱包
//			Wallet wallet=walletMapper.selectWallet(userTask.getUserId(),1);
//			
//			if (wallet==null) {
//				resultInfo.setCode("999999");   //特殊的返回码，前台需要定制
//				resultInfo.setMessage("您还没有导入钱包账户，请导入井通钱包账户");
//				return resultInfo;
//			}
			
			
			//根据用户Id获取moac钱包
			Wallet wallet=walletMapper.selectWallet(userTask.getUserId(), 1);
			
			if (wallet==null) {
				resultInfo.setCode("999999");   //特殊的返回码，前台需要定制
				resultInfo.setMessage("您还没有导入钱包账户，请导入井通钱包账户");
				return resultInfo;
			}
			
			//由于交易延迟的问题，因此先保留交易的hash
			WalletDetail detail=new WalletDetail();
			//墨客转账是在前台完成的，这里只需要用hash值查询转账的信息即可
//			TransactionDetail transactionDetail=MoacUtils.getTransactionDetail(hash);
//			detail.setNumber(System.currentTimeMillis()+"");
//			detail.setCreateDate(new Date());  //设置创建日期
//			detail.setBlock(transactionDetail.getBlockNumber());   //设置区块信息
//			detail.setFromAddress(transactionDetail.getFrom());  //转账方的钱包地址
//			detail.setToAddress(transactionDetail.getTo());
//			detail.setFee((Double)(Long.parseLong(transactionDetail.getGas())/1000000000000000000.0*Long.parseLong(transactionDetail.getGasPrice())));   //旷工费用
//			detail.setRemark("任务奖励发放");   //设置备注为转账
//			Date trsdate=new Date();   //交易日期
//			detail.setTransactionDate(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));  //交易时间
			detail.setActivated(userTask.getActivated());  //设置钱包的类型
//			detail.setMoney("-"+transactionDetail.getValue());
			detail.setWalletId(wallet.getWalletId());
			detail.setHash(hash);
			
			int count=detailMapper.insertSelective(detail);
			if (count==0) {
				throw new RuntimeException();
			}
			
		}else if(userTask.getActivated().equals(2)){  //如果是井通
			
			//开发商只有配置了账号才能进行转账
			if (config.getSwtcAddress()==null||config.getSwtcSecret()==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("开发商没有配置账号，暂时不能领取奖励");
				return resultInfo;
			}
			
			//根据用户Id和对应的钱包类型获取用户的钱包信息,2表示井通钱包
			Wallet wallet=walletMapper.selectWallet(userTask.getUserId(),2);
			
			if (wallet==null) {
				resultInfo.setCode("999999");   //特殊的返回码，前台需要定制
				resultInfo.setMessage("您还没有导入钱包账户，请导入井通钱包账户");
				return resultInfo;
			}
			
			//钱包存在，配置存在，那么可以进行转账了
			
			PayNeedData data=new PayNeedData();
			String orderNum=System.currentTimeMillis()+"";
			data.setClient_id(orderNum);  //订单号
			data.setSecret(Base64Util.Decoder(config.getSwtcSecret()));  //秘钥
			
			PayObject payObject=new PayObject();
			payObject.setDestination(wallet.getAddress());   //目标账号
			payObject.setSource(config.getSwtcAddress());  //发起账号
			
			//转账的金额
			Amount amount=new Amount();
			amount.setValue(String.valueOf(userTask.getMoney()));  //设置转账的金额
			
			payObject.setAmount(amount);
			
			data.setPayment(payObject);
			
			
			//调用转账接口
			Map<String, Object> map=SwtcUtils.pay(data, config.getSwtcAddress());
			
			//转账失败
			if (map==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("领取失败");
				return resultInfo;
			}
			
			if (!(Boolean)map.get("success")==true||!(map.get("result").equals("tesSUCCESS"))) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("领取失败");
				return resultInfo;
			}
			
			String swtHash=(String) map.get("hash");  //获取hash值，用于查询交易信息
			
			//添加一条钱包明细
			//封装信息
			WalletDetail detail=new WalletDetail();
			detail.setHash(swtHash);  //hash的值
			detail.setNumber(orderNum);   //交易号
			detail.setCreateDate(new Date());  //设置创建日期
			detail.setBlock(null);   //设置区块信息
			detail.setFromAddress(config.getSwtcAddress());  //转账方的钱包地址
			detail.setToAddress(wallet.getAddress());
			detail.setFee((Double)map.get("fee"));   //旷工费用
			detail.setRemark("任务奖励发放");   //设置备注为转账
			Date trsdate=new Date();   //交易日期
			detail.setTransactionDate(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));  //交易时间
			detail.setUrl(SwtcUtils.HTTP+"v2/transactions/"+hash);   //设置公开查账的地址
			detail.setActivated(wallet.getType());  //设置钱包的类型
			detail.setMoney("-"+userTask.getMoney());
			detail.setWalletId(wallet.getWalletId());
			
			int count=detailMapper.insertSelective(detail);
			if (count==0) {
				throw new RuntimeException();
			}
			
		}
		
		//更新信息为已领取
		userTask.setStatus(1);
		
		int count=userTaskMapper.updateByPrimaryKeySelective(userTask);
		
		if (count==0) {
			throw new RuntimeException();
		}
		
		resultInfo.setMessage("领取成功");
		return resultInfo;
	}

	/**
	 * 验证用户完成的次数、失效、开始时间和结束时间
	 */
	@Override
	public Object finishTask(Integer userId, Integer taskId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取任务信息
		Task task=taskMapper.selectByPrimaryKey(taskId);
		
		if (task==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该任务不存在");
			return resultInfo;
		}
		
		if (task.getStatus().equals(0)) {   //失效
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务已经失效");
			return resultInfo;
		}
		
		
		//验证时间
		if (!(task.getStartDate().getTime()<System.currentTimeMillis()&&System.currentTimeMillis()<task.getEndDate().getTime())) { 
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务还未开始或者已经结束");
			return resultInfo;
		}
		
		
		//根据用户Id和任务Id获取用户完成任务的信息
		
		List<UserTask> userTasks=userTaskMapper.selectUserTasksListByUserIdAndTaskId(userId, taskId);
		
		if (userTasks.size()>=task.getAllowNumber()) {   //如果完成次数已经达到了最大完成的次数
			resultInfo.setCode("-1");
			resultInfo.setMessage("已经达到最大完成次数");
			return resultInfo;
		}
		
		//没有达到最大完成次数，那么直接添加一条记录即可
		UserTask userTask=new UserTask();
		userTask.setCreateDate(new Date());
		userTask.setStatus(2);  //待发放
		userTask.setUserId(userId);
		userTask.setTaskId(taskId);
		userTask.setActivated(task.getActivated());  //奖励金额的类型
		userTask.setMoney(task.getRewardMoney());  //奖励的钱
		
		String content=null;
		if (task.getActivated().equals(1)) {  //领取墨客币
			content=task.getTaskName()+"领取墨客币";
		}else {  //井通
			content=task.getTaskName()+"领取井通";
		}
		
		userTask.setContent(content);
		
		//插入
		int count=userTaskMapper.insertSelective(userTask);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加任务记录失败");
			return resultInfo;
		}
		resultInfo.setMessage("任务完成成功");
		return resultInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
