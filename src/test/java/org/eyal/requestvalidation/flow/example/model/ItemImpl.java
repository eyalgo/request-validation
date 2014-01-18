package org.eyal.requestvalidation.flow.example.model;

import org.eyal.requestvalidation.model.Item;

public class ItemImpl implements Item {

	private final String name;

	public ItemImpl(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
