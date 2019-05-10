package com.itheima.springmvc.service;

import java.util.List;

import com.itheima.springmvc.pojo.Items;

public interface ItemService {
	public List<Items> selectItemsList();
	
	public Items selectItemsById(Integer id);
	
	public void updateItems(Items items);
}
