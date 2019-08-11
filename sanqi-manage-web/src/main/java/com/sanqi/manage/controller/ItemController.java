package com.sanqi.manage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanqi.common.bean.EasyUIResult;
import com.sanqi.manage.pojo.Item;
import com.sanqi.manage.service.ItemService;

/**
*@author作者weilin
*@version 创建时间:2019年4月22日下午6:16:09
*类说明
*/
@Controller
@RequestMapping("item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc")String desc,
			@RequestParam("itemParams")String itemParams){
		try {
			sendMsg(item.getId(),"insert");
			this.itemService.saveItem(item,desc,itemParams);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value ="page",defaultValue ="1" )Integer page,
			@RequestParam(value ="rows",defaultValue ="30" )Integer rows){
		
		try {
			EasyUIResult result = this.itemService.queryItemList(page,rows);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc")String desc,
			@RequestParam("itemParams")String itemParams){
			try {
				boolean b = this.itemService.updateItem(item,desc,itemParams);
				if(b) {
					//发送消息至其他系统，已经更新
					sendMsg(item.getId(),"update");
					return ResponseEntity.status(HttpStatus.CREATED).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@RequestParam("ids")Long id){
			try {
				boolean b = this.itemService.deleteItemById(id);
				if(b) {
					return ResponseEntity.status(HttpStatus.CREATED).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	private void sendMsg(Long itemId, String type) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("itemId", itemId);
		msg.put("type", type);
		msg.put("date", System.currentTimeMillis());
		try {
			rabbitTemplate.convertAndSend("item."+ type, MAPPER.writeValueAsString(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
