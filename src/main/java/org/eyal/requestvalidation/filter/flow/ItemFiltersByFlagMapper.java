package org.eyal.requestvalidation.filter.flow;

import java.util.List;

import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.Request;

public interface ItemFiltersByFlagMapper {
	List<Filter> getFilters(Request request);
}
