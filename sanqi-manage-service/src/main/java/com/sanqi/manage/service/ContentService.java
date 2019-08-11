package com.sanqi.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanqi.common.bean.EasyUIResult;
import com.sanqi.manage.mapper.ContentMapper;
import com.sanqi.manage.pojo.Content;

/**
*@author作者weilin
*@version 创建时间:2019年5月1日下午4:38:33
*类说明
*/
@Service
public class ContentService extends BaseService<Content>{

	@Autowired
	private ContentMapper contentMapper;
	
	public EasyUIResult queryListByCategoryId(Long categoryId,Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		List<Content> list = this.contentMapper.queryContentList(categoryId);
		PageInfo<Content> pageInfo = new PageInfo<Content>(list);
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

}
