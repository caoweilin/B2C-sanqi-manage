package com.sanqi.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanqi.manage.pojo.ItemDesc;
import com.sanqi.manage.service.ItemDescService;

/**
*@author作者weilin
*@version 创建时间:2019年4月23日下午9:45:58
*类说明
*/
@Controller
@RequestMapping("item/desc")
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value = "{itemId}",method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByitemId(@PathVariable("itemId") Long itemId){
		ItemDesc itemDesc = this.itemDescService.queryById(itemId);
		try {
			if(itemDesc == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
