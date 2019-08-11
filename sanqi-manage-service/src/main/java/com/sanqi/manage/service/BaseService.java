package com.sanqi.manage.service;
/**
*@author作者weilin
*@version 创建时间:2019年4月22日下午4:46:29
*类说明
*/

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanqi.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
	
	/*
	1、queryById
	2、queryAll
	3、queryOne
	4、queryListByWhere
	5、queryPageListByWhere
	6、save
	7、update
	8、deleteById
	9、deleteByIds
	10、deleteByWhere
	*/
	
	@Autowired
	private Mapper<T> mapper;
	
	public T queryById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<T> queryAll(){
		return mapper.select(null);
	}
	
	public T queryOne(T record) {
		return mapper.selectOne(record);
	}
	
	public List<T> queryListByWhere(T record){
		return mapper.select(record);
	}
	
	public PageInfo<T> queryPageListByWhere(T record,Integer page, Integer rows){
		PageHelper.startPage(page,rows);
		List<T> list = mapper.select(record);
		return new PageInfo<T>(list);
	}
	
	public Integer save(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insert(record);
	}
	
	/*
	 * 选择字段不为空的数据进行插入
	 * */
	public Integer saveSelective(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insertSelective(record);
	}
	
	public Integer update(T record) {
		record.setUpdated(new Date());
		return mapper.updateByPrimaryKey(record);
	}
	
	public Integer updateSelective(T record) {
		record.setUpdated(new Date());
		record.setCreated(null);
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public Integer deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer deleteByIds(List<Object> ids, Class<T> clazz,String property) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, ids);
		return mapper.deleteByExample(example);
	}
	
	public Integer deleteByWhere(T record) {
		return mapper.delete(record);
	}
}
