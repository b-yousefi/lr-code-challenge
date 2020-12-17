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
   * Bound function reference to call to update item.
   *
   * @memberof AddItemsDialog
   * @bindable
   */
  @bindable update: (item: Api.Item) => Promise<void>;

  /**
   * Bound function reference to call to cancel edition
   *
   * @memberof AddItemsDialog
   * @bindable
   */
  @bindable oncancel: () => void;

  /**
   * The current item
   *
   * @type {Item}
   * @memberof AddItemsDialog
   * @bindable
   */
  @bindable item: Api.Item;


  /**
   * To show whether form is in edit mode or add mode
   *
   * @type {boolean}
   * @memberof AddItemsDialog
   * @bindable
   */
  @bindable isEditMode: boolean;

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
   * @memberof AddItemsDialog
   * @lifecycle
   */
  bind() {
    if (this.item) {
      this.itemName = this.item.name;
      this.attributes = Object.entries(this.item.attributes);
      this.isEditMode = true;
    } else {
      this.isEditMode = false;
    }
  }

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
  * Updates item if it already exist, otherwise adds item
  *
  */
  saveItem() {
    if (this.isEditMode) {
      this.updateItem();
    } else {
      this.addItem();
    }
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
   * update current item
   *
   * @return {*}  {Promise<void>}
   * @memberof AddItemsDialog
   */
  async updateItem(): Promise<void> {
    void await this.update({
      id: this.item.id,
      name: this.itemName,
      attributes: Object.fromEntries(this.attributes)
    });
    this.resetDialog();
  }

  /**
   * cancel edition
   *
   * @memberof AddItemsDialog
   */
  cancel() {
    this.oncancel();
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
