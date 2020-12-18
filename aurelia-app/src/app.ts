import { connectTo } from 'aurelia-store'
import { pluck } from "rxjs/operators";

import ItemState from './store/item/state';
import * as Api from './store/item/actions';

import './app.scss';

/**
 * Root app component
 *
 * @export
 * @class App
 */
@connectTo<ItemState>({
  selector: (store) => store.state.pipe(pluck("items")),
  target: "items",
})
export class App {
  public title: string = 'Labregister v0.1: A Simple Key-Value based Item Manager';

  /**
   * List of existing items
   *
   * @type {ItemState}
   * @memberof App
   */
  public items: ItemState;

  /**
   * @memberof App
   * @lifecycle
   */
  attached() {
    Api.getAllItems();
  }
}
