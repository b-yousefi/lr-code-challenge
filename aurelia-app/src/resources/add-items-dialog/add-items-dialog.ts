import { bindable } from 'aurelia-framework';

import * as Api from '../api/items';

import './add-items-dialog.scss';

export class AddItemsDialog {
  /**
   * Bound function reference to call to persist the new item.
   *
   * @memberof AddItemsDialog
   * @bindable
   */
  @bindable save: (item: Api.Item) => Promise<void>;

  /**
   * Input element value of the item name
   *
   * @type {string}
   * @memberof AddItemsDialog
   */
  public itemName: string;

  /**
   * Array of attributes of the new item
   *   The attributes key and value inputs are bound to the elements of the array
   *
   * @type {Array<Array<string>>}
   * @memberof AddItemsDialog
   */
  public attributes: Array<Array<string>> = new Array();

  /**
   * Add empty attribute to attributes array
   *
   * @memberof AddItemsDialog
   */
  addEmptyAttribute(): void {
    this.attributes.push(['', '']);
  }

  /**
  * Removes the attribute from item
  *
  */
  removeAttribute(index) {
    this.attributes.splice(index, 1);
  }

  /**
   * Persist current item
   *
   * @return {*}  {Promise<void>}
   * @memberof AddItemsDialog
   */
  async addItem(): Promise<void> {
    void await this.save({
      name: this.itemName,
      attributes: Object.fromEntries(this.attributes)
    });
    this.resetDialog();
  }

  /**
   * Resets the add item form
   *
   * @memberof AddItemsDialog
   */
  resetDialog() {
    this.itemName = '';
    this.attributes = new Array();
  }

  /**
   * Getter for all attributes valid
   *
   * @readonly
   * @memberof AddItemsDialog
   */
  get validateAttributes() {
    return this.attributes.every(([key, value]) => {
      return key && value
    });
  }
}
