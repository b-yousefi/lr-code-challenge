package com.labregister.api.items.domain;

import com.labregister.api.core.validation.Entity;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Item resource to demonstrate Labregister functionality
 */
public class Item implements Entity, Comparable<Item> {

	private String id;

	@NotBlank
	private String name;

	private Map<@NotBlank String, @NotBlank String> attributes = new HashMap<>();

	private Date creationDate;

	public Item() {
		// needed for JSON deserialization
	}

	public Item(String name) {
		this.name = name;
	}

	public Item(String name, Map<String, String> attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int compareTo(Item item) {
		return item.getCreationDate().compareTo(this.getCreationDate());
	}
}
