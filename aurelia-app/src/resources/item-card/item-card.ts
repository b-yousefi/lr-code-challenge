import { bindable } from 'aurelia-framework';
import { Item } from '../api/items';

import './item-card.scss';

/**
 * Custom element to display an item
 *
 * @export
 * @class ItemCard
 * @customElement
 */
export class ItemCard {
  /**
   * The item to display
   *
   * @type {Item}
   * @memberof ItemCard
   * @bindable
   */
  @bindable item: Item;

  /**
   * Array of this item's attributes
   *
   * @type {Array<Array<string>>}
   * @memberof ItemCard
   */
  public attributes: Array<Array<string>>;

  /**
   * @memberof ItemCard
   * @lifecycle
   */
  bind() {
    this.convertItemsObjectToArray();
  }

  /**
   * Convert items object to iterable array, so that in can be used with repeat.for in the view
   *
   * @memberof ItemCard
   */
  convertItemsObjectToArray() {
    this.attributes = Object.entries(this.item.attributes);
  }
}
