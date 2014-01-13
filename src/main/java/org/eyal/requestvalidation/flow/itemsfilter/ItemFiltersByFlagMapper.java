package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;

public class ItemFiltersByFlagMapper extends AbstractMapperByFlag<Filter> {
	public ItemFiltersByFlagMapper(List<Filter> defaultFilters, Map<String, List<Filter>> mapOfFilters) {
		super(defaultFilters, mapOfFilters);
	}
}

