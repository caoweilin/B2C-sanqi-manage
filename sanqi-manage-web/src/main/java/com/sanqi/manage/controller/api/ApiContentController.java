package com.sanqi.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanqi.common.bean.EasyUIResult;
import com.sanqi.manage.service.ContentService;

/**
 * @author作者weilin
 * @version 创建时间:2019年5月1日下午4:40:32 类说明
 */
@RequestMapping("api/content")
@Controller
public class ApiContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryListByCategoryId
	(@RequestParam("categoryId")Long categoryId,
			@RequestParam("page")Integer page,
	@RequestParam("rows")Integer rows){
		try {
			EasyUIResult result = this.contentService.queryListByCategoryId(categoryId,page,rows);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
