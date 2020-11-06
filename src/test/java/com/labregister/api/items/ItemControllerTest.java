package com.labregister.api.items;

import com.google.common.collect.ImmutableList;
import com.labregister.api.common.MVCIntegrationTest;
import com.labregister.api.items.controller.ItemController;
import com.labregister.api.items.domain.Item;
import com.labregister.api.items.service.ItemService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

	@Test
	public void POST_createsItemSuccessfully_WhenRequestValid() throws Exception {
		Item item = ItemFactory.randomItem();
		final String body = getItemBodyAsJSON(item);

		when(itemServiceMock.createItem(any())).thenReturn(item);

		ResultActions actions = mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
		                                                      .content(body))
		                               .andExpect(status().isCreated());
		assertItem("$", item, actions);
	}

	private void assertItem(String rootPath, Item item, ResultActions actions) throws Exception {
		actions.andExpect(jsonPath(rootPath + ".id", is(item.getId())))
		       .andExpect(jsonPath(rootPath + ".creationDate").exists())
		       .andExpect(jsonPath(rootPath + ".name", is(item.getName())))
		       .andExpect(jsonPath(rootPath + ".attributes").exists());
	}

	private String getItemBodyAsJSON(Item item) throws JSONException {
		JSONObject json = new JSONObject().put("name", item.getName());
		return json.toString();
	}

}
