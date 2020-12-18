import { Item } from 'models/Item';
import store from "../store";

import * as Mutations from "./mutations";

function getAllItems(): void {
  store.dispatch(Mutations.getAllItems);
}

function addItem(item: Item): void {
  store.dispatch(Mutations.postItem, item);
}

function updateItem(item: Item): void {
  store.dispatch(Mutations.putItem, item);
}

function getItemVersions(itemId: string): void {
  store.dispatch(Mutations.getItemVersions, itemId);
}

export { getAllItems, addItem, updateItem, getItemVersions };
