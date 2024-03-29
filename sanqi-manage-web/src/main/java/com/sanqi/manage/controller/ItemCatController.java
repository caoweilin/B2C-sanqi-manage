package com.sanqi.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanqi.manage.pojo.ItemCat;
import com.sanqi.manage.service.ItemCatService;

/**
*@author作者weilin
*@version 创建时间:2019年4月22日下午3:59:22
*类说明
*/
@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value ="id",
	defaultValue ="0")Long pid){
		
		try {
			List<ItemCat> list = this.itemCatService.queryItemCatListByParentId(pid);
			
			if(null == list || list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

}
