package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ApkVersionService {
	Object getLastApkVersion(Integer type)throws Exception;
}
