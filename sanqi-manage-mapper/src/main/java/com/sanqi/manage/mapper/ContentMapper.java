package com.sanqi.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.sanqi.manage.pojo.Content;

/**
*@author作者weilin
*@version 创建时间:2019年5月1日下午4:36:45
*类说明
*/
public interface ContentMapper extends Mapper<Content>{
	
	public List<Content> queryContentList(Long categoryId);

}
