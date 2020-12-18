import { ItemVersion } from "models/ItemVersion";
import { Item } from "models/Item";
import ItemState from "store/item/state";
import store from "store/store";

const itemsEndpoint = "/items";

/**
 * Get all items.
 *
 * @export
 * @return {Promise<ItemState>} The state with fetched items
 */
const getAllItems = async (state: ItemState): Promise<ItemState> => {
  const response = await fetch(itemsEndpoint, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
  const items: Item[] = await response.json();

  const newState: ItemState = {
    ...state,
    items: items,
  };

  return newState;
};

/**
 * Create a new item.
 *
 * @export
 * @param {Item} item The item to store
 * @return {Promise<ItemState>} The state with new item added
 */
const postItem = async (state: ItemState, item: Item): Promise<ItemState> => {
  const response = await fetch(itemsEndpoint, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(item),
  });

  const addedItem: Item = await response.json();

  const newState: ItemState = {
    ...state,
    items: [addedItem].concat(state.items),
  };

  return newState;
};

/**
 * Update existing item.
 *
 * @export
 * @param {Item} item The item to update
 * @return {Promise<ItemState>}  The state with updated item
 */
const putItem = async (state: ItemState, item: Item): Promise<ItemState> => {
  const itemUrl = `${itemsEndpoint}/${item.id}`;
  console.log(item);
  console.log(JSON.stringify(item));
  const response = await fetch(itemUrl, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(item),
  });

  const updatedItem: Item = await response.json();

  const updatedItems = [...state.items];
  const updatedItemIndx = updatedItems.findIndex(
    (it) => it.id === updatedItem.id
  );
  updatedItems[updatedItemIndx] = updatedItem;

  const newState: ItemState = {
    ...state,
    items: updatedItems,
  };
  return newState;
};

/**
 * Get all versions of selected item.
 *
 * @export
 * @param {string} itemId of item
 * @return {Promise<ItemState>} The state with fetched versions of selected item
 */
const getItemVersions = async (
  state: ItemState,
  itemId: string
): Promise<ItemState> => {
  const itemUrl = `${itemsEndpoint}/${itemId}/versions`;
  const response = await fetch(itemUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  const itemVerions: ItemVersion[] = await response.json();

  const updatedItems = [...state.items];
  const itemIndx = updatedItems.findIndex((it) => it.id === itemId);

  updatedItems[itemIndx] = { ...updatedItems[itemIndx], versions: itemVerions };

  const newState: ItemState = {
    ...state,
    items: updatedItems,
  };

  return newState;
};

store.registerAction("getAllItems", getAllItems);
store.registerAction("postItem", postItem);
store.registerAction("putItem", putItem);
store.registerAction("getItemVersions", getItemVersions);

export { getAllItems, postItem, putItem, getItemVersions };
