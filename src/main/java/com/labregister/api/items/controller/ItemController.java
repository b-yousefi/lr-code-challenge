package com.labregister.api.items.controller;

import com.google.common.base.Preconditions;
import com.labregister.api.core.creation.EntityCreatedResponse;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.domain.ItemVersion;
import com.labregister.api.items.service.ItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ItemController {

	private static final String RESOURCE_ITEM = "/items/{id}";

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		Preconditions.checkArgument(itemService != null);
		this.itemService = itemService;
	}

	@GetMapping(value = "/items")
	@ResponseBody
	public Collection<Item> getItems() {
		return itemService.getItems();
	}

	@PostMapping(value = "/items")
	public EntityCreatedResponse<Item> createItem(@RequestBody Item request) {
		Item created = itemService.createItem(request);
		return new EntityCreatedResponse<>(created, getItemLocation(created));
	}

	@PutMapping(value = "/items/{itemId}")
	@ResponseBody
	public Item updateItem(@PathVariable String itemId, @RequestBody Item request) {
		return itemService.updateItem(itemId, request);
	}

	@GetMapping(value = "/items/{itemId}/versions")
	@ResponseBody
	public Collection<ItemVersion> getItemVersions(@PathVariable String itemId) {
		return itemService.getItemVersions(itemId);
	}

	private URI getItemLocation(Item item) {
		UriTemplate itemLocation = new UriTemplate(RESOURCE_ITEM);
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("id", item.getId());
		return itemLocation.expand(uriVariables);
	}
}
