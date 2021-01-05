import { Item } from 'models/Item';
import { ItemVersion } from 'models/ItemVersion';

const itemsEndpoint = "/items";

/**
 * Get all items. 
 *
 * @export
 * @return {Promise<Item[]>} The stored items
 */
export async function getAllItems(): Promise<Item[]> {
  const response = await fetch(
    itemsEndpoint,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
    }
  );

  return response.json();
}


/**
 * Create a new item. 
 *
 * @export
 * @param {Item} item The item to store
 * @return {Promise<Item>} The stored item
 */
export async function postItem(item: Item): Promise<Item> {
  const response = await fetch(
    itemsEndpoint,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    }
  );

  return response.json();
}

/**
 * Update existing item. 
 *
 * @export
 * @param {Item} item The item to update
 * @return {Promise<Item>} The updated item
 */
export async function putItem(item: Item): Promise<Item> {
  const itemUrl = `${itemsEndpoint}/${item.id}`
  const response = await fetch(
    itemUrl,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    }
  );

  return response.json();
}

/**
 * Get all versions of item.
 *
 * @export
 * @param {string} itemId of item
 * @return {Promise<ItemVersion[]>} The stored versions of item
 */
export async function getItemVersions(itemId: string): Promise<ItemVersion[]> {
  const itemUrl = `${itemsEndpoint}/${itemId}/versions`
  const response = await fetch(
    itemUrl,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
    }
  );

  return response.json();
}
