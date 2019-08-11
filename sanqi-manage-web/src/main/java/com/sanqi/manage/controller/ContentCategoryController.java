package com.sanqi.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanqi.manage.pojo.ContentCategory;
import com.sanqi.manage.service.ContentCategoryService;

/**
*@author作者weilin
*@version 创建时间:2019年5月1日下午4:39:52
*类说明
*/
@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/*
	 * 根据父节点id查询内容分类列表
	 * 
	 * */
	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryByPId(@RequestParam(value ="id",
			defaultValue="0")Long pid){
		try {
			ContentCategory record = new ContentCategory();
			record.setParentId(pid);
			List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
			if(null == list || list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/*
	 * 新增节点
	 * 
	 * */
	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory
			contentCategory){
		try {		
			this.contentCategoryService.saveContentCategory(contentCategory);
			return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method =RequestMethod.PUT)
	public ResponseEntity<Void> rename(@RequestParam("id")Long id,
			@RequestParam("name")String name){
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setName(name);
			contentCategory.setId(id);
			this.contentCategoryService.updateSelective(contentCategory);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method =RequestMethod.DELETE)
	public ResponseEntity<Void> delete(ContentCategory contentCategory){
		try {
			this.contentCategoryService.deleteAll(contentCategory);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
