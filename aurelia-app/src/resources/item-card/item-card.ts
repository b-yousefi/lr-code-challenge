import { bindable } from 'aurelia-framework';
import { dispatchify } from 'aurelia-store';

import { Item } from '../../models/Item';
import * as Mutations from '../../store/item/mutations';

import './item-card.scss';

/**
 * Custom element to display an item
 *
 * @export
 * @class ItemCard
 * @customElement
 */
export class ItemCard {
  getItemVersions: (itemId: string) => Promise<void>;

  constructor() {
    this.getItemVersions = dispatchify(Mutations.getItemVersions);
  }

  /**
   * The item
   *
   * @type {Item}
   * @memberof ItemCard
   * @bindable
   */
  @bindable item: Item;

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
   * Function to get all versions of item.
   *
   * @memberof ItemCard
   */
  versions() {
    this.getItemVersions(this.item.id);
  }

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
    this.showVersions = this.item.versions !== undefined;
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
   * @memberof ItemCard
   */
  onStartEdit(): void {
    this.isEditMode = true;
  }

  /**
   * Swith to View mode
   *
   * @memberof ItemCard
   */
  onCancelEdit = () => {
    this.isEditMode = false;
  }

  /**
   * Toggle item versions
   *
   * @memberof ItemCard
   */
  onToggleVersions = () => {
    if (this.showVersions) {
      this.showVersions = false;
    } else {
      this.versions();
      this.showVersions = true;
    }
  }
}
