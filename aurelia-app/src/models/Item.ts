import { ItemVersion } from '../models/ItemVersion';
export interface Item {
  id?: string;
  name: string;
  attributes: { [key: string]: string };
  versions?: ItemVersion[];
}
