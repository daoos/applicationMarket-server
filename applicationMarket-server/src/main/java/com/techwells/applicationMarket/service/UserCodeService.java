package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.UserCode;

/**
 * 验证码的业务层
 * @author 陈加兵 	
 * @since 2018年11月26日 上午10:09:34
 */
@Transactional
public interface UserCodeService {
	/**
	 * 添加验证码
	 * @param code
	 * @return
	 * @throws Exception
	 */
	int addCode(UserCode code)throws Exception;
	
	
	/**
	 * 根据手机号码获取验证码信息
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	UserCode getCodeByMobile(String mobile)throws Exception;
	
	/**
	 * 修改验证码
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	int modifyUserCode(UserCode userCode)throws Exception;
	
	
}
