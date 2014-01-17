package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;

import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;

public interface FiltersEngine {
	ItemsFilterResponse applyFilters(List<Filter> filters, Request request);
}
