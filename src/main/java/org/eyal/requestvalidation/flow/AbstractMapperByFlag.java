package org.eyal.requestvalidation.flow;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eyal.requestvalidation.model.Request;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public abstract class AbstractMapperByFlag<T> implements MapperByFlag<T> {

	private List<T> defaultOperations;
	private Map<String, List<T>> mapOfOperations;

	public AbstractMapperByFlag(List<T> defaultOperations, Map<String, List<T>> mapOfOperations) {
		this.defaultOperations = defaultOperations;
		this.mapOfOperations = mapOfOperations;
	}

	@Override
    public final List<T> getOperations(Request request) {
		Set<T> selectedFilters = Sets.newHashSet(defaultOperations);

		Set<String> flags = request.getFlags();
		for (String flag : flags) {
			if (mapOfOperations.containsKey(flag)) {
				selectedFilters.addAll(mapOfOperations.get(flag));
			}
		}
		return Lists.newArrayList(selectedFilters);
    }

}
