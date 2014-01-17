package org.eyal.requestvalidation.flow.itemsfilter;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;

public class FiltersExecutor extends AbstractFlowExecutor<Filter, ItemsFilterResponse> {

	public FiltersExecutor(MapperByFlag<Filter> filtersMapper, FiltersEngine filtersEngine) {
		super(filtersMapper, filtersEngine);
	}
}
