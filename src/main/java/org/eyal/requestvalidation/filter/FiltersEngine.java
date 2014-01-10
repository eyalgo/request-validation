package org.eyal.requestvalidation.filter;

import java.util.List;

import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;

import com.google.common.collect.Lists;

public class FiltersEngine {

	public FiltersEngine() {
	}

	public ItemsFilterResponse validate(List<Filter> filters, List<Item> items) {
		List<Item> validItems = Lists.newLinkedList(items);
		List<InvalidItemInformation> invalidItemInformations = Lists.newLinkedList();
		for (Filter validator : filters) {
			ItemsFilterResponse responseFromFilter = responseFromFilter(validItems, validator);
			validItems = responseFromFilter.getValidItems();
			invalidItemInformations.addAll(responseFromFilter.getInvalidItemsInformations());
		}

		return new ItemsFilterResponse(validItems, invalidItemInformations);
	}

	private ItemsFilterResponse responseFromFilter(List<Item> items, Filter filter) {
		List<Item> validItems = Lists.newLinkedList();
		List<InvalidItemInformation> invalidItemInformations = Lists.newLinkedList();
		for (Item item : items) {
			if (filter.apply(item)) {
				validItems.add(item);
			} else {
				invalidItemInformations.add(new InvalidItemInformation(item, filter.errorMessage()));
			}
		}
		return new ItemsFilterResponse(validItems, invalidItemInformations);
	}
}
