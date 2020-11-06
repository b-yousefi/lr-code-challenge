package com.labregister.api.items.service;

import com.labregister.api.core.validation.EntityValidator;
import com.labregister.api.items.domain.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

	private List<Item> items;

	private EntityValidator entityValidator;

	public ItemServiceImpl(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;
		this.items = new ArrayList<>();
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
		return this.items.stream()
		                 .sorted()
		                 .collect(Collectors.toList());
	}

	@Override
	public void deleteAllItems() {
		this.items.clear();
	}

	private Item save(Item item) {
		this.items.add(item);
		return item;
	}
}
