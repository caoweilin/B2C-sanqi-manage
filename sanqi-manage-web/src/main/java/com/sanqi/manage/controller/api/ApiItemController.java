package com.sanqi.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanqi.manage.pojo.Item;
import com.sanqi.manage.pojo.ItemDesc;
import com.sanqi.manage.service.ItemDescService;
import com.sanqi.manage.service.ItemService;

/**
*@author作者weilin
*@version 创建时间:2019年5月2日下午11:20:45
*类说明
*/
@RequestMapping("api/item")
@Controller
public class ApiItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ResponseEntity<Item> queryById(@PathVariable("itemId")Long itemId){
		try {
			Item item = this.itemService.queryById(itemId);
			if(item == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value="desc/{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryDescById(@PathVariable("itemId")Long itemId){
		try {
			ItemDesc itemDesc = this.itemDescService.queryById(itemId);
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
