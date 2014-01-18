package org.eyal.requestvalidation.flow.itemsfilter;

import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;

public class ItemFiltersMapperByFlag extends AbstractMapperByFlag<Filter> {
	public ItemFiltersMapperByFlag(List<Filter> defaultFilters, Map<String, List<Filter>> filtersByFlag) {
		super(defaultFilters, filtersByFlag);
	}
}

