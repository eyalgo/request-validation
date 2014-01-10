package org.eyal.requestvalidation.filter.flow;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.Request;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ItemFiltersByFlagMapperImpl implements ItemFiltersByFlagMapper {

	private List<Filter> defaultFilters;
	private Map<String, List<Filter>> mapOfFilters;

	public ItemFiltersByFlagMapperImpl(List<Filter> defaultFilters, Map<String, List<Filter>> mapOfFilters) {
		this.defaultFilters = defaultFilters;
		this.mapOfFilters = mapOfFilters;
	}

	@Override
	public List<Filter> getFilters(Request request) {
		Set<Filter> selectedFilters = Sets.newHashSet(defaultFilters);

		Set<String> flags = request.getFlags();
		for (String flag : flags) {
			if (mapOfFilters.containsKey(flag)) {
				selectedFilters.addAll(mapOfFilters.get(flag));
			}
		}
		return Lists.newArrayList(selectedFilters);
	}

}
