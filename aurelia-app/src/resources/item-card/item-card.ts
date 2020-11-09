import { bindable } from 'aurelia-framework';
import { Item, ItemVersion } from '../api/items';

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
   * The item
   *
   * @type {Item}
   * @memberof ItemCard
   * @bindable
   */
  @bindable item: Item;

  /**
   * The item versions
   *
   * @type {ItemVersion[]}
   * @memberof ItemCard
   * @bindable
   */
  @bindable itemVersions: ItemVersion[] = [];

  /**
   * Edit mode 
   *
   * @type {boolean}
   * @memberof ItemCard
   * @bindable
   */
  @bindable isEditMode: boolean;

  /**
   * Version mode 
   *
   * @type {boolean}
   * @memberof ItemCard
   * @bindable
   */
  @bindable showVersions: boolean;

  /**
   * Bound function reference to call to update item.
   *
   * @memberof ItemCard
   * @bindable
   */
  @bindable update: (item: Item) => Promise<void>;

  /**
   * Bound function reference to call to get all versions of item.
   *
   * @memberof ItemCard
   * @bindable
   */
  @bindable versions: (itemId: string) => Promise<ItemVersion[]>;

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

  /**
   * Swith to Edit mode
   *
   * @memberof ItemCardEditable
   */
  onStartEdit(): void {
    this.isEditMode = true;
  }

  /**
   * Swith to View mode
   *
   * @memberof ItemCardEditable
   */
  onCancelEdit = () => {
    this.isEditMode = false;
  }

  /**
   * Toggle item versions
   *
   * @memberof ItemCardEditable
   */
  onToggleVersions = async (): Promise<void> => {
    if (this.showVersions) {
      this.showVersions = false;
    } else {
      this.itemVersions = await this.versions(this.item.id);
      this.showVersions = true;
    }
  }
}
