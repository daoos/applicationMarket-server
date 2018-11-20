package com.techwells.applicationMarket.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import jnr.ffi.Struct.int16_t;

import org.springframework.core.NestedCheckedException;
import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.TaskMapper;
import com.techwells.applicationMarket.dao.UserMapper;
import com.techwells.applicationMarket.dao.UserTaskMapper;
import com.techwells.applicationMarket.dao.WalletMapper;
import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.UserTask;
import com.techwells.applicationMarket.domain.Wallet;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImageVos;
import com.techwells.applicationMarket.domain.rs.TaskAppVersionVos;
import com.techwells.applicationMarket.service.UserService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private WalletMapper walletMapper;

	@Resource
	private UserTaskMapper userTaskMapper;
	
	@Resource
	private TaskMapper taskMapper;

	@Resource
	private AppMapper appMapper;
	
	
	
	
	@Override
	public Object regist(String userName, String password) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		// 判断用户账号是否存在，如果不存在，那么可以直接注册
		User user = userMapper.selectUserByUserName(userName);

		// 已经被注册了
		if (user != null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该账号已经被注册了");
			return resultInfo;
		}

		// 如果没有被注册，那么可以直接注册
		user = new User(); // 需要创建
		user.setUserName(userName);
		user.setMobile(userName);
		user.setPassword(password);
		user.setCreateDate(new Date());

		int count = userMapper.insertSelective(user);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("注册失败");
			return resultInfo;
		}

		resultInfo.setMessage("注册成功");
		return resultInfo;
	}

	@Override
	public Object login(String userName, String password) throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		User user = userMapper.selectUserByUserName(userName);

		if (user == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户不存在");
			return resultInfo;
		}

		// 如果用户存在，验证密码

		if (!user.getPassword().equals(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码错误");
			return resultInfo;
		}

		user.setActivated(1); // 已登录
		user.setLastLoginTime(new Date()); // 最后登录时间

		userMapper.updateByPrimaryKeySelective(user); // 更新数据

		user.setPassword("**********");

		// 密码正确，登录成功
		resultInfo.setMessage("登录成功");
		resultInfo.setResult(user);
		return resultInfo;

	}

	@Override
	public Object forgetPassword(String userName, String newPwd)
			throws Exception {
		// 判断这个用户名是否已经注册了
		ResultInfo resultInfo = new ResultInfo();
		User user = userMapper.selectUserByUserName(userName);

		if (user == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户没有注册");
			return resultInfo;
		}

		user.setPassword(newPwd);

		// 修改
		int count = userMapper.updateByPrimaryKeySelective(user);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改密码失败");
			return resultInfo;
		}

		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object modifyInfo(User user) throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		int count = userMapper.updateByPrimaryKeySelective(user);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;

	}

	@Override
	public User getUser(String userName) throws Exception {
		return userMapper.selectUserByUserName(userName);
	}

	@Override
	public Object getExaminList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		// 获取总数
		int total = userMapper.countTotalExaminList(pagingTool);

		if (total == 0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}

		List<User> users = userMapper.selectExmainList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(users);
		resultInfo.setTotal(total);
		return resultInfo;

	}

	@Override
	public Object modifyBatch(String[] ids, Integer status) throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		int count = userMapper.updateUserBatch(ids, status);

		if (count != ids.length) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核失败");
			return resultInfo;
		}

		resultInfo.setMessage("审核成功");
		return resultInfo;

	}

	@Override
	public Object getUserById(Integer userId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		User user = userMapper.selectByPrimaryKey(userId);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(user);
		return resultInfo;
	}

	@Override
	public Object modifyPwd(Integer userId, String oldPwd, String newPwd) {
		ResultInfo resultInfo = new ResultInfo();
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前用户不存在");
			return resultInfo;
		}

		if (!oldPwd.equals(user.getPassword())) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前密码不正确");
			return resultInfo;
		}

		User user2 = new User();
		user2.setPassword(newPwd);
		user2.setUserId(userId);

		int count = userMapper.updateByPrimaryKeySelective(user2);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}

		resultInfo.setMessage("修改成功");

		return resultInfo;
	}

	@Override
	public Object getUserListBack(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		// 分页获取用户信息
		int total = userMapper.countTotalUserListBack(pagingTool);
		if (total == 0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}

		List<User> users = userMapper.selectUserListBack(pagingTool);

		// 遍历查询钱包
		for (User user : users) {
			// 根据用户Id查询钱包信息
			List<Wallet> wallets = walletMapper.selectWallets(user.getUserId());
			// 遍历钱包
			for (Wallet wallet : wallets) {
				if (wallet.getType().equals(1)) { // Moac钱包
					user.setMoacBalance(wallet.getBalance());
				} else if (wallet.getType().equals(2)) {
					user.setSwtcBalance(wallet.getBalance());
				}
			}
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(users);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	@Override
	public Object getTaskListBack(Integer userId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		List<UserTask> userTasks = userTaskMapper.selectUserTaskList(userId);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(userTasks);
		return resultInfo;
	}

	@Override
	public List<User> getUserListById(String[] userIds) {
		return null;
	}

	@Override
	public Object getCenterInfo(Integer userId, Integer platform)
			throws Exception {
		ResultInfo resultInfo = new ResultInfo();

		// 获取任务清单
		PagingTool pagingTool=new PagingTool();
		pagingTool.setStartNum(0);
		pagingTool.setPageSize(3);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("platform", platform);
		pagingTool.setParams(params);
		
		// 分页获取的任务信息，有效的、已开始的，未结束的， ----当前用户未完成的或者未达到允许完成次数的，未完待续-----
//		List<Task> tasks = taskMapper.selectTaskDetailList(pagingTool);
//
//		List<TaskAppVersionVos> taskAppVersionVos = new ArrayList<TaskAppVersionVos>(); // 封装任务信息

		// 遍历获取详细信息
//		for (Task task : tasks) {
//			TaskAppVersionVos taskApp = null;
//			// 如果是下载安装类，需要联合查询应用信息
//			if (task.getTaskTypeId().equals(1)) {
//				// 查询应用的信息
//				AppVersionImageVos appVersion = appMapper
//						.selectAppVersionImageVos(task.getAppId(),
//								(Integer) pagingTool.getParams()
//										.get("platform"));
//				if (appVersion != null) {
//					taskApp = new TaskAppVersionVos();
//					taskApp.setAppId(task.getAppId()); // 应用Id
//					taskApp.setTaskId(task.getTaskId());
//					taskApp.setAppName(appVersion.getAppName()); // 应用名称
//					taskApp.setLogo(appVersion.getLogo()); // 图标
//					taskApp.setVersionNum(appVersion.getVersionNum()); // 版本号
//					taskApp.setSize(appVersion.getSize()); // 应用大小
//					taskApp.setIntroduction(task.getIntroduction());// 简介
//					taskApp.setRewardMoney(task.getRewardMoney()); // 奖励的钱
//					taskApp.setActivated(task.getActivated()); // 奖励的类型
//					taskApp.setTaskTypeId(task.getTaskTypeId()); // 任务类型的Id
//					taskApp.setTaskName(task.getTaskName());
//				}
//			} else { // 如果是其他类型的，直接返回信息即可
//				taskApp = new TaskAppVersionVos();
//				taskApp.setIntroduction(task.getIntroduction());// 简介
//				taskApp.setRewardMoney(task.getRewardMoney()); // 奖励的钱
//				taskApp.setActivated(task.getActivated()); // 奖励的类型
//				taskApp.setLink(task.getLink()); // 链接
//				taskApp.setTaskTypeId(task.getTaskTypeId()); // 任务类型的Id
//				taskApp.setTaskName(task.getTaskName());
//				taskApp.setTaskId(task.getTaskId());
//			}
//			if (taskApp != null) {
//				taskAppVersionVos.add(taskApp); // 添加到集合中
//			}
//		}
		
		
		//获取一个钱包
		List<Wallet> wallets=walletMapper.selectWallets(userId);   
		
		//封装返回的数据
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("wallet", wallets.size()!=0?wallets.get(0):null);  //钱包信息
//		map.put("tasks",taskAppVersionVos);  //任务清单
		
		//获取安装记录，（安卓）
		if (platform.equals(1)) {  //如果是安卓，那么需要查询安装记录
			PagingTool pagingTool2=new PagingTool(1,4);  //显示前4个
			Map<String, Object> params1=new HashMap<String, Object>();
			params1.put("userId", userId);
			int total=appMapper.countTotalUpgrade(pagingTool2);   //获取总数
			
			List<AppAndVersionVos> appAndVersionVos=appMapper.selectUpgradeList(pagingTool);
			map.put("total", total);
			map.put("apps", appAndVersionVos);
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		return resultInfo;
	}

}
