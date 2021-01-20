import { Item } from "models/Item";

export default interface ItemState {
  items: Item[];
}

export const initialState: ItemState = {
  items: [],
};

