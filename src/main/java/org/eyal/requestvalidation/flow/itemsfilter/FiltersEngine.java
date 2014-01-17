package org.eyal.requestvalidation.flow.itemsfilter;

import org.eyal.requestvalidation.flow.FlowEngine;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.ItemsFilterResponse;

public interface FiltersEngine extends FlowEngine<ItemsFilterResponse, Filter> {
}
