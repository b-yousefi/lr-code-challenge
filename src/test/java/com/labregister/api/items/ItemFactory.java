package com.labregister.api.items;

import com.google.common.collect.ImmutableMap;
import com.labregister.api.items.domain.Item;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

public class ItemFactory {

	static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	static SecureRandom random = new SecureRandom();

	public static Item randomItem() {
		Item item = new Item();
		item.setId(randomUUID());
		item.setName(randomString(12));
		item.setAttributes(ImmutableMap.of(randomString(4), randomString(10), randomString(5), randomString(16)));
		item.setCreationDate(randomDate());
		return item;
	}

	private static String randomUUID() {
		return UUID.randomUUID()
		           .toString();
	}


	private static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(random.nextInt(chars.length())));
		return sb.toString();
	}

	private static Date randomDate() {
		return new Date();
	}
}
