package com.labregister.api.items.service;

import com.labregister.api.items.domain.Item;
import com.labregister.api.items.domain.ItemVersion;

import java.util.List;

public interface ItemService {

	Item createItem(Item item);

	Item updateItem(String id, Item item);

	List<Item> getItems();

	List<ItemVersion> getItemVersions(String itemId);

	void deleteAllItems();
}
