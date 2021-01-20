import produce from 'immer';
import { setAutoFreeze } from 'immer';

import { ItemVersion } from "models/ItemVersion";
import { Item } from "models/Item";
import ItemState from "store/item/state";
import * as Api from './api';

setAutoFreeze(false);

const itemsEndpoint = "/items";

/**
 * Get all items.
 *
 * @export
 * @return {Promise<ItemState>} The state with fetched items
 */
const getAllItems = async (state: ItemState): Promise<ItemState> => {
  const items: Item[] = await Api.getAllItems();

  const newState: ItemState = produce(state, (draftState) => {
    draftState.items = items
  });

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
  const addedItem: Item = await Api.postItem(item);

  const newState: ItemState = produce(state, (draftState) => {
    draftState.items.unshift(addedItem);
  });

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
  const updatedItem: Item = await Api.putItem(item);

  const updatedItemIndx = state.items.findIndex(
    (it) => it.id === updatedItem.id
  );

  const newState: ItemState = produce(state, (draftState) => {
    draftState.items[updatedItemIndx] = updatedItem;
  });

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
  const itemVerions: ItemVersion[] = await Api.getItemVersions(itemId);

  const itemIndx = state.items.findIndex((it) => it.id === itemId);

  const newState: ItemState = produce(state, (draftState) => {
    draftState.items[itemIndx] = { ...draftState.items[itemIndx], versions: itemVerions };
  });

  return newState;
};

export { getAllItems, postItem, putItem, getItemVersions };
