package com.sanqi.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
*@author作者weilin
*@version 创建时间:2019年4月23日下午5:23:36
*类说明
*/
@Service
public class PropertieService {
	
	@Value("${IMAGE_BASE_URL}")
	public String IMAGE_BASE_URL;
	
	@Value("${REPOSITORY_PATH}")
	public String REPOSITORY_PATH;

}
