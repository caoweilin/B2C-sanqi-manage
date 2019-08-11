package com.sanqi.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanqi.manage.mapper.ItemParamMapper;
import com.sanqi.manage.pojo.ItemParam;

/**
*@author作者weilin
*@version 创建时间:2019年4月23日下午10:51:22
*类说明
*/
@Service
public class ItemParamService extends BaseService<ItemParam>{

	@Autowired
	private ItemParamMapper itemParamMapper;
	
}
