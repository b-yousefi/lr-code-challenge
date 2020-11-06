package com.labregister.api.items;

import com.google.common.collect.ImmutableMap;
import com.labregister.api.core.exception.EntityValidationException;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.service.ItemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
		Item secondItemInList = items.get(1);
		Assert.assertEquals("Item 2", secondItemInList.getName());
		Assert.assertEquals("2020", secondItemInList.getAttributes().get("year"));
		Assert.assertEquals("white", secondItemInList.getAttributes().get("color"));
		Assert.assertEquals("M", secondItemInList.getAttributes().get("size"));
		Assert.assertNotNull(secondItemInList.getId());
		Assert.assertNotNull(secondItemInList.getCreationDate());
		Item lastItemInList = items.get(2);
		Assert.assertEquals("Item 1", lastItemInList.getName());
		Assert.assertEquals("2019", lastItemInList.getAttributes().get("year"));
		Assert.assertEquals("black", lastItemInList.getAttributes().get("color"));
		Assert.assertEquals("S", lastItemInList.getAttributes().get("size"));
		Assert.assertNotNull(firstItemInList.getId());
		Assert.assertNotNull(firstItemInList.getCreationDate());
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
