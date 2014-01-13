package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;

import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;

public class RequestFilter {

	private MapperByFlag<Filter> filtersMapper;
	private FiltersEngine filtersEngine;

	public RequestFilter(MapperByFlag<Filter> filtersMapper, FiltersEngine filtersEngine) {
		this.filtersMapper = filtersMapper;
		this.filtersEngine = filtersEngine;
	}

	public ItemsFilterResponse filter(Request request) {
		List<Filter> filters = filtersMapper.getOperations(request);
		return filtersEngine.applyFilters(filters, request.getItems());
	}
}
