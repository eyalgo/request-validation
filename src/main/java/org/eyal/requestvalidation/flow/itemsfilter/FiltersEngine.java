package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;

import org.eyal.requestvalidation.flow.FlowEngine;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;

import com.google.common.collect.Lists;

public class FiltersEngine implements FlowEngine<ItemsFilterResponse, Filter> {
	public FiltersEngine() {
	}

	@Override
	public ItemsFilterResponse applyOperations(List<Filter> operations, Request request) {
		List<Item> validItems = Lists.newLinkedList(request.getItems());
		List<InvalidItemInformation> invalidItemInformations = Lists.newLinkedList();
		for (Filter validator : operations) {
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
