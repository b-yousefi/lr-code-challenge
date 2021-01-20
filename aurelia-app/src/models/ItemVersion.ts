import { Item } from "./Item";

export interface ItemVersion extends Item {
  versionDate: Date;
  versionNumber: number;
}
