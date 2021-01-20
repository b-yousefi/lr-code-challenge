import { bindable } from 'aurelia-framework';
import { ItemVersion } from '../../models/ItemVersion';

import './item-version-card.scss';

/**
 * Custom element to display item version
 *
 * @export
 * @class ItemVersionCard
 * @customElement
 */

export class ItemVersionCard {

  /**
   * The version to display
   *
   * @type {ItemVersion}
   * @memberof ItemVersionCard
   * @bindable
   */
  @bindable version: ItemVersion;


  /**
   * Array of this version's attributes
   *
   * @type {Array<Array<string>>}
   * @memberof ItemVersionCard
   */
  public attributes: Array<Array<string>>;

  /**
   * @memberof ItemVersionCard
   * @lifecycle
   */
  bind() {
    this.convertItemsObjectToArray();
  }

  /**
   * Convert items object to iterable array, so that in can be used with repeat.for in the view
   *
   * @memberof ItemVersionCard
   */
  convertItemsObjectToArray() {
    this.attributes = Object.entries(this.version.attributes);
  }

}
