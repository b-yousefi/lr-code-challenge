package com.labregister.api.items.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labregister.api.core.validation.Entity;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * Simple Item resource to demonstrate Labregister functionality
 */
public class Item implements Entity, Comparable<Item> {

	private String id;

	@NotBlank
	private String name;

	private Map<@NotBlank String, @NotBlank String> attributes = new HashMap<>();

	private Date creationDate;

	private Date lastUpdateDate;

	private int versionNumber = 0;

	@JsonIgnore
	private final Deque<ItemVersion> versions = new LinkedList<>();

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
		this.lastUpdateDate = creationDate;
	}

	public List<ItemVersion> getVersions() {
		return new ArrayList<>(versions);
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void createVersion() {
		versionNumber++;
		versions.push(new ItemVersion(this));
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	@Override
	public int compareTo(Item item) {
		return item.getCreationDate().compareTo(this.getCreationDate());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return id.equals(item.id) &&
				name.equals(item.name) &&
				attributes.equals(item.attributes);
	}
}
