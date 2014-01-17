package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;

import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;

public interface FiltersEngine {
	ItemsFilterResponse applyFilters(List<Filter> filters, List<Item> items);
}
