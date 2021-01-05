import { connectTo, Store } from 'aurelia-store';
import { inject } from 'aurelia-dependency-injection';
import { pluck } from "rxjs/operators";
import { dispatchify } from 'aurelia-store';

import ItemState from './store/item/state';
import * as Mutations from './store/item/mutations';
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
@inject(Store)
export class App {
  public title: string = 'Labregister v0.1: A Simple Key-Value based Item Manager';

  getAllItems: () => Promise<void>;

  constructor(store: Store<ItemState>) {
    store.registerAction(Mutations.getAllItems.name, Mutations.getAllItems);
    store.registerAction(Mutations.postItem.name, Mutations.postItem);
    store.registerAction(Mutations.putItem.name, Mutations.putItem);
    store.registerAction(Mutations.getItemVersions.name, Mutations.getItemVersions);

    this.getAllItems = dispatchify(Mutations.getAllItems);
  }

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
    this.getAllItems();
  }
}
