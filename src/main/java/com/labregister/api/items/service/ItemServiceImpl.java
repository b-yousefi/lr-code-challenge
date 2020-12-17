package com.labregister.api.items.service;

import com.labregister.api.core.exception.ResourceNotFoundException;
import com.labregister.api.core.validation.EntityValidator;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.domain.ItemVersion;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public final class ItemServiceImpl implements ItemService {

	private Map<String, Item> items;

	private EntityValidator entityValidator;

	public ItemServiceImpl(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;
		this.items = new HashMap<>();
	}

	@Override
	public Item createItem(Item request) {
		entityValidator.validateCreate(request);
		request.setId(UUID.randomUUID().toString());
		request.setCreationDate(new Date());
		return save(request);
	}

	@Override
	public List<Item> getItems() {
		return this.items.values().stream().sorted().collect(Collectors.toList());
	}

	@Override
	public void deleteAllItems() {
		this.items.clear();
	}

	@Override
	public Item updateItem(String id, Item request) {
		entityValidator.validateUpdate(id, request);
		Item previousItem = getItem(id);
		if (isNewVersion(request)) {
			previousItem.setName(request.getName());
			previousItem.setAttributes(request.getAttributes());
			previousItem.setLastUpdateDate(new Date());
			return save(previousItem);
		} else {
			return previousItem;
		}

	}

	@Override
	public List<ItemVersion> getItemVersions(String itemId) {
		return this.getItem(itemId).getVersions();
	}

	private Item save(Item item) {
		this.items.put(item.getId(), item);
		item.createVersion();
		return item;
	}

	private boolean isNewVersion(Item updatedItem) {
		return !this.items.containsKey(updatedItem.getId())
				|| !this.items.get(updatedItem.getId()).equals(updatedItem);
	}

	private Item getItem(String itemId) {
		if (!items.containsKey(itemId)) {
			throw new ResourceNotFoundException("ITEM NOT FOUND");
		} else {
			return items.get(itemId);
		}
	}
}
