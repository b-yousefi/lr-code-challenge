import * as Api from './resources/api/items';

import './app.scss';

/**
 * Root app component
 *
 * @export
 * @class App
 */
export class App {
  public title: string = 'Labregister v0.1: A Simple Key-Value based Item Manager';

  /**
   * List of existing items
   *
   * @type {Api.Item[]}
   * @memberof App
   */
  public items: Api.Item[];

  public versions: Api.ItemVersion[];
  /**
   * @memberof App
   * @lifecycle
   */
  attached() {
    void this.getAllItems();
  }

  /**
   * Load all items
   *
   * @return {*}  {Promise<void>}
   * @memberof App
   */
  async getAllItems(): Promise<void> {
    this.items = await Api.getAllItems();
  }

  /**
   * Persist an item
   * 
   * Passed as function reference to custom element
   *   and thus needs to be an arrow function to bind 'this' *
   *
   * @param {Api.Item} item
   * @memberof App
   */
  addItem = async (item: Api.Item): Promise<void> => {
    await Api.postItem(item);
    this.getAllItems();
  }

  /**
   * update the item
   * 
   * Passed as function reference to custom element
   *   and thus needs to be an arrow function to bind 'this' *
   *
   * @param {Api.Item} item
   * @memberof App
   */
  updateItem = async (item: Api.Item): Promise<void> => {
    const updatedItem = await Api.putItem(item);
    const itemId = this.items.findIndex(it => it.id == updatedItem.id);
    this.items = [...this.items];
    this.items[itemId] = updatedItem;
  }

  /**
   * get all versions of item
   * 
   * Passed as function reference to custom element
   *   and thus needs to be an arrow function to bind 'this' *
   *
   * @param {string} itemId
   * @memberof App
   */
  async getItemVersions(itemId: string): Promise<Api.ItemVersion[]> {
    return await Api.getItemVersions(itemId);
  }
}
