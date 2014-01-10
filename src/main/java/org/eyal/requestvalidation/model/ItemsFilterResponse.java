package org.eyal.requestvalidation.model;

import java.util.List;

public class ItemsFilterResponse {
	private List<Item> validItems;
	private List<InvalidItemInformation> invalidItemInformations;

	public ItemsFilterResponse(List<Item> validItems, List<InvalidItemInformation> invalidItemInformations) {
		this.validItems = validItems;
		this.invalidItemInformations = invalidItemInformations;
	}

	public List<Item> getValidItems() {
		return validItems;
	}

	public List<InvalidItemInformation> getInvalidItemsInformations() {
		return invalidItemInformations;
	}

}
