package com.sanqi.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.sanqi.manage.mapper.ItemParamItemMapper;
import com.sanqi.manage.pojo.ItemParamItem;

/**
*@author作者weilin
*@version 创建时间:2019年4月23日下午10:49:37
*类说明
*/
@Service
public class ItemParamItemService extends BaseService<ItemParamItem>{
	
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	public void updateItemParamItem(Long itemId,String itemParams) {
		//更新数据
		ItemParamItem record = new ItemParamItem();
		record.setParamData(itemParams);	
		record.setUpdated(new Date());
		
		//更新条件
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		this.itemParamItemMapper.updateByExampleSelective(record, example);
		
	}

}
