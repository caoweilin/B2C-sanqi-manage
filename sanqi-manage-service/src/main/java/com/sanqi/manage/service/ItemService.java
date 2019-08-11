package com.sanqi.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanqi.common.bean.EasyUIResult;
import com.sanqi.manage.mapper.ItemMapper;
import com.sanqi.manage.pojo.Item;
import com.sanqi.manage.pojo.ItemDesc;
import com.sanqi.manage.pojo.ItemParamItem;

/**
*@author作者weilin
*@version 创建时间:2019年4月22日下午6:18:03
*类说明
*/
@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	public void saveItem(Item item, String desc,String itemParams) {
		item.setStatus(1);
		item.setId(null);
		super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);	
		
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setParamData(itemParams);
		this.itemParamItemService.save(itemParamItem);
	}

	public EasyUIResult queryItemList(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		
		//设置创建时间倒叙排序
		Example example = new Example(Item.class);
		example.setOrderByClause("created DESC");
		List<Item> lists = this.itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(lists);
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

	public boolean updateItem(Item item, String desc,String itemParams) {
		item.setStatus(null);//商品状态不被修改
		Integer count1 = this.updateSelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = this.itemDescService.updateSelective(itemDesc);

		this.itemParamItemService.updateItemParamItem(item.getId(), itemParams);
		
		return count1 ==1 && count2 == 1;
	}

	public boolean deleteItemById(Long id) {
		return itemMapper.deleteByPrimaryKey(id) == 1;
	}


}
