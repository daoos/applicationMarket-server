package com.techwells.applicationMarket.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.UserCodeMapper;
import com.techwells.applicationMarket.domain.UserCode;
import com.techwells.applicationMarket.service.UserCodeService;

/**
 * 验证码
 * @author 陈加兵 	
 * @since 2018年11月26日 上午10:10:10
 */
@Service
public class UserCodeServiceImpl implements UserCodeService{

	@Resource
	private UserCodeMapper userCodeMapper;
	
	private Logger logger=LoggerFactory.getLogger(UserCodeServiceImpl.class);
	
	
	@Override
	public int addCode(UserCode code) throws Exception {
		return userCodeMapper.insertSelective(code);
	}

	@Override
	public UserCode getCodeByMobile(String mobile) throws Exception {
		return userCodeMapper.selectUserCodeByMobile(mobile);
	}

	@Override
	public int modifyUserCode(UserCode userCode) throws Exception {
		return userCodeMapper.updateByPrimaryKeySelective(userCode);
	}
	
}
