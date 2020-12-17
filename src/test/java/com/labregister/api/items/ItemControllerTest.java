package com.labregister.api.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.labregister.api.common.MVCIntegrationTest;
import com.labregister.api.items.controller.ItemController;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.domain.ItemVersion;
import com.labregister.api.items.service.ItemService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest extends MVCIntegrationTest {

	@MockBean
	private ItemService itemServiceMock;

	@Test
	public void GET_Items_ReturnsCorrectHeaderAndContent() throws Exception {
		List<Item> items = ImmutableList.of(ItemFactory.randomItem(),
		                                    ItemFactory.randomItem(),
		                                    ItemFactory.randomItem());

		when(itemServiceMock.getItems()).thenReturn(items);

		ResultActions actions = mockMvc.perform(get("/items"))
		                               .andExpect(status().isOk())
		                               .andExpect(jsonPath("$", hasSize(3)));

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			assertItem("$.[" + i + "]", item, actions);
		}
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void POST_createsItemSuccessfully_WhenRequestValid() throws Exception {
		Item item = ItemFactory.randomItem();
		final String body = getNewItemBodyAsJSON(item);

		when(itemServiceMock.createItem(any())).thenReturn(item);

		ResultActions actions = mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated());
		assertItem("$", item, actions);
	}

	@Test
	public void PUT_updateItemSuccessfully_WhenRequestValid() throws Exception {
		Item item = ItemFactory.randomItem();
		final String body = asJsonString(item);

		when(itemServiceMock.updateItem(anyString(), any())).thenReturn(item);

		ResultActions actions = mockMvc
				.perform(put("/items/" + item.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isOk());

		assertItem("$", item, actions);
	}

	@Test
	public void GET_ItemVersions_ReturnsCorrectHeaderAndContent() throws Exception {
		List<ItemVersion> items = ImmutableList
				.of(ItemFactory.randomItem(), ItemFactory.randomItem(), ItemFactory.randomItem()).stream()
				.map(ItemVersion::new).collect(Collectors.toList());

		when(itemServiceMock.getItemVersions(anyString())).thenReturn(items);

		final String ITEM_ID = "TEST-ID";

		ResultActions actions = mockMvc.perform(get("/items/" + ITEM_ID + "/versions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));

		for (int i = 0; i < items.size(); i++) {
			ItemVersion itemVersion = items.get(i);
			assertItemVersion("$.[" + i + "]", itemVersion, actions);
		}
	}

	private void assertItem(String rootPath, Item item, ResultActions actions) throws Exception {
		actions.andExpect(jsonPath(rootPath + ".id", is(item.getId())))
				.andExpect(jsonPath(rootPath + ".creationDate").exists())
				.andExpect(jsonPath(rootPath + ".name", is(item.getName())))
				.andExpect(jsonPath(rootPath + ".attributes").exists())
				.andExpect(jsonPath(rootPath + ".versionNumber").exists())
				.andExpect(jsonPath(rootPath + ".lastUpdateDate").exists());
	}

	private void assertItemVersion(String rootPath, ItemVersion itemVersion, ResultActions actions) throws Exception {
		actions.andExpect(jsonPath(rootPath + ".versionNumber", is(itemVersion.getVersionNumber())))
				.andExpect(jsonPath(rootPath + ".versionDate").exists())
				.andExpect(jsonPath(rootPath + ".name", is(itemVersion.getName())))
				.andExpect(jsonPath(rootPath + ".attributes").exists());
	}

	private String getNewItemBodyAsJSON(Item item) throws JSONException {
		JSONObject json = new JSONObject().put("name", item.getName());
		return json.toString();
	}

}
