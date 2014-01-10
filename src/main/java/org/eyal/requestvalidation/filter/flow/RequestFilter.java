package org.eyal.requestvalidation.filter.flow;

import java.util.List;

import org.eyal.requestvalidation.filter.FiltersEngine;
import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;

public class RequestFilter {

	private ItemFiltersByFlagMapper filtersMapper;
	private FiltersEngine filtersEngine;

	public RequestFilter(ItemFiltersByFlagMapper filtersMapper, FiltersEngine filtersEngine) {
		this.filtersMapper = filtersMapper;
		this.filtersEngine = filtersEngine;
	}

	public ItemsFilterResponse filter(Request request) {
		List<Filter> filters = filtersMapper.getFilters(request);
		return filtersEngine.applyFilters(filters, request.getItems());
	}
}
