package com.sanqi.manage.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanqi.common.bean.ItemCatResult;
import com.sanqi.manage.service.ItemCatService;

/**
*@author作者weilin
*@version 创建时间:2019年4月27日下午8:19:33
*类说明
*/
@RequestMapping("api/item/cat")
@Controller
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	private static final ObjectMapper Mapper = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatList(){
		try {
			ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
			return ResponseEntity.ok(itemCatResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/*	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> queryItemCatList(@RequestParam
			(value = "callback",required= false)String callback) throws Exception{
		ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
		if(null == itemCatResult) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		String result = Mapper.writeValueAsString(itemCatResult);
		if(StringUtils.isEmpty(callback)) {
			return ResponseEntity.ok(result);
		}else {
			return ResponseEntity.ok(callback + "(" + result + ")");
		}
	
	}*/
	
}
