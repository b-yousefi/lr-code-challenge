package com.labregister.api.items;

import com.google.common.collect.ImmutableMap;
import com.labregister.api.core.exception.EntityValidationException;
import com.labregister.api.core.exception.ResourceNotFoundException;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.domain.ItemVersion;
import com.labregister.api.items.service.ItemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	public ItemServiceTest() {
	}

	@Test(expected = EntityValidationException.class)
	public void createItem_ThrowsException_WhenNameIsEmpty() {
		final String EMPTY_NAME = "";
		itemService.createItem(new Item(EMPTY_NAME));
	}

	@Test(expected = EntityValidationException.class)
	public void createItem_ThrowsException_WhenNameIsNull() {
		itemService.createItem(new Item(null));
	}

	@Test(expected = EntityValidationException.class)
	public void createItem_ThrowsException_WhenAttributeKeyIsEmpty() {
		Item item = new Item("Item", ImmutableMap.of("", "value"));
		itemService.createItem(item);
	}

	@Test(expected = EntityValidationException.class)
	public void createItem_ThrowsException_WhenAttributeKeyIsBlank() {
		Item item = new Item("Item", ImmutableMap.of(" ", "value"));
		itemService.createItem(item);
	}

	@Test
	public void createItem_ReturnsCreatedItem_WhenRequestValid() {
		final String ITEM_NAME = "Hello Luke";
		Item request = new Item(ITEM_NAME);

		Item created = itemService.createItem(request);
		Assert.assertEquals(ITEM_NAME, created.getName());
	}

	@Test
	public void getItems_ReturnsCorrectDataOrderedByCreationDateDESC() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertEquals(3, items.size());
		Item firstItemInList = items.get(0);
		Assert.assertEquals("Item 3", firstItemInList.getName());
		Assert.assertEquals("2010", firstItemInList.getAttributes().get("year"));
		Assert.assertEquals("pink", firstItemInList.getAttributes().get("color"));
		Assert.assertNotNull(firstItemInList.getId());
		Assert.assertNotNull(firstItemInList.getCreationDate());
		Assert.assertEquals(firstItemInList.getCreationDate(), firstItemInList.getLastUpdateDate());
		Assert.assertEquals(1, firstItemInList.getVersionNumber());
		Item secondItemInList = items.get(1);
		Assert.assertEquals("Item 2", secondItemInList.getName());
		Assert.assertEquals("2020", secondItemInList.getAttributes().get("year"));
		Assert.assertEquals("white", secondItemInList.getAttributes().get("color"));
		Assert.assertEquals("M", secondItemInList.getAttributes().get("size"));
		Assert.assertNotNull(secondItemInList.getId());
		Assert.assertNotNull(secondItemInList.getCreationDate());
		Assert.assertEquals(secondItemInList.getCreationDate(), secondItemInList.getLastUpdateDate());
		Assert.assertEquals(1, secondItemInList.getVersionNumber());
		Item lastItemInList = items.get(2);
		Assert.assertEquals("Item 1", lastItemInList.getName());
		Assert.assertEquals("2019", lastItemInList.getAttributes().get("year"));
		Assert.assertEquals("black", lastItemInList.getAttributes().get("color"));
		Assert.assertEquals("S", lastItemInList.getAttributes().get("size"));
		Assert.assertNotNull(lastItemInList.getId());
		Assert.assertNotNull(lastItemInList.getCreationDate());
		Assert.assertEquals(lastItemInList.getCreationDate(), lastItemInList.getLastUpdateDate());
		Assert.assertEquals(1, lastItemInList.getVersionNumber());
		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenPathIdDoesNotExist() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item item = items.get(0);
		final String NON_EXISTING_ID = "THIS-IS-AN-NON-EXISTING-ID";
		item.setId(NON_EXISTING_ID);
		Assertions.assertThrows(ResourceNotFoundException.class, () ->
				itemService.updateItem(item.getId(), item)
		);

		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenPathIdIsNotEqualToRequestItemId() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 1);
		Item item1 = items.get(0);
		Item item2 = items.get(1);

		Assertions.assertThrows(EntityValidationException.class, () ->
				itemService.updateItem(item1.getId(), item2)
		);

		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenNameIsEmpty() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item item = items.get(0);
		final String EMPTY_NAME = "";
		item.setName(EMPTY_NAME);
		Assertions.assertThrows(EntityValidationException.class, () ->
				itemService.updateItem(item.getId(), item)
		);
		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenNameIsNull() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item item = items.get(0);
		item.setName(null);
		Assertions.assertThrows(EntityValidationException.class, () ->
				itemService.updateItem(item.getId(), item)
		);
		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenAttributeKeyIsEmpty() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item item = items.get(0);
		item.setAttributes(ImmutableMap.of("", "value"));
		Assertions.assertThrows(EntityValidationException.class, () ->
				itemService.updateItem(item.getId(), item)
		);
		cleanItemsRepo();
	}

	@Test
	public void updateItem_ThrowsException_WhenAttributeKeyIsBlank() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item item = items.get(0);
		item.setAttributes(ImmutableMap.of(" ", "value"));
		Assertions.assertThrows(EntityValidationException.class, () ->
				itemService.updateItem(item.getId(), item)
		);
		cleanItemsRepo();
	}

	@Test
	public void updateItem_DoesNotChangeVersion_WhenItemHasNotChanged() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item selectedItem = items.get(0);
		Item item = new Item();
		item.setId(selectedItem.getId());
		item.setName(selectedItem.getName());
		item.setAttributes(selectedItem.getAttributes());

		Item updatedItem = itemService.updateItem(item.getId(), item);
		Assert.assertEquals(1, updatedItem.getVersionNumber());
		cleanItemsRepo();
	}

	@Test
	public void updateItem_UpdateItem_and_CreateVersion_WhenRequestValid() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item selectedItem = items.get(0);

		final String UPDATED_ITEM_NAME = "Hello Luke";
		Item item = new Item(UPDATED_ITEM_NAME, selectedItem.getAttributes());
		item.setId(selectedItem.getId());

		Assert.assertNotEquals(UPDATED_ITEM_NAME, selectedItem.getName());
		Item updatedItem = itemService.updateItem(item.getId(), item);
		Assert.assertEquals(2, updatedItem.getVersionNumber());
		Assert.assertEquals(UPDATED_ITEM_NAME, updatedItem.getName());
		cleanItemsRepo();
	}

	@Test
	public void updateItem_UpdateItemAttributes_WhenRequestValid() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item selectedItem = items.get(0);

		final Map<String, String> UPDATED_ATTRIBUTES = ImmutableMap.of("year", "2020");
		Item item = new Item(selectedItem.getName(), UPDATED_ATTRIBUTES);
		item.setId(selectedItem.getId());

		Assert.assertNotEquals(UPDATED_ATTRIBUTES, selectedItem.getAttributes());
		Item updatedItem = itemService.updateItem(item.getId(), item);
		Assert.assertEquals(UPDATED_ATTRIBUTES, updatedItem.getAttributes());
		cleanItemsRepo();
	}

	@Test
	public void getItemVersions_ReturnsHistoryOfChangesInDescOrder() throws InterruptedException {
		cleanItemsRepo();
		initItemsRepo();
		List<Item> items = itemService.getItems();
		Assert.assertTrue(items.size() > 0);
		Item selectedItem = items.get(0);
		final String ITEM_NAME_VERSION_1 = selectedItem.getName();
		final String ITEM_NAME_VERSION_2 = "Hello Luke";
		final String ITEM_NAME_VERSION_3 = "Hello Behnaz";

		Thread.sleep(1000);
		Item item = new Item(ITEM_NAME_VERSION_2);
		item.setId(selectedItem.getId());
		itemService.updateItem(item.getId(), item);

		Thread.sleep(1000);
		item = new Item(ITEM_NAME_VERSION_3);
		item.setId(selectedItem.getId());
		itemService.updateItem(item.getId(), item);

		List<ItemVersion> versions = itemService.getItemVersions(item.getId());
		Assert.assertEquals(3, versions.size());
		ItemVersion firstVersionInList = versions.get(0);
		ItemVersion secondVersionInList = versions.get(1);
		ItemVersion thirdVersionInList = versions.get(2);
		Assert.assertEquals(ITEM_NAME_VERSION_3, firstVersionInList.getName());
		Assert.assertTrue(firstVersionInList.getVersionDate().toInstant().isAfter((secondVersionInList.getVersionDate().toInstant())));
		Assert.assertTrue(firstVersionInList.getVersionNumber() > secondVersionInList.getVersionNumber());
		Assert.assertEquals(ITEM_NAME_VERSION_2, secondVersionInList.getName());
		Assert.assertTrue(secondVersionInList.getVersionDate().toInstant().isAfter((thirdVersionInList.getVersionDate().toInstant())));
		Assert.assertTrue(secondVersionInList.getVersionNumber() > thirdVersionInList.getVersionNumber());
		Assert.assertEquals(ITEM_NAME_VERSION_1, thirdVersionInList.getName());
		cleanItemsRepo();
	}

	private void cleanItemsRepo() {
		itemService.deleteAllItems();
	}

	private void initItemsRepo() throws InterruptedException {
		Item item1 = new Item("Item 1", ImmutableMap.of("year", "2019",
				"color", "black",
				"size", "S"));

		itemService.createItem(item1);
		Thread.sleep(1000);
		Item item2 = new Item("Item 2", ImmutableMap.of("year", "2020",
		                                                "color", "white",
		                                                "size", "M"));
		itemService.createItem(item2);
		Thread.sleep(1000);
		Item item3 = new Item("Item 3", ImmutableMap.of("year", "2010",
		                                                "color", "pink"));
		itemService.createItem(item3);
	}
}
