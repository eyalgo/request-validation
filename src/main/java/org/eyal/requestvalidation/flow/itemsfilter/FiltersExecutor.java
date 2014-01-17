package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;

import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;

public class FiltersExecutor {

	private MapperByFlag<Filter> filtersMapper;
	private FiltersEngineImpl filtersEngine;

	public FiltersExecutor(MapperByFlag<Filter> filtersMapper, FiltersEngineImpl filtersEngine) {
		this.filtersMapper = filtersMapper;
		this.filtersEngine = filtersEngine;
	}

	public ItemsFilterResponse filter(Request request) {
		List<Filter> filters = filtersMapper.getOperations(request);
		return filtersEngine.applyFilters(filters, request.getItems());
	}
}
