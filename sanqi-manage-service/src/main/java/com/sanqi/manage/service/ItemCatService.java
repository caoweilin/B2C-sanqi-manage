package com.sanqi.manage.service;
/**
*@author作者weilin
*@version 创建时间:2019年4月22日下午3:58:36
*类说明
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanqi.common.bean.ItemCatData;
import com.sanqi.common.bean.ItemCatResult;
import com.sanqi.common.service.RedisService;
import com.sanqi.manage.mapper.ItemCatMapper;
import com.sanqi.manage.pojo.ItemCat;
@Service
public class ItemCatService extends BaseService<ItemCat>{

	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private RedisService redisService;
	
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 30 *3;
	
	private static final String REDIS_KEY = "SANQI_COM_ITEM_CAT_API";
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public ItemCatResult queryAllToTree() {
        ItemCatResult result = new ItemCatResult();
        
        //从缓存中命中
        String cacheData = this.redisService.get(REDIS_KEY);
        if(StringUtils.isNotEmpty(cacheData)) {
        	try {
				return mapper.readValue(cacheData, ItemCatResult.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }
        try {
			this.redisService.set(REDIS_KEY, mapper.writeValueAsString(result), REDIS_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return result;
}


	public List<ItemCat> queryItemCatListByParentId(Long pid) {
		ItemCat record = new ItemCat();
		record.setParentId(pid);
		return itemCatMapper.select(record);
	}
	
	
	 
}
